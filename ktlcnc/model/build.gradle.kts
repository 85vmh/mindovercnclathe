plugins { kotlin("jvm") }

group = "ro.dragossusi"

version = Versions.app

repositories { mavenCentral() }

dependencies {
  implementation(project(":protos"))

  implementation(Libs.Coroutines.core)
  api(Libs.Grpc.proto_kotlin)
  testImplementation(kotlin("test"))
}

tasks.test { useJUnitPlatform() }
