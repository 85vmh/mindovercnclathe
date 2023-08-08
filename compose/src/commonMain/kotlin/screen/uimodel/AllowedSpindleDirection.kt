package screen.uimodel

enum class AllowedSpindleDirection(val text: String) {
    CW("Forward"),
    CCW("Reverse"),
    BOTH("Both");

    companion object {
        fun fromString(text: String): AllowedSpindleDirection? {
            return entries.find { it.text == text }
        }
    }
}