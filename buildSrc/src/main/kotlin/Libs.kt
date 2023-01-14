object Libs {
  const val okio = "com.squareup.okio:okio:${Versions.okio}"

  object Coroutines {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val swing = "org.jetbrains.kotlinx:kotlinx-coroutines-swing:${Versions.coroutines}"
    const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
  }

  object Serialization {
    const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"
    const val json_okio =
      "org.jetbrains.kotlinx:kotlinx-serialization-json-okio:${Versions.serialization}"
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

  const val cli = "org.jetbrains.kotlinx:kotlinx-cli:${Versions.cli}"

  const val ktlcnc = "ro.dragossusi.ktlcnc:ktlcnc:${Versions.ktlcnc}"
  const val ktlcnc_model = "ro.dragossusi.ktlcnc:ktlcnc-model:${Versions.ktlcnc}"

  const val logging = "io.github.microutils:kotlin-logging:${Versions.logging}"

  const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib"
}
