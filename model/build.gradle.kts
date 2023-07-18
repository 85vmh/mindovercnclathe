plugins { kotlin("multiplatform") }

version = Versions.app

kotlin {
    jvm()
    js(IR)

    sourceSets {
        val commonMain by getting {

            dependencies {
                implementation(Libs.stdlib)
                implementation(Libs.Coroutines.core)
                implementation(project(":ktlcnc"))
                implementation(project(":protos"))
                implementation(Libs.okio)
            }
        }
    }
}
