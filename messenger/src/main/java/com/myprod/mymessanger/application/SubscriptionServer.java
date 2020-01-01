package com.myprod.mymessanger.application;

import com.myprod.mymessanger.domain.message.Message;
import com.myprod.mymessanger.domain.user.User;
import com.myprod.mymessanger.domain.message.Room;
import graphql.GraphQL;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.reactivex.Flowable;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.graphql.ApolloWSHandler;
import io.vertx.reactivex.RxHelper;
import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

public class SubscriptionServer extends AbstractVerticle {

  public static void main(String[] args) {
    Launcher.executeCommand("run", SubscriptionServer.class.getName());
  }

  private List<Message> messages;

  @Override
  public void start() {
    prepareData();

    Router router = Router.router(vertx);
    router.route("/graphql").handler(ApolloWSHandler.create(createGraphQL()));

    HttpServerOptions httpServerOptions = new HttpServerOptions()
      .setWebsocketSubProtocols("graphql-ws");
    vertx.createHttpServer(httpServerOptions)
      .requestHandler(router)
      .listen(8080);
  }

  private void prepareData() {

    User ilia = User.builder().name("Ilia").uuid(UUID.randomUUID()).build();
    Room room = Room.builder().name("roomer").owner(ilia).uuid(UUID.randomUUID()).build();

    messages = new ArrayList<>();
    messages.add(
      Message
        .builder()
        .uuid(UUID.randomUUID())
        .message("this is message")
        .owner(ilia)
        .room(room)
        .build()
    );
  }

  private GraphQL createGraphQL() {
    String schema = vertx.fileSystem().readFileBlocking("messages.graphql").toString();

    SchemaParser schemaParser = new SchemaParser();
    TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

    RuntimeWiring runtimeWiring = newRuntimeWiring()
      .type("Subscription", builder -> builder.dataFetcher("messages", this::messagesFetcher))
      .build();

    SchemaGenerator schemaGenerator = new SchemaGenerator();
    GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

    return GraphQL.newGraphQL(graphQLSchema)
      .build();
  }

  private Publisher<Message> messagesFetcher(DataFetchingEnvironment env) {
    return Flowable.interval(1, TimeUnit.SECONDS) // Ticks
      .zipWith(Flowable.fromIterable(messages), (tick, link) -> link) // Emit link on each tick
      .observeOn(RxHelper.scheduler(context)); // Observe on the verticle context thread
  }
}
