plugins { kotlin("jvm") }

version = Versions.app

dependencies {
    implementation(Libs.stdlib)
    implementation(Libs.Kodein.core)
    implementation(Libs.Settings.core)
    implementation(Libs.Settings.coroutines)
    implementation(project(":data:gcode:api"))
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
