package com.mindovercnc.linuxcnc.settings.model

interface SettingKey

enum class IntegerKey : SettingKey {
    RpmValue,
    CssValue,
    MaxCssRpm,
    LastToolUsed,
}

enum class DoubleKey : SettingKey {
    OrientAngle,
    FeedPerRev,
    FeedPerMin,
    TaperAngle,
    VirtualLimitXMinus,
    VirtualLimitXPlus,
    VirtualLimitZMinus,
    VirtualLimitZPlus;
}

enum class BooleanKey : SettingKey {
    IsRpmMode,
    IsUnitsPerRevMode,
    OrientedStop,
    VirtualLimitXMinusActive,
    VirtualLimitXPlusActive,
    VirtualLimitZMinusActive,
    VirtualLimitZPlusActive,
    LimitZPlusIsToolRelated
}