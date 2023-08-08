package screen.uimodel

enum class WorkpieceMaterial(val text: String) {
    Plastics("Plastics"),
    Aluminium("Aluminium"),
    Brass("Brass"),
    LowCarbonSteel("Low carbon steel"),
    HighCarbonSteel("High carbon steel");

    companion object {
        fun fromString(text: String): WorkpieceMaterial? {
            return entries.find { it.text == text }
        }
    }
}