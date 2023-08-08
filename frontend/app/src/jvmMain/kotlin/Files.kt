import okio.Path.Companion.toPath

object Files {

  private val homeFile = System.getProperty("user.home").toPath()

  /** This hidden folder will contain the app specific files */
  val appDir = homeFile.div(".mindovercnclathe")
}
