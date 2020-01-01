package com.myprod.mymessanger.application;

import com.myprod.mymessanger.domain.message.Message;
import com.myprod.mymessanger.domain.message.Room;
import com.myprod.mymessanger.domain.user.User;
import graphql.GraphQL;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.graphql.GraphQLHandler;
import io.vertx.ext.web.handler.graphql.VertxDataFetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;
import static java.util.stream.Collectors.toList;

public class ServerVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Launcher.executeCommand("run", ServerVerticle.class.getName());
  }

  private List<Message> messages;

  @Override
  public void start() {
    prepareData();

    Router router = Router.router(vertx);
    router.route("/graphql").handler(GraphQLHandler.create(createGraphQL()));

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8888);
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
      .type("Query", builder -> {
        VertxDataFetcher<List<Message>> getAllMessages = new VertxDataFetcher<>(this::getAllMessages);
        return builder.dataFetcher("allMessages", getAllMessages);
      })
      .build();

    SchemaGenerator schemaGenerator = new SchemaGenerator();
    GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

    return GraphQL.newGraphQL(graphQLSchema)
      .build();
  }

  private void getAllMessages(DataFetchingEnvironment env, Promise<List<Message>> future) {
    boolean secureOnly = env.getArgument("secureOnly");
    List<Message> result = messages.stream()
      .filter(message -> !secureOnly)
      .collect(toList());
    future.complete(result);
  }
}
