import Versions.exposed

plugins {
    kotlin("jvm")
}

version = "unspecified"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation(project(":repository"))
    implementation(project(":logger"))
    implementation("ro.dragossusi.ktlcnc:ktlcnc:0.0.1")
    implementation(project(":database"))
    implementation(project(":model"))
}