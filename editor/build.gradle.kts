plugins {
  kotlin("jvm")
  kotlin("plugin.serialization")
}

version = Versions.app

dependencies {
  implementation(project(":dispatcher"))
  implementation(Libs.stdlib)
  implementation(Libs.Serialization.json)
  implementation(Libs.Coroutines.core)
  implementation(Libs.Serialization.json_okio)
  implementation(Libs.ktlcnc)
  implementation(Libs.okio)
}
