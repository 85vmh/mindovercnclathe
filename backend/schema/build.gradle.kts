plugins {
    kotlin("multiplatform")
    id("app.cash.sqldelight")
}

kotlin {
    jvm()
    js(IR) { browser() }

    sourceSets {
        val commonMain by getting {}

        val jvmMain by getting {
            dependencies { implementation("app.cash.sqldelight:sqlite-driver:2.0.1") }
        }
    }
}

sqldelight { databases { create("KtCncDatabase") { packageName.set("com.mindovercnc.ktcnc") } } }
