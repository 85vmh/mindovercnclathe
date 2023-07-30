package screen.uimodel

enum class CutDirection {
    Longitudinal,
    Transversal;

    companion object {
        fun fromString(text: String): CutDirection? {
            return entries.find { it.name == text }
        }
    }
}