import java.io.File
import org.gradle.api.Project

object NativePaths {
  fun getNativePaths(project: Project): List<String> {

    val linuxCncHome = project.requirePath("LINUXCNC_HOME", "linuxcnc.home")

    val linuxCncJdk = project.requirePath("LINUXCNC_JDK", "linuxcnc.jdk")

    val vtkLib = project.requirePath("VTK_LIB", "vtk.lib")

    return listOf(File(linuxCncHome, "lib").path, File(linuxCncJdk, "lib").path, vtkLib)
  }

  fun createJvmArgs(project: Project) =
    "-Djava.library.path=${NativePaths.getNativePaths(project).joinToString(":")}"
}
