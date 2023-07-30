package screen.uimodel

enum class CuttingStrategy(val text: String) {
    Roughing("Roughing"),
    Finishing("Finishing"),
    RoughingAndFinishing("Roughing & Finishing");

    val isRoughing get() = this == Roughing || this == RoughingAndFinishing
    val isFinishing get() = this == Finishing || this == RoughingAndFinishing

    companion object {
        fun fromText(text: String): CuttingStrategy? {
            return entries.find { it.text == text }
        }
    }
}