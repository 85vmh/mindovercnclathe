plugins { kotlin("jvm") }

version = "unspecified"

dependencies {
  implementation(Libs.stdlib)
  implementation(project(":repository"))
  implementation(project(":logger"))
  implementation(project(":dispatchers"))
  implementation(Libs.ktlcnc)
  implementation(project(":database"))
  implementation(project(":model"))
  implementation(Libs.okio)
}
