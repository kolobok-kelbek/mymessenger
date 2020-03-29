# For Linux

### Starting development

For start project need execute commands:
1. Add `mymessenger.local` to `/etc/hosts`
2. `./gradlew build`
3. `./gradlew dockerBuild`
4. `./gradlew dcUp`

### Update jar file in container

After changed files, you need execute commands:
1. `./gradlew rebuildMessenger`   // for messenger
2. `./gradlew rebuildUserManager` // for user manager

### Code Style fix:
1. `./gradlew messenger:goJF`   // for messenger
2. `./gradlew user-manager:goJF` // for user manager

# For Windows

### Starting development

For start project need execute commands:
1. Add `mymessenger.local` to `С:\windows\system32\drivers\etc\hosts`
2. `gradlew.bat build`
3. `gradlew.bat dockerBuild`
4. `gradlew.bat dcUp`

### Update jar file in container

After changed files, you need execute commands:
1. `gradlew.bat rebuildMessenger`   // for messenger
2. `gradlew.bat rebuildUserManager` // for user manager

### Code Style fix:
1. `gradlew.bat messenger:goJF`   // for messenger
2. `gradlew.bat user-manager:goJF` // for user manager

# Intellij IDEA

You can install plugins for more comfortable development:

1. [Lombok plugin](https://plugins.jetbrains.com/plugin/6317-lombok/)
2. [GraphQL plugin](https://plugins.jetbrains.com/plugin/8097-js-graphql/)
3. [Ideolog plugin](https://plugins.jetbrains.com/plugin/9746-ideolog/)

# Code Style
Need download [Google Code Style config](https://raw.githubusercontent.com/google/styleguide/gh-pages/intellij-java-google-style.xml)
and export in `File→Settings→Editor→Code Style`

# Admin panels

#### For postgresql - [adminer](http://localhost/adminer)<br>
server - `db`<br>
username - `root`<br>
password - `pass`<br>
database - `mymessenger`<br>

#### For redis - [redis commander](http://localhost:8082/)
