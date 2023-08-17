package statemachine

import ru.nsk.kstatemachine.Event

sealed class Events {
    object EStopPressed : Event
    object MachineOn : Event
    object MachineOff : Event
    object MachineHome : Event
    object StartSpindle : Event
    object StopSpindle : Event
}