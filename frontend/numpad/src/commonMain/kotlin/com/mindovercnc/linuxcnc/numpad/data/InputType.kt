package com.mindovercnc.linuxcnc.numpad.data

enum class InputType(
    override val description: String,
    override val unit: String? = null,
    override val minValue: Double = Double.MIN_VALUE,
    override val maxValue: Double = Double.MAX_VALUE,
    override val allowsNegativeValues: Boolean = false,
    override val maxDecimalPlaces: Int = 0,
    override val initialValue: Double = 0.0
) : NumInputParameters {
    RPM(
        description = "Spindle Speed",
        unit = "rev/min",
        minValue = 10.0,
        maxValue = 3000.0,
        initialValue = 100.0
    ),
    CSS(
        description = "Constant Surface Speed",
        unit = "m/min",
        minValue = 1.0,
        maxValue = 500.0,
        initialValue = 50.0
    ),
    CSS_MAX_RPM(
        description = "Spindle Max Speed",
        unit = "rev/min",
        minValue = 10.0,
        maxValue = 3000.0,
        initialValue = 100.0
    ),
    ORIENTED_STOP(
        description = "Oriented Stop Angle",
        unit = "degrees",
        minValue = 0.0,
        maxValue = 360.0,
        initialValue = 100.0,
        maxDecimalPlaces = 1
    ),
    FEED_PER_REV(
        description = "Feed per revolution",
        unit = "mm/rev",
        minValue = 0.010,
        maxValue = 5.000,
        initialValue = 0.100,
        maxDecimalPlaces = 3
    ),
    FEED_PER_MIN(
        description = "Feed per minute",
        unit = "mm/min",
        minValue = 10.0,
        maxValue = 500.0,
        initialValue = 10.0,
        maxDecimalPlaces = 1
    ),
    TOUCH_OFF_X(
        description = "Touch-Off X",
        unit = "diameter",
        minValue = 0.010,
        maxValue = 320.000,
        initialValue = 10.0,
        maxDecimalPlaces = 3
    ),
    TOUCH_OFF_Z(
        description = "Touch-Off Z",
        unit = "",
        minValue = 0.001,
        maxValue = 650.000,
        initialValue = 10.0,
        maxDecimalPlaces = 3
    ),
    TOOL_CLEARANCE(
        description = "Tool Clearance",
        unit = "mm",
        minValue = 0.1,
        maxValue = 100.000,
        initialValue = 1.0,
        maxDecimalPlaces = 3
    ),
    TOOL_NUMBER(
        description = "Tool #",
        minValue = 1.0,
        maxValue = 99.0,
        initialValue = 1.0,
    ),
    DOC(
        description = "Depth of cut",
        minValue = 0.05,
        maxValue = 5.0,
        initialValue = 1.0,
        maxDecimalPlaces = 3

    ),
    FINISH_PASSES(
        description = "Finish passes",
        minValue = 0.05,
        maxValue = 5.0,
        initialValue = 1.0,
    ),
    THREAD_STARTS(
        description = "Thread Starts",
        minValue = 1.0,
        maxValue = 10.0,
        initialValue = 1.0,
    ),
    THREAD_PITCH(
        description = "Thread Pitch",
        minValue = 0.5,
        maxValue = 10.0,
        initialValue = 1.0,
        maxDecimalPlaces = 2
    ),
    THREAD_X_START(
        description = "X Start",
        minValue = 0.0,
        maxValue = 300.0,
        initialValue = 0.0,
        maxDecimalPlaces = 3
    ),
    THREAD_MAJOR_DIAMETER(
        description = "Major Diameter",
        minValue = 0.0,
        maxValue = 300.0,
        initialValue = 0.0,
        maxDecimalPlaces = 3
    ),
    THREAD_MINOR_DIAMETER(
        description = "Minor Diameter",
        minValue = 0.0,
        maxValue = 300.0,
        initialValue = 0.0,
        maxDecimalPlaces = 3
    ),
    THREAD_FINAL_DEPTH(
        description = "Final depth (in radius measurement)",
        minValue = 0.0,
        maxValue = 300.0,
        initialValue = 0.0,
        maxDecimalPlaces = 3
    ),
    THREAD_Z_START(
        description = "Z Start",
        minValue = 0.0,
        maxValue = 600.0,
        initialValue = 0.0,
        maxDecimalPlaces = 3
    ),
    THREAD_Z_END(
        description = "Z End",
        minValue = 0.0,
        maxValue = 600.0,
        initialValue = 0.0,
        maxDecimalPlaces = 3
    ),
    THREAD_LEAD_LENGTH(
        description = "Lead Length",
        minValue = 0.0,
        maxValue = 20.0,
        initialValue = 3.0,
        maxDecimalPlaces = 3
    ),
    THREAD_SPRING_PASSES(
        description = "Spring Passes",
        minValue = 0.0,
        maxValue = 20.0,
        initialValue = 1.0,
    ),
    X_START(
        description = "Initial X",
        minValue = 0.0,
        maxValue = 300.0,
        initialValue = 0.0,
        allowsNegativeValues = true,
        maxDecimalPlaces = 3
    ),
    X_END(
        description = "Final X",
        minValue = 0.0,
        maxValue = 300.0,
        initialValue = 0.0,
        allowsNegativeValues = true,
        maxDecimalPlaces = 3
    ),
    Z_START(
        description = "Z Start",
        minValue = -500.0,
        maxValue = 500.0,
        initialValue = 0.0,
        allowsNegativeValues = true,
        maxDecimalPlaces = 3
    ),
    Z_END(
        description = "Z End",
        minValue = -500.0,
        maxValue = 500.0,
        initialValue = 0.0,
        allowsNegativeValues = true,
        maxDecimalPlaces = 3
    ),
    KEY_SLOT_DEPTH(
        description = "KeySlot Depth",
        minValue = 0.0,
        maxValue = 20.0,
        initialValue = 1.0,
        maxDecimalPlaces = 2
    ),
    FILLET_RADIUS(
        description = "Fillet Radius",
        unit = "mm",
        minValue = -500.0,
        maxValue = 500.0,
        initialValue = 0.0,
        maxDecimalPlaces = 3
    ),
    TOOL_X_COORDINATE(
        description = "Tool X Coordinate",
        unit = "mm",
        minValue = -500.0,
        maxValue = 500.0,
        initialValue = 0.0,
        allowsNegativeValues = true,
        maxDecimalPlaces = 3
    ),
    TOOL_Z_COORDINATE(
        description = "Tool Z Coordinate",
        unit = "mm",
        minValue = -500.0,
        maxValue = 500.0,
        initialValue = 0.0,
        allowsNegativeValues = true,
        maxDecimalPlaces = 3
    ),
    WORKPIECE_ZERO_COORDINATE(
        description = "ToolTip to Z0 distance",
        allowsNegativeValues = true,
        maxDecimalPlaces = 3
    ),
    TIP_RADIUS(
        description = "Tip Radius",
        unit = "mm",
        minValue = 0.0,
        maxValue = 20.0,
        initialValue = 0.0,
        maxDecimalPlaces = 1
    ),
    TIP_ANGLE(
        description = "Tip Angle",
        unit = "mm",
        minValue = 0.0,
        maxValue = 20.0,
        initialValue = 0.0,
    ),
    INSERT_SIZE(
        description = "Insert Size",
        unit = "mm",
        minValue = 0.0,
        maxValue = 20.0,
        initialValue = 0.0,
        maxDecimalPlaces = 1
    ),
    BLADE_WIDTH(
        description = "Blade Width",
        unit = "mm",
        minValue = 0.0,
        maxValue = 20.0,
        initialValue = 0.0,
        maxDecimalPlaces = 3
    ),
    TOOL_DIAMETER(
        description = "Diameter",
        unit = "mm",
        minValue = 0.0,
        maxValue = 50.0,
        initialValue = 0.0,
        maxDecimalPlaces = 3
    ),
    TAPER_ANGLE(
        description = "Taper Angle",
        unit = "degrees",
        minValue = 1.0,
        maxValue = 89.0,
        initialValue = 45.0,
        maxDecimalPlaces = 2
    ),
    VIRTUAL_LIMIT_X_MINUS(
        description = "X Minus Limit",
        allowsNegativeValues = true,
        maxDecimalPlaces = 3
    ),
    VIRTUAL_LIMIT_X_PLUS(
        description = "X Plus Limit",
        allowsNegativeValues = true,
        maxDecimalPlaces = 3
    ),
    VIRTUAL_LIMIT_Z_MINUS(
        description = "Z Minus Limit",
        allowsNegativeValues = true,
        maxDecimalPlaces = 3
    ),
    VIRTUAL_LIMIT_Z_PLUS(
        description = "Z Plus Limit",
        allowsNegativeValues = true,
        maxDecimalPlaces = 3
    ),
    DIAL_INDICATOR_DISTANCE(
        description = "Dial indicator distance",
        allowsNegativeValues = true,
        maxDecimalPlaces = 3
    ),
    TOOL_ID(
        description = "Tool Id",
        allowsNegativeValues = false,
        maxDecimalPlaces = 0
    ),
    TOOL_HOLDER_NO(
        description = "Tool Holder #",
        allowsNegativeValues = false,
        maxDecimalPlaces = 0
    ),
    FRONT_ANGLE(
        description = "Front Angle",
        allowsNegativeValues = false,
        maxDecimalPlaces = 0,
//            unit = "degrees"
    ),
    BACK_ANGLE(
        description = "Back Angle",
        allowsNegativeValues = false,
        maxDecimalPlaces = 0,
//            unit = "degrees"
    ),
    MIN_BORE_DIAMETER(
        description = "Min Bore Diameter",
        allowsNegativeValues = false,
        maxDecimalPlaces = 0,
//            unit = "degrees"
    ),
    MAX_X_DEPTH(
        description = "Max X Depth",
        allowsNegativeValues = false,
        maxDecimalPlaces = 1,
//            unit = "degrees"
    ),
    MAX_Z_DEPTH(
        description = "Max Z Depth",
        allowsNegativeValues = false,
        maxDecimalPlaces = 1,
//            unit = "degrees"
    ),
    THREADING_MIN_PITCH(
        description = "Min Pitch",
        allowsNegativeValues = false,
        maxDecimalPlaces = 2,
//            unit = "degrees"
    ),
    THREADING_MAX_PITCH(
        description = "Max Pitch",
        allowsNegativeValues = false,
        maxDecimalPlaces = 2,
//            unit = "degrees"
    ),
    MIN_AP(
        description = "Min - Depth of Cut (ap)",
        allowsNegativeValues = false,
        maxDecimalPlaces = 2,
    ),
    MAX_AP(
        description = "Max - Depth of Cut (ap)",
        allowsNegativeValues = false,
        maxDecimalPlaces = 2,
        unit = "mm"
    ),
    MIN_FN(
        description = "Min - Feed per rev (ap)",
        allowsNegativeValues = false,
        maxDecimalPlaces = 2,
    ),
    MAX_FN(
        description = "Max - Feed per rev (ap)",
        allowsNegativeValues = false,
        maxDecimalPlaces = 2,
        unit = "mm/rev"
    ),
    MIN_VC(
        description = "Min - Cutting speed (vc)",
        allowsNegativeValues = false,
        maxDecimalPlaces = 2,
    ),
    MAX_VC(
        description = "Max - Cutting speed (vc)",
        allowsNegativeValues = false,
        maxDecimalPlaces = 2,
        unit = "m/min"
    );
}