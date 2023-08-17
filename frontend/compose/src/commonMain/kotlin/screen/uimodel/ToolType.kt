package screen.uimodel

@Deprecated("Not used, possible duplicate")
enum class ToolType(val text: String) {
    PROFILING("Profiling"),
    PARTING_GROOVING("Parting/Grooving"),
    DRILLING_REAMING("Drilling/Reaming"),
    THREADING("Threading"),
    KEY_SLOTTING("Key Slotting");

    companion object {
        fun fromString(text: String): ToolType? {
            return entries.find { it.text == text }
        }
    }
}
