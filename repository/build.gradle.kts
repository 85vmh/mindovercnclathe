plugins { kotlin("jvm") }

version = "unspecified"

repositories { mavenCentral() }

dependencies {
  implementation(kotlin("stdlib"))
  api(Libs.Coroutines.core)
  implementation(Libs.Kodein.core)

  implementation(project(":database"))
  api(project(":model"))
  api(Libs.ktlcnc_model)
  implementation(Libs.okio)
}
