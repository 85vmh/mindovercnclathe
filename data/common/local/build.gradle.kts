plugins { kotlin("jvm") }

version = Versions.app

dependencies {
    implementation(Libs.stdlib)
    implementation(project(":data:common:api"))
    implementation(project(":logger"))
    implementation(project(":dispatcher"))
    implementation(project(":ktlcnc"))
    implementation(project(":backend:database"))
    implementation(project(":model"))
    implementation(project(":protos"))
    implementation(project(":editor"))
    implementation(Libs.okio)

    // logging
    implementation(Libs.logging)
}
