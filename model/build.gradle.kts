plugins {
    kotlin("jvm")
}

version = Versions.app

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
    implementation("ro.dragossusi.ktlcnc:ktlcnc:0.0.1")
}