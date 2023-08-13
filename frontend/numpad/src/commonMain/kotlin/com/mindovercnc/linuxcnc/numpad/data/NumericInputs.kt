package com.mindovercnc.linuxcnc.numpad.data

object NumericInputs {

    @Deprecated("No longer needed, use the enum instead")
    val entries: Map<InputType, NumInputParameters> = createMap()

    /**
     * Creates a map with with parameters for every [InputType].
     *
     * TODO replace this, it's really hard to read.
     */
    @Deprecated("No longer needed, use the enum instead")
    fun createMap() = InputType.entries.associateWith { it }
}