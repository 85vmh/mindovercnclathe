package ui.tab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.mindovercnc.linuxcnc.screen.status.root.StatusRootScreen
import com.mindovercnc.linuxcnc.screen.status.Status

object StatusTab : AppTab<Status>(StatusRootScreen()) {

    var previousTab: AppTab<*>? = null

    override val options: TabOptions
        @Composable
        get() {
            val title = "Status"
            val icon = rememberVectorPainter(Icons.Default.Info)

            return remember {
                TabOptions(
                    index = 4u,
                    title = title,
                    icon = icon
                )
            }
        }
}