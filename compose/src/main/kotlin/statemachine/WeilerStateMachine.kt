package statemachine

import ru.nsk.kstatemachine.*

class WeilerStateMachine {

    val machine = createStateMachine {
        addInitialState(States.MachineStarted) {
            onEntry {
                println("Entered $this")
            }
            transition<Events.EStopPressed> {
                targetState = States.SpindleRunning
                onTriggered {
                    println("Transition to yellow state")
                }
            }
            onExit {
                println("Exit $this")
            }
        }
    }
}

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

sealed class Events {
    object EStopPressed : Event
    object MachineOn : Event
    object MachineOff : Event
    object MachineHome : Event
    object StartSpindle : Event
    object StopSpindle : Event
}