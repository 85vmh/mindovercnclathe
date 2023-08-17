plugins { kotlin("multiplatform") }

version = Versions.app

kotlin {
    jvm()
    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.stdlib)
                implementation(Libs.datetime)
                api(Libs.Coroutines.core)
                implementation(Libs.Kodein.core)

//                implementation(project(":database"))
                implementation(project(":editor"))

                api(project(":model"))

                api(project(":protos"))
                implementation(Libs.okio)
            }
        }
    }
}
