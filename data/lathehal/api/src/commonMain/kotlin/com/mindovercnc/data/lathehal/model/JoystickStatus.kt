package com.mindovercnc.data.lathehal.model

import com.mindovercnc.model.Axis
import com.mindovercnc.model.Direction

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