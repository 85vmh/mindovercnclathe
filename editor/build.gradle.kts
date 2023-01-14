plugins {
  kotlin("jvm")
  kotlin("plugin.serialization")
}

version = Versions.app

dependencies {
  implementation(Libs.stdlib)
  implementation(Libs.Serialization.json)
  implementation(Libs.Serialization.json_okio)
  implementation(project(":repository"))
  //    implementation(project(":libcnc"))
  implementation(Libs.ktlcnc)
  implementation(Libs.okio)
  implementation(project(":database"))
}
