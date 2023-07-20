plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()
    js(IR)

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.Coroutines.core)
                implementation(Libs.logging)
            }
        }
    }
}