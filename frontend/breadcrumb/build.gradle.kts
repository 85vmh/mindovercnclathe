plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

version = Versions.app

kotlin {
    jvm()
    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.Coroutines.core)
                implementation(Libs.Serialization.json)
                implementation(Libs.okio)

                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(project(":frontend:scroll"))
            }
        }
    }
}
