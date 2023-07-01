plugins { kotlin("jvm") }

version = "unspecified"

dependencies {
  implementation(Libs.stdlib)
  implementation(project(":data:repository"))
  implementation(project(":logger"))
  implementation(project(":dispatcher"))
  implementation(project(":ktlcnc"))
  implementation(project(":database"))
  implementation(project(":model"))
  implementation(project(":grpc"))
  implementation(project(":editor"))
  implementation(Libs.okio)

  // logging
  implementation(Libs.logging)
}
