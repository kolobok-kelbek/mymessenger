FROM gradle:6.3-jdk11

WORKDIR /app

COPY entrypoint.sh /usr/local/bin/

RUN chmod 777 /usr/local/bin/entrypoint.sh \
    && ln -s /usr/local/bin/entrypoint.sh /

EXPOSE 8080

USER gradle

ENTRYPOINT ["entrypoint.sh"]
