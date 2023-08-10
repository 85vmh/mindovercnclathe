package screen.uimodel

enum class Wcs(val text: String) {
    G54("G54"),
    G55("G55"),
    G56("G56"),
    G57("G57"),
    G58("G58"),
    G59("G59"),
    G59_1("G59.1"),
    G59_2("G59.2"),
    G59_3("G59.3");

    companion object {
        fun fromString(text: String): Wcs? {
            return entries.find { it.text == text }
        }
    }
}