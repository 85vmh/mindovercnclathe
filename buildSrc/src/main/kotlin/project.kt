import org.gradle.api.Project

fun Project.getString(key: String): String? = property(key)?.toString()

fun Project.requirePath(
    env: String,
    property: String
): String {
    return System.getenv(env)
        ?: rootProject.getString(property)
        ?: throw IllegalStateException("$env not set")
}