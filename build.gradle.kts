/**
 * Common
 */

val regex = "(_|-|\\.)([A-Za-z0-9])".toRegex()

fun String.toLowerCamelCase(): String {
  return regex.replace(this) {
    it.value.replace("(_|-|\\.)".toRegex(), "")
      .toUpperCase()
  }
}

fun servicesToDcFilePaths(services: Map<String, String>): Array<String> {
  var dockerComposeFilePaths = emptyArray<String>()

  services.forEach { (_, filePath) ->
    dockerComposeFilePaths += "-f"
    dockerComposeFilePaths += filePath
  }

  return dockerComposeFilePaths
}

/**
 *  Docker compose
 */

var services = mapOf(
  "user-manager.back" to "user-manager/docker-compose.backend.yml",
  "user-manager.back.test" to "user-manager/docker-compose.backend.test.yml",
  "user-manager.front" to "user-manager/docker-compose.frontend.yml",
  "proxy" to "env/docker-compose.proxy.yml"
)

var commands = mapOf(
  "up" to arrayOf("up", "-d"),
  "ps" to arrayOf("ps"),
  "stop" to arrayOf("stop"),
  "down" to arrayOf("down", "-v")
)

var commandDescriptions = mapOf(
  "up" to "Start containers",
  "ps" to "Show containers list",
  "stop" to "Stop containers",
  "down" to "Stop and delete containers and volumes"
)

open class DockerCompose : Exec() {
  init {
    group = "docker-compose"
    executable = "docker-compose"
    this.doFirst {
      println("\nExecuded command - `" + commandLine.joinToString(" ") + "`")
    }
  }
}

open class DockerComposeExec : DockerCompose() {
  var dockerComposeFilePaths = emptyArray<String>()

  init {
    group = "docker-compose"
    executable = "docker-compose"

    if (project.hasProperty("cmd")) {
      val list = ArrayList<String>()
      list.addAll(dockerComposeFilePaths.toList())
      list.addAll(project.property("cmd").toString().split(" "))
      this.setArgs(list)
    }

    this.doFirst {
      println("\nExecuded command - `" + commandLine.joinToString(" ") + "`")
    }

    this.finalizedBy({
      if (!project.hasProperty("cmd")) {
        throw IllegalArgumentException("Not found command for execute. You can try by example: `./gradlew $name up -d`")
      }
    })
  }
}

tasks.register<DockerComposeExec>("dc") {
  group = "docker-compose.all"
  description = "docker-compose unifying all docker-compose yml files"
  dockerComposeFilePaths += servicesToDcFilePaths(services)
}

commands.forEach { (taskName: String, cmd: Array<String>) ->
  val desc = commandDescriptions[taskName]

  tasks.register(taskName, DockerCompose::class) {
    group = "docker-compose.all"
    description = desc.toString()
    this.setArgs((servicesToDcFilePaths(services) + cmd).toMutableList())
  }
}

services.forEach { (serviceName: String, dockerComposeFilePath) ->
  val name = serviceName.toLowerCamelCase()
  val filePaths = servicesToDcFilePaths(services)
  tasks.register(name, DockerComposeExec::class) {
    group = "docker-compose.$serviceName"
    description = "Docker-compose for $serviceName"
    dockerComposeFilePaths = filePaths
  }

  commands.forEach { (taskName: String, cmd: Array<String>) ->
    val desc = commandDescriptions[taskName] ?: ""

    tasks.register((serviceName + "_" + taskName).toLowerCamelCase(), DockerCompose::class) {
      group = "docker-compose.$serviceName"
      description = desc
      this.setArgs((arrayOf("-f", dockerComposeFilePath) + cmd).asList())
    }
  }
}
