sealed interface Communication {
    data object Local : Communication
    data class Remote(val host: String) : Communication
}
