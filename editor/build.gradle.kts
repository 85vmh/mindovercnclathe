plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

version = Versions.app

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}")
    implementation(project(":repository"))
//    implementation(project(":libcnc"))
    implementation("ro.dragossusi.ktlcnc:ktlcnc:0.0.1")
    implementation(project(":database"))
}