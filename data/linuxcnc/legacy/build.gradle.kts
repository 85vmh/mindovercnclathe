plugins { kotlin("jvm") }

version = Versions.app

dependencies {
    implementation(Libs.stdlib)
    implementation(project(":data:linuxcnc:api"))
    implementation(project(":logger"))
    implementation(project(":dispatcher"))
    implementation(project(":ktlcnc"))
    implementation(project(":model"))
    implementation(project(":protos"))
    implementation(Libs.okio)

    // logging
    implementation(Libs.Log.logging)
}
