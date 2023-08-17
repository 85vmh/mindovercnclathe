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

