plugins {
  kotlin("jvm")
  kotlin("plugin.serialization")
}

group = "ro.dragossusi"

version = Versions.app

kotlin {
  val arguments = NativePaths.createJvmArgs(project)
  println(arguments)
  kotlinDaemonJvmArgs = listOf(arguments)
}

repositories { mavenCentral() }

dependencies {
  implementation(Libs.Coroutines.core)
  implementation(Libs.Serialization.json)
  implementation(Libs.logging)
  implementation(Libs.Serialization.json_okio)
  implementation(project(":initializer"))
  api(project(":ktlcnc:model"))
  api(project(":protos"))
  api(Libs.Kodein.core)

  testImplementation(kotlin("test"))
}

tasks.test { useJUnitPlatform() }
