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
                implementation("ch.qos.logback:logback-classic:1.4.8")
                implementation(Libs.logging)

                // okio
                implementation(Libs.okio)

                // compose
                implementation(compose.material3)


                // internal modules
                implementation(project(":clipboard"))
                implementation(project(":compose"))
                implementation(project(":dispatcher"))
                implementation(project(":editor"))
                // todo uncomment
//                implementation(project(":data:impl"))
                implementation(project(":model"))
                implementation(project(":data:linuxcnc:api"))
                implementation(project(":data:common:api"))
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

        val jvmTest by getting {
            dependencies {
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.uiTestJUnit4)
                implementation(Libs.mockk)
                implementation(Libs.Coroutines.test)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(project(":startup:args"))
                implementation(project(":data:common:impl"))
                implementation(project(":data:linuxcnc:legacy"))
                implementation(project(":data:linuxcnc:remote"))
                implementation(project(":initializer"))

                implementation(Libs.Coroutines.swing)

                //compose
                implementation(compose.desktop.currentOs)
                implementation(compose.uiTooling)

                // todo remove
                implementation(Libs.Compose.splitpane)

                // the library that contains the JNI interface for communicating with LinuxCNC library
                implementation(project(":ktlcnc"))

                implementation(project(":database"))
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
