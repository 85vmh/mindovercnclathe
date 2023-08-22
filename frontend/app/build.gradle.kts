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
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.stdlib)
                implementation(Libs.Coroutines.core)
                implementation(Libs.Serialization.json)
                implementation(Libs.cli)
                implementation(Libs.datetime)

                // logging
                implementation(Libs.Log.logback)
                implementation(Libs.Log.logging)

                // okio
                implementation(Libs.okio)

                // compose
                implementation(compose.material3)


                // internal modules
                implementation(project(":frontend:clipboard"))
                implementation(project(":frontend:compose"))
                implementation(project(":frontend:di"))
                implementation(project(":frontend:domain"))

                implementation(project(":dispatcher"))
                implementation(project(":editor"))
                // todo uncomment
//                implementation(project(":data:impl"))
                implementation(project(":model"))

                implementation(project(":data:common:impl"))
                implementation(project(":data:linuxcnc:api"))
                implementation(project(":data:common:api"))
                implementation(project(":data:gcode:api"))
                implementation(project(":data:lathehal:api"))

                implementation(project(":startup:args"))

                implementation(project(":protos"))
                implementation(Libs.Grpc.okhttp)

                //    implementation(project(":vtk"))
                implementation(Libs.Kodein.compose)

                // navigation
                implementation("cafe.adriel.voyager:voyager-navigator:${Versions.voyager}")
                implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:${Versions.voyager}")
                implementation("cafe.adriel.voyager:voyager-tab-navigator:${Versions.voyager}")
                implementation("cafe.adriel.voyager:voyager-transitions:${Versions.voyager}")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(project(":startup:args"))

                implementation(project(":data:linuxcnc:local"))
                implementation(project(":data:linuxcnc:remote"))

                implementation(project(":data:lathetools:local"))
                implementation(project(":data:lathetools:remote"))

                implementation(project(":data:lathehal:local"))
                implementation(project(":data:lathehal:remote"))

                implementation(project(":data:gcode:local"))
                implementation(project(":data:gcode:remote"))

                implementation(project(":data:settings:local"))
                implementation(project(":data:settings:remote"))

                implementation(project(":initializer"))

                implementation(Libs.Coroutines.swing)

                //compose
                implementation(compose.desktop.currentOs)
                implementation(compose.uiTooling)

                // todo remove
                implementation(Libs.Compose.splitpane)

                // the library that contains the JNI interface for communicating with LinuxCNC library
                implementation(project(":ktlcnc"))

                implementation(project(":backend:database"))
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(project(":data:gcode:remote"))
                implementation(project(":data:linuxcnc:remote"))
                implementation(project(":data:settings:remote"))
                implementation(project(":data:lathetools:remote"))
                implementation(project(":data:lathehal:remote"))
            }
        }

        val jvmTest by getting {
            dependencies {
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.uiTestJUnit4)
                implementation(Libs.mockk)
                implementation(Libs.Coroutines.test)
            }
        }
    }
}


compose {
    desktop {
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
    experimental {
        web.application {}
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}
