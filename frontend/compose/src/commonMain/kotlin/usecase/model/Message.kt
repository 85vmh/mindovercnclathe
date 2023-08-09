package usecase.model

data class Message(
    val text: String,
    val level: Level = Level.INFO,
) {
    enum class Level {
        INFO, WARNING, ERROR, DEBUG
    }
}