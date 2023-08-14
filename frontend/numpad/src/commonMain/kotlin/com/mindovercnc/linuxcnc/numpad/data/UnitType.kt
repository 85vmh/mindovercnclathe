package com.mindovercnc.linuxcnc.numpad.data

enum class UnitType(val value: String) {
    DEGREES("°"),
    REV_PER_MIN("rev/min"),
    MM_PER_MIN("mm/min"),
    MM_PER_REV("mm/rev"),
    MM("mm"),
    M_PER_MIN("m/min"),

    /**
     * TODO change this to a metric size.
     */
    DIAMETER("diameter"),

}
