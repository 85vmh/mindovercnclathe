plugins { kotlin("multiplatform") }

version = Versions.app

kotlin {
    jvm()
    js(IR)

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.stdlib)
                implementation(project(":data:repository"))
                implementation(project(":logger"))
                implementation(project(":dispatcher"))
                implementation(project(":database"))
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
