object Libs {
    const val okio = "com.squareup.okio:okio:${Versions.okio}"
    const val wire = "com.squareup.wire:wire-grpc-client:${Versions.wire}"

    const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.Kotlinx.datetime}"

    const val bignum = "com.ionspin.kotlin:bignum:${Versions.bignum}"

    const val mockk = "io.mockk:mockk:${Versions.mockk}"

    object Decompose {
        const val core = "com.arkivanov.decompose:decompose:${Versions.decompose}"
        const val extensions = "com.arkivanov.decompose:extensions-compose-jetbrains:${Versions.decompose}"
    }

    object Roborazzi {
        const val core = "io.github.takahirom.roborazzi:roborazzi:${Versions.roborazzi}"
        const val compose_desktop =
            "io.github.takahirom.roborazzi:roborazzi-compose-desktop:${Versions.roborazzi}"
        const val junit_rule =
            "io.github.takahirom.roborazzi:roborazzi-junit-rule:${Versions.roborazzi}"
    }

    object Settings {
        const val core = "com.russhwolf:multiplatform-settings:${Versions.settings}"
        const val coroutines =
            "com.russhwolf:multiplatform-settings-coroutines:${Versions.settings}"
    }

    object Compose {
        const val splitpane =
            "org.jetbrains.compose.components:components-splitpane:${Versions.compose}"
    }

    object Coroutines {
        const val core =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlinx.coroutines}"
        const val swing =
            "org.jetbrains.kotlinx:kotlinx-coroutines-swing:${Versions.Kotlinx.coroutines}"
        const val test =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Kotlinx.coroutines}"
    }

    object Serialization {
        const val json =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Kotlinx.serialization}"
        const val json_okio =
            "org.jetbrains.kotlinx:kotlinx-serialization-json-okio:${Versions.Kotlinx.serialization}"
    }

    object Kodein {
        const val compose = "org.kodein.di:kodein-di-framework-compose:${Versions.kodein}"
        const val core = "org.kodein.di:kodein-di:${Versions.kodein}"
    }

    object Exposed {
        const val core = "org.jetbrains.exposed:exposed-core:${Versions.exposed}"
        const val dao = "org.jetbrains.exposed:exposed-dao:${Versions.exposed}"
        const val jdbc = "org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}"
    }

    object Grpc {
        const val grpc_kotlin = "io.grpc:grpc-kotlin-stub:${Versions.Grpc.grpc_kotlin}"
        const val grpc_proto = "io.grpc:grpc-protobuf:${Versions.Grpc.grpc}"
        const val proto_kotlin =
            "com.google.protobuf:protobuf-kotlin:${Versions.Grpc.protobuf_kotlin}"
        const val okhttp = "io.grpc:grpc-okhttp:${Versions.Grpc.grpc}"
    }

    object Voyager {
        const val navigator = "cafe.adriel.voyager:voyager-navigator:${Versions.voyager}"
        const val tab_navigator = "cafe.adriel.voyager:voyager-tab-navigator:${Versions.voyager}"
        const val transitions = "cafe.adriel.voyager:voyager-transitions:${Versions.voyager}"
        const val bottom_sheet_navigator =
            "cafe.adriel.voyager:voyager-bottom-sheet-navigator:${Versions.voyager}"
    }

    const val cli = "org.jetbrains.kotlinx:kotlinx-cli:${Versions.Kotlinx.cli}"

    object Log {
        const val logging = "io.github.microutils:kotlin-logging:${Versions.Log.logging}"
        const val logback = "ch.qos.logback:logback-classic:${Versions.Log.logback}"
    }

    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val test = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
}
