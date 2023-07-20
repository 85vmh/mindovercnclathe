package com.mindovercnc.model

data class JoystickStatus(
    val position: Position,
    val isRapid: Boolean = false
) {

    enum class Position(
        val axis: Axis?,
        val direction: Direction?
    ) {
        Neutral(null, null),
        XPlus(Axis.X, Direction.Positive),
        XMinus(Axis.X, Direction.Negative),
        ZPlus(Axis.Z, Direction.Positive),
        ZMinus(Axis.Z, Direction.Negative);
    }
}