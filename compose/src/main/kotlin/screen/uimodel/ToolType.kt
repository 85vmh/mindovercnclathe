package screen.uimodel

enum class ToolType(val text: String) {
    PROFILING("Profiling"),
    PARTING_GROOVING("Parting/Grooving"),
    DRILLING_REAMING("Drilling/Reaming"),
    THREADING("Threading"),
    KEY_SLOTTING("Key Slotting");

    companion object {
        fun fromString(text: String): ToolType? {
            return values().find { it.text == text }
        }
    }
}