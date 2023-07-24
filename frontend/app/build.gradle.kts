import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

version = Versions.app

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
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
                @OptIn(ExperimentalComposeLibrary::class) implementation(compose.material3)

                // jars
                implementation(fileTree(mapOf("dir" to "lib", "include" to listOf("*.jar"))))

                // the library that contains the JNI interface for communicating with LinuxCNC library
                implementation(project(":ktlcnc"))

                // internal modules
                implementation(project(":clipboard"))
                implementation(project(":compose"))
                implementation(project(":database"))
                implementation(project(":dispatcher"))
                implementation(project(":editor"))
                implementation(project(":data:impl"))
                implementation(project(":model"))
                implementation(project(":data:repository"))
                implementation(project(":startup:args"))

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
            }
        }

        val commonTest by getting {
            dependencies {
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.uiTestJUnit4)
                implementation("io.mockk:mockk:1.12.4")
                implementation(Libs.Coroutines.test)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(project(":startup:args"))
                implementation(project(":data:legacy"))
                implementation(project(":initializer"))
            }
        }
    }
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