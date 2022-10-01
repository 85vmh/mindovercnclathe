import Versions.coroutines
import Versions.exposed
import Versions.kodein
import Versions.sqliteJdbc

plugins {
    kotlin("jvm")
}

version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")
    implementation("org.kodein.di:kodein-di-framework-compose:${kodein}")

    api("org.jetbrains.exposed:exposed-core:$exposed")
    api("org.jetbrains.exposed:exposed-dao:$exposed")
    api("org.jetbrains.exposed:exposed-jdbc:$exposed")

    implementation("org.xerial:sqlite-jdbc:$sqliteJdbc")
    implementation(project(":model"))
    implementation("ro.dragossusi.ktlcnc:ktlcnc-model:0.0.1")

}