plugins { kotlin("jvm") }

version = Versions.app

dependencies {
    implementation(Libs.stdlib)
    implementation(project(":data:repository"))
    implementation(project(":logger"))
    implementation(project(":dispatcher"))
    implementation(project(":ktlcnc"))
    implementation(project(":database"))
    implementation(project(":model"))
    implementation(project(":protos"))
    implementation(project(":editor"))
    implementation(Libs.okio)

    // logging
    implementation(Libs.logging)
}
