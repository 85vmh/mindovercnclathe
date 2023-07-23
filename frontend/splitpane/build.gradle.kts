import org.jetbrains.compose.compose

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

            }
        }

        val commonTest by getting {
            dependencies {
                implementation(compose("org.jetbrains.compose.ui:ui-test-junit4"))
                implementation("io.mockk:mockk:1.12.4")
                implementation(Libs.Coroutines.test)
            }
        }

        val jvmMain by getting {
            dependencies{
                implementation(Libs.Compose.splitpane)
            }
        }
    }
}
