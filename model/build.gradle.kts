plugins { kotlin("jvm") }

version = Versions.app

dependencies {
  implementation(Libs.stdlib)
  implementation(Libs.Coroutines.core)
  implementation(Libs.ktlcnc)
  implementation(Libs.okio)
}
