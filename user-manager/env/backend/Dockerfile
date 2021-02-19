FROM gradle:6.8.2-jdk15

WORKDIR /app

COPY entrypoint.sh /usr/local/bin/

RUN chmod 777 /usr/local/bin/entrypoint.sh \
    && ln -s /usr/local/bin/entrypoint.sh /

EXPOSE 8080

USER gradle

ENTRYPOINT ["entrypoint.sh"]
