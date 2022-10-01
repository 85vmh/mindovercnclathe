package screen.uimodel

enum class CutDirection {
    Longitudinal,
    Transversal;

    companion object {
        fun fromString(text: String): CutDirection? {
            return values().find { it.name == text }
        }
    }
}