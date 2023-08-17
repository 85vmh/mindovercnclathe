package ui.tab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.mindovercnc.linuxcnc.screen.conversational.ConversationalRootScreen
import com.mindovercnc.linuxcnc.screen.conversational.Conversational

object ConversationalTab : AppTab<Conversational>(ConversationalRootScreen()) {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Conversational"
            val icon = rememberVectorPainter(Icons.Default.Star)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }
}