plugins { kotlin("jvm") }

version = Versions.app

dependencies {
    implementation(Libs.stdlib)
    implementation(Libs.Coroutines.core)
    implementation(Libs.Kodein.core)
    implementation(Libs.Settings.core)
    implementation(Libs.Settings.coroutines)
    implementation(project(":data:settings:api"))

    // logging
    implementation(Libs.logging)
}
