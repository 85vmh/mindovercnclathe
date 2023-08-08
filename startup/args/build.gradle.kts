plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm()
    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.cli)
                implementation(Libs.logging)
                implementation(Libs.okio)
                implementation(compose.ui)
            }
        }
    }
}
