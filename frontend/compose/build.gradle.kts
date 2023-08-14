import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

version = Versions.app

kotlin {
    jvm()
    js(IR) { browser() }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.stdlib)
                implementation(Libs.Coroutines.core)
                implementation(Libs.Serialization.json)

                // logging
                implementation(Libs.logging)

                // okio
                implementation(Libs.okio)

                implementation(Libs.datetime)

                // compose
                //                implementation(compose.uiTooling)
                implementation(compose.material)
                implementation(compose.material3)
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                // internal modules
                implementation(project(":frontend:clipboard"))
                implementation(project(":actor"))
                implementation(project(":dispatcher"))
                implementation(project(":editor"))
                implementation(project(":model"))
                implementation(project(":initializer"))

                implementation(project(":data:common:api"))
                implementation(project(":data:linuxcnc:api"))
                implementation(project(":data:tools:api"))
                implementation(project(":data:gcode:api"))
                implementation(project(":data:settings:api"))

                implementation(project(":frontend:breadcrumb"))
                implementation(project(":frontend:filesystem"))
                implementation(project(":frontend:scroll"))
                implementation(project(":frontend:editor"))
                implementation(project(":frontend:widgets"))
                implementation(project(":frontend:listitem"))
                implementation(project(":frontend:numpad"))
                implementation(project(":frontend:format"))

                implementation(project(":startup:args"))

                implementation(project(":protos"))
                implementation(Libs.Grpc.okhttp)
                //                implementation(Libs.Compose.splitpane)

                //    implementation(project(":vtk"))
                implementation(Libs.Kodein.compose)

                implementation(Libs.bignum)

                // navigation
                implementation(Libs.Voyager.navigator)
                implementation(Libs.Voyager.bottom_sheet_navigator)
                implementation(Libs.Voyager.tab_navigator)
                implementation(Libs.Voyager.transitions)
            }
        }

        val jvmMain by getting { dependencies { implementation(compose.desktop.currentOs) } }
        val jvmTest by getting {
            dependencies {
                @OptIn(ExperimentalComposeLibrary::class) implementation(compose.uiTestJUnit4)
                implementation(Libs.mockk)
                implementation(Libs.Coroutines.test)
                implementation(compose.material)
            }
        }
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}
