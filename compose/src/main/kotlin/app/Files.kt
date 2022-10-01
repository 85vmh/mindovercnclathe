package app

import java.io.File

object Files {

    private val homeFile = File(System.getProperty("user.home"))

    /**
     * This hidden folder will contain the app specific files
     */
    val appDir = File(homeFile, ".mindovercnclathe").apply {
        mkdirs()
    }

}