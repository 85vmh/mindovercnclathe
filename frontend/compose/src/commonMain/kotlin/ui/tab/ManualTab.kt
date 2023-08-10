package ui.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.orEmpty
import org.jetbrains.compose.resources.rememberImageVector
import org.jetbrains.compose.resources.resource
import ui.screen.manual.Manual
import ui.screen.manual.root.ManualTurningScreen

object ManualTab : AppTab<Manual>(ManualTurningScreen()) {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Manual"
            val icon = resource("manual_tab.xml").rememberImageVector(LocalDensity.current).orEmpty()
            val painter = rememberVectorPainter(icon)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = painter
                )
            }
        }
}