package ui.tab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.mindovercnc.linuxcnc.screen.tools.Tools
import com.mindovercnc.linuxcnc.screen.tools.list.ToolsRootScreen

object ToolsTab : AppTab<Tools>(com.mindovercnc.linuxcnc.screen.tools.list.ToolsRootScreen()) {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Tools"
            val icon = rememberVectorPainter(Icons.Default.Build)

            return remember { TabOptions(index = 3u, title = title, icon = icon) }
        }
}
