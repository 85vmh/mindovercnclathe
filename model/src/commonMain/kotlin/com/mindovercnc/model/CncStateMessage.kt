package com.mindovercnc.model

enum class CncStateMessage(val level: Level = Level.Warning) {
    MachineInEstop(Level.Error),
    MachineNotON(Level.Error),
    XAxisNotHomed,
    ZAxisNotHomed,
    XAxisHoming(Level.Info),
    ZAxisHoming(Level.Info),
    SpindleOperationNotAllowed,
    SpindleForwardNotAllowed,
    SpindleReverseNotAllowed,
    JoystickCannotFeedWithSpindleOff,
    JoystickResetRequired,
    ReachedMinSoftLimitX,
    ReachedMaxSoftLimitX,
    ReachedMinSoftLimitZ,
    ReachedMaxSoftLimitZ,
    JoystickDisabledOnXAxis,
    JoystickDisabledOnZAxis;

    enum class Level {
        Error, Warning, Info
    }
}