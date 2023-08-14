import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

version = Versions.app

kotlin {
    jvm()
    js(IR) { browser() }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.stdlib)
                implementation(Libs.Coroutines.core)
                implementation(Libs.Serialization.json)

                // logging
                implementation(Libs.logging)

                // okio
                implementation(Libs.okio)

                implementation(Libs.datetime)
                implementation(Libs.bignum)

                implementation(project(":frontend:format"))

                // compose
                //                implementation(compose.uiTooling)
                implementation(compose.material)
                implementation(compose.material3)
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }
    }
}
