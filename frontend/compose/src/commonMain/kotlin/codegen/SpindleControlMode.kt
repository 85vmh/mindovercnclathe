package codegen

sealed class SpindleControlMode(val gCode: String) {
    data class CSS(val css: Int, val maxRpm: Int) : SpindleControlMode("G96")
    data class RPM(val rpm: Int) : SpindleControlMode("G97")
}