plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

version = Versions.app

kotlin {
    jvm()
    js()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.Coroutines.core)
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.material3)
            }
        }
    }
}
