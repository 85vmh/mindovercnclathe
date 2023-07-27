plugins { kotlin("multiplatform") }

version = Versions.app

kotlin {
    jvm()
    js(IR)

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.stdlib)
                implementation(Libs.Kodein.core)
                implementation(Libs.Settings.core)
                implementation(Libs.Settings.coroutines)
                implementation(project(":data:repository"))
                implementation(project(":logger"))
                implementation(project(":dispatcher"))
//                implementation(project(":database"))
                implementation(project(":model"))
                implementation(project(":protos"))
                implementation(project(":editor"))
                implementation(Libs.okio)

                // logging
                implementation(Libs.logging)
            }
        }
    }
}
