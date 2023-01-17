plugins { kotlin("jvm") }

version = "unspecified"

dependencies {
  implementation(Libs.stdlib)
  implementation(project(":repository"))
  implementation(project(":logger"))
  implementation(project(":dispatcher"))
  implementation(Libs.ktlcnc)
  implementation(project(":database"))
  implementation(project(":model"))
  implementation(project(":grpc"))
  implementation(Libs.okio)

  // logging
  implementation(Libs.logging)
}
