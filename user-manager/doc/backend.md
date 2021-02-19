# Backend User Manager service

**NOTE**

Before start service need run proxy `gradle proxyUp` or `docker-compose -f env/docker-compose.proxy.yml up -d`

---
### Menu

- [Main]../..(/README.md)
    - [Help](../../doc/help.md)
    - [Development](../../doc/development.md)
    - [Environment](../../doc/environment.md)
    - [User Manager](common.md)
        - [Frontend](frontend.md)
        - **Backend**

---
### Start service:

- Run `gradle userManagerBack -Pcmd="build"` or `docker-compose -f user-manager/docker-compose.user-manager.backend.yml build`
- Run `gradle userManagerUp` or `docker-compose -f user-manager/docker-compose.user-manager.backend.yml up -d`

**NOTE**:
After starting, wait about 5 minutes until the application builds and the migrations load.

---
### Technologies:
- Spring boot
- PostgreSQL

---
### Credentials for [Adminer](http://um.adminer.mm.local)
- server - `mm-um-backend-db`
- username - `muser`
- password - `muser`
- database - `mm`
