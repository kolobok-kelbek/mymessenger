# Backend User Manager service

**NOTE**

Before start service need run proxy `gradle upProxy` or `docker-compose -f docker-compose.proxy.yml up -d`

---
### Menu

- [Main](/README.md)
    - [Help](/doc/help.md)
    - [Development](/doc/development.md)
    - [Environment](/doc/environment.md)
    - [User Manager](/user-manager/doc/common.md)
        - [Frontend](/user-manager/doc/frontend.md)
        - **Backend**

---
### Start service:

- Run `gradle buildUserManagerBackend` or `docker-compose -f docker-compose.user-manager.backend.yml build`
- Run `gradle upUserManagerBackend` or `docker-compose -f docker-compose.user-manager.backend.yml up -d`

**NOTE**:
After starting, wait about 5 minutes until the application builds and the migrations load.

---
### Technologies:
- Spring boot
- PostgreSQL

---
### Credentials for [Adminer](http://user-manager.adminer.mymessenger.local)
- server - `mymessenger-user-manager-backend-db`
- username - `mymess`
- password - `pass`
- database - `mymessenger`

---
### Migrating the database
execute Flyway to migrate our database:

`gradle flywayMigrate -i`
