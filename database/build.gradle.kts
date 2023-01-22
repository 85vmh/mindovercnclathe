import Versions.sqliteJdbc

plugins { kotlin("jvm") }

version = "unspecified"

repositories { mavenCentral() }

dependencies {
  implementation(kotlin("stdlib"))
  api(Libs.Coroutines.core)
  api(Libs.Kodein.core)

  api(Libs.Exposed.core)
  api(Libs.Exposed.dao)
  api(Libs.Exposed.jdbc)

  implementation("org.xerial:sqlite-jdbc:$sqliteJdbc")

  implementation(project(":model"))

  implementation(Libs.ktlcnc_model)
}
