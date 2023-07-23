package statemachine

import ru.nsk.kstatemachine.DefaultState

object States {
    object MachineStarted : DefaultState("Green")
    object MachineInEStop : DefaultState("Green")
    object MachineNotOn : DefaultState("Green")
    object MachineNotHomed : DefaultState("Green")
    object MachineHoming : DefaultState("Green")
    object MachineReady : DefaultState("Green")
    object SpindleRunning : DefaultState("Yellow")
    object AxisFeeding : DefaultState("Axis Feeding")
    object AxisJogging : DefaultState("Axis Jogging")
}