package ui.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.tab.TabOptions
import ui.screen.manual.Manual
import ui.screen.manual.root.ManualTurningScreen

object ManualTab : AppTab<Manual>(ManualTurningScreen()) {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Manual"
            val icon = painterResource("manual_tab.xml")

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }
}