plugins { kotlin("jvm") }

version = "unspecified"

repositories { mavenCentral() }

dependencies {
  implementation(kotlin("stdlib"))
  api(Libs.Coroutines.core)
  implementation(Libs.Kodein.core)

  implementation(project(":database"))
  implementation(project(":editor"))

  api(project(":model"))

  /** @deprecated Will be replaced by grpc. */
  api(project(":ktlcnc:model"))

  api(project(":grpc"))
  implementation(Libs.okio)
}
