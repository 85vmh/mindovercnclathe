import Versions.sqliteJdbc

plugins { kotlin("jvm") }

version = Versions.app

repositories { mavenCentral() }

dependencies {
    implementation(Libs.stdlib)
    api(Libs.Coroutines.core)
    api(Libs.Kodein.core)

    api(Libs.Exposed.core)
    api(Libs.Exposed.dao)
    api(Libs.Exposed.jdbc)

    implementation(Libs.logging)

    implementation("org.xerial:sqlite-jdbc:$sqliteJdbc")

    implementation(project(":dispatcher"))
    implementation(project(":initializer"))
    implementation(project(":model"))

    implementation(project(":ktlcnc:model"))
}
