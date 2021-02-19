# Environment

**NOTE**

###### Developers on windows should use `gradlew.bat`, on linux should use `./gradlew`.

---

### Menu

- [Main](../README.md)
    - [Help](../doc/help.md)
    - [Development](../doc/development.md)
    - **Environment**
    - [User Manager](../user-manager/doc/common.md)
        - [Frontend](../user-manager/doc/frontend.md)
        - [Backend](../user-manager/doc/backend.md)

---
### Before start project need:

- add domains to /etc/hosts
```
127.0.0.1   um.api.mm.local
127.0.0.1   um.adminer.mm.local
127.0.0.1   um.doc.mm.local
127.0.0.1   um.mm.local
```
- install dependencies:
    - [docker v19.03.9+](https://docs.docker.com/get-docker/)
    - [docker-compose v1.24.1+](https://docs.docker.com/compose/install/)
    - Java v8+
        - [OpenJDK](https://openjdk.java.net/install/index.html)
        - [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html)
- run proxy `gradle upProxy` or `docker-compose -f docker-compose.proxy.yml up -d`

---
### Services:
- [User Manager](/user-manager/doc/common.md)

---
### Technologies:

- Microservices
- Mono-repository
- [Github](https://github.com/)
- [Traefik](https://docs.traefik.io/)
- [Gradle](https://gradle.org/) / [Webpack](https://webpack.js.org/)
- [Docker](https://www.docker.com/) / docker-compose

---
### Important links:
- [Traefik dashboard](http://localhost:8080/dashboard) - available after run proxy.
