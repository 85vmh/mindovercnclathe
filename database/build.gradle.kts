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

    implementation(Libs.logging)

    implementation("org.xerial:sqlite-jdbc:$sqliteJdbc")

    implementation(project(":model"))
    implementation(project(":dispatcher"))

    implementation(project(":ktlcnc:model"))
}
