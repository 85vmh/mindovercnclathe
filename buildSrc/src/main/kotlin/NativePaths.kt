import org.gradle.api.Project
import java.io.File

object NativePaths {
    fun getNativePaths(rootProject: Project): List<String> {

        val linuxCncHome = rootProject.requirePath(
            "LINUXCNC_HOME",
            "linuxcnc.home"
        )

        val linuxCncJdk = rootProject.requirePath(
            "LINUXCNC_JDK",
            "linuxcnc.jdk"
        )

        val vtkLib = rootProject.requirePath(
            "VTK_LIB",
            "vtk.lib"
        )

        return listOf(
            File(linuxCncHome, "lib").path,
            File(linuxCncJdk, "lib").path,
            vtkLib
        )
    }

}