# Extend vert.x image
FROM vertx/vertx3

ENV VERTICLE_NAME com.myprod.mymessanger.application.ServerVerticle
ENV VERTICLE_FILE build/libs/messenger-1.0.0-SNAPSHOT-fat.jar

ENV VERTICLE_HOME /usr/verticles

EXPOSE 8888

COPY $VERTICLE_FILE $VERTICLE_HOME/

WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec vertx run $VERTICLE_NAME -cp $VERTICLE_HOME/*"]
