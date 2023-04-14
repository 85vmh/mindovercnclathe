plugins { kotlin("jvm") }

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
  api(project(":ktlcnc:model"))
  api(project(":grpc"))

  testImplementation(kotlin("test"))
}

tasks.test { useJUnitPlatform() }
