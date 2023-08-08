package usecase.model

data class ActiveCode(
    val value: Float,
    val stringRepresentation: String,
    val type: Type
) {
    enum class Type {
        GCode, MCode
    }
}