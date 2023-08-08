import org.jetbrains.compose.compose
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

                // logging
                implementation(Libs.logging)

                // okio
                implementation(Libs.okio)

                implementation(Libs.datetime)

                // compose
//                implementation(compose.uiTooling)
                implementation(compose.material)
                implementation(compose.material3)

                // internal modules
                implementation(project(":clipboard"))
                implementation(project(":actor"))
                implementation(project(":dispatcher"))
                implementation(project(":editor"))
                implementation(project(":model"))
                implementation(project(":initializer"))

                implementation(project(":data:common:api"))
                implementation(project(":data:linuxcnc:api"))

                implementation(project(":frontend:breadcrumb"))
                implementation(project(":frontend:filesystem"))
                implementation(project(":frontend:scroll"))
                implementation(project(":frontend:editor"))

                implementation(project(":startup:args"))

                implementation(project(":protos"))
                implementation(Libs.Grpc.okhttp)
//                implementation(Libs.Compose.splitpane)

                //    implementation(project(":vtk"))
                implementation(Libs.Kodein.compose)

                implementation("com.ionspin.kotlin:bignum:0.3.8")

                // navigation
                implementation("cafe.adriel.voyager:voyager-navigator:${Versions.voyager}")
                implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:${Versions.voyager}")
                implementation("cafe.adriel.voyager:voyager-tab-navigator:${Versions.voyager}")
                implementation("cafe.adriel.voyager:voyager-transitions:${Versions.voyager}")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(compose("org.jetbrains.compose.ui:ui-test-junit4"))
                implementation("io.mockk:mockk:1.12.4")
                implementation(Libs.Coroutines.test)
                implementation(compose.material)
            }
        }
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}
