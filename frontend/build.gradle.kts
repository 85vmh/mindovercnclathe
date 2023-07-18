import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
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
    implementation(Libs.logging)

    // okio
    implementation(Libs.okio)

    // compose
    implementation(compose.desktop.currentOs)
    implementation(compose.uiTooling)
    @OptIn(ExperimentalComposeLibrary::class) implementation(compose.material3)

    // jars
    implementation(fileTree(mapOf("dir" to "lib", "include" to listOf("*.jar"))))

    // the library that contains the JNI interface for communicating with LinuxCNC library
    implementation(project(":ktlcnc"))

    // internal modules
    implementation(project(":clipboard"))
    implementation(project(":database"))
    implementation(project(":dispatcher"))
    implementation(project(":editor"))
    implementation(project(":data:impl"))
    implementation(project(":model"))
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

compose.desktop {
    application {
        mainClass = "MainKt"

        jvmArgs(NativePaths.createJvmArgs(rootProject))

        nativeDistributions {
            // needed by the database
            modules("java.sql")
            targetFormats(TargetFormat.Deb)
        }
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}
