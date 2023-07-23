import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

version = Versions.app

dependencies {
    implementation(Libs.stdlib)
    implementation(Libs.Coroutines.core)
    implementation(Libs.Coroutines.swing)
    implementation(Libs.Serialization.json)
    implementation(Libs.cli)

    // logging
    implementation("ch.qos.logback:logback-classic:1.4.8")
    implementation(Libs.logging)

    // okio
    implementation(Libs.okio)

    // compose
    implementation(compose.desktop.currentOs)
    implementation(compose.uiTooling)
    implementation(compose.material3)


    // the library that contains the JNI interface for communicating with LinuxCNC library
    implementation(project(":ktlcnc"))

    // internal modules
    implementation(project(":clipboard"))
    implementation(project(":actor"))
    implementation(project(":database"))
    implementation(project(":dispatcher"))
    implementation(project(":editor"))
    implementation(project(":model"))
    implementation(project(":initializer"))

    implementation(project(":data:impl"))
    implementation(project(":data:legacy"))
    implementation(project(":data:repository"))

    implementation(project(":protos"))
    implementation(Libs.Grpc.okhttp)
    implementation(Libs.Compose.splitpane)

    //    implementation(project(":vtk"))
    implementation(Libs.Kodein.compose)

    // navigation
    implementation("cafe.adriel.voyager:voyager-navigator:${Versions.voyager}")
    implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:${Versions.voyager}")
    implementation("cafe.adriel.voyager:voyager-tab-navigator:${Versions.voyager}")
    implementation("cafe.adriel.voyager:voyager-transitions:${Versions.voyager}")

    // State Machine
    implementation("io.github.nsk90:kstatemachine:0.9.4")

    testImplementation(compose("org.jetbrains.compose.ui:ui-test-junit4"))
    testImplementation("io.mockk:mockk:1.12.4")
    testImplementation(Libs.Coroutines.test)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}
