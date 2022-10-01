plugins {
    //id("multiplatform-library-convention")
    kotlin("multiplatform")
    id("dev.icerock.mobile.multiplatform-resources")
//    id("detekt-convention")
}

apply(plugin = "dev.icerock.mobile.multiplatform-resources")

version = "unspecified"

repositories {
    mavenCentral()
}

kotlin {
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */

    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("dev.icerock.moko:resources:0.17.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}