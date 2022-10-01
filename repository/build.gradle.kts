import Versions.coroutines
import Versions.kodein

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
    implementation("org.kodein.di:kodein-di-framework-compose:$kodein")

    implementation(project(":database"))
    api(project(":model"))
    api("ro.dragossusi.ktlcnc:ktlcnc-model:0.0.1")

}