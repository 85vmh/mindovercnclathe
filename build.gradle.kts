buildscript {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")

        gradlePluginPortal()
    }
}

plugins {
    kotlin("jvm") version Versions.kotlin apply false
    kotlin("plugin.serialization") version Versions.kotlin apply false
    id("org.jetbrains.compose") version Versions.compose apply false
    id("com.google.protobuf") version Versions.Grpc.plugin apply false
    id("com.squareup.wire") version Versions.Grpc.Wire.plugin apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")
    }
}
