package ui.tab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.mindovercnc.linuxcnc.screen.programs.Programs
import com.mindovercnc.linuxcnc.screen.programs.root.ProgramsRootScreen

object ProgramsTab : AppTab<Programs>(ProgramsRootScreen()) {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Programs"
            val icon = rememberVectorPainter(Icons.Default.List)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }
}