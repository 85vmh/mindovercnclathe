import org.jetbrains.compose.compose

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

version = Versions.app

dependencies {
    implementation(Libs.stdlib)

    //compose
    implementation(compose.desktop.currentOs)
    implementation(compose.uiTooling)
    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
    implementation(compose.material3)

    implementation(project(":model"))

    val vtkJarPath = project.requirePath("VTK_JAR", "vtk.jar")
    implementation(files(vtkJarPath))
}