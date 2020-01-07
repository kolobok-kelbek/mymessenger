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

# Intellij IDEA

You can install plugins for more comfortable development:

1. [Lombok plugin](https://plugins.jetbrains.com/plugin/6317-lombok/)
2. [GraphQL plugin](https://plugins.jetbrains.com/plugin/8097-js-graphql/)
3. [Ideolog plugin](https://plugins.jetbrains.com/plugin/9746-ideolog/)

this is test message
