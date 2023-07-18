plugins { kotlin("multiplatform") }

version = Versions.app

kotlin {
    jvm()
    js(IR)

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.Coroutines.core)
                implementation(Libs.Kodein.core)
            }
        }
    }
}