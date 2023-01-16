buildscript {
  repositories {
    google()
    mavenCentral()
    mavenLocal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://jitpack.io")

    gradlePluginPortal()
  }

  dependencies {
    // compose
    //    classpath("org.jetbrains.compose:compose-gradle-plugin:${Versions.compose}")
  }
}

plugins {
  kotlin("jvm") version Versions.kotlin apply false
  kotlin("plugin.serialization") version Versions.kotlin apply false
  id("org.jetbrains.compose") version Versions.compose apply false
  id("com.google.protobuf") version Versions.Grpc.plugin apply false
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

// tasks.create<Exec>("cleanNatives") {
//    val path = projectDir.path + "/native"
//    //commandLine("make clean -C $path")
//    commandLine("echo", "ceva bun")
// }
//
// tasks.create<Exec>("buildNatives") {
//    val path = projectDir.path + "/native"
//    commandLine("make -C $path")
// }
