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
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:${Versions.coroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-cli:${Versions.cli}")

    //compose
    implementation(compose.desktop.currentOs)
    implementation(compose.uiTooling)
    @OptIn(ExperimentalComposeLibrary::class)
    implementation(compose.material3)

    //jars
    implementation(fileTree(mapOf("dir" to "lib", "include" to listOf("*.jar"))))

    //the library that contains the JNI interface for communicating with LinuxCNC library
    implementation("ro.dragossusi.ktlcnc:ktlcnc:0.0.1")

    //internal modules
    implementation(project(":repository"))
    implementation(project(":database"))
    implementation(project(":impl"))
    implementation(project(":editor"))
    implementation(project(":model"))
//    implementation(project(":vtk"))
    implementation("org.kodein.di:kodein-di-framework-compose:${Versions.kodein}")

    //navigation
    implementation("cafe.adriel.voyager:voyager-navigator:${Versions.voyager}")
    implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:${Versions.voyager}")
    implementation("cafe.adriel.voyager:voyager-tab-navigator:${Versions.voyager}")
    implementation("cafe.adriel.voyager:voyager-transitions:${Versions.voyager}")

    // State Machine
    implementation("io.github.nsk90:kstatemachine:0.9.4")

    testImplementation(compose("org.jetbrains.compose.ui:ui-test-junit4"))
    testImplementation("io.mockk:mockk:1.12.4")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
}

compose.desktop {
    application {
        mainClass = "MainKt"
        jvmArgs("-Djava.library.path=${NativePaths.getNativePaths(rootProject).joinToString(":")}")
        nativeDistributions {
            targetFormats(TargetFormat.Deb)
        }
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}