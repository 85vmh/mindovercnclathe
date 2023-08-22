package app

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.mindovercnc.linuxcnc.screen.manual.root.ManualTurningComponent
import com.mindovercnc.linuxcnc.screen.programs.root.ProgramsRootComponent
import com.mindovercnc.linuxcnc.screen.status.root.StatusRootComponent
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class Manual(val component: ManualTurningComponent) : Child
        class Conversational(
        // TODO: create component
        ) : Child
        class Programs(val component: ProgramsRootComponent) : Child
        class Tools(val component: ToolsComponent) : Child
        class Status(val component: StatusRootComponent) : Child
    }
}
