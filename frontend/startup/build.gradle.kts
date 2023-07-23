plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

version = Versions.app

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.Coroutines.core)
                implementation(Libs.Serialization.json)
                implementation(Libs.cli)
            }
        }
    }
}
