plugins { kotlin("jvm") }

version = "unspecified"

repositories { mavenCentral() }

dependencies {
  implementation(kotlin("stdlib"))
  api(Libs.Coroutines.core)
  implementation(Libs.Kodein.core)

  implementation(project(":database"))
  api(project(":model"))

  /** @deprecated Will be replaced by grpc. */
  api(Libs.ktlcnc_model)

  api(project(":grpc"))
  implementation(Libs.okio)
}
