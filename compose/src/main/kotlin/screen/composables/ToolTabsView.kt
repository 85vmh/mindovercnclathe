package screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class ToolsTabItem(val tabTitle: String) {
    ToolHolders("Tool Holders"),
    LatheTools("Lathe Tools"),
    CuttingInserts("Cutting Inserts");
}

@Composable
fun <E : Enum<E>> ToolTabsView(
    currentTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    val shape = RoundedCornerShape(16.dp) //define border radius here
    val background = MaterialTheme.colorScheme.primaryContainer
    val selected = MaterialTheme.colorScheme.primary

    TabRow(
        selectedTabIndex = currentTabIndex,
        modifier = modifier
            .clip(shape = shape)
            .border(1.dp, selected, shape = shape),
        indicator = {
            TabRowDefaults.Indicator(
                color = Color.Transparent
            )
        },
        containerColor = background
    ) {
        ToolsTabItem.values().forEachIndexed { index, tabItem ->
            Tab(
                selected = currentTabIndex == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = tabItem.tabTitle,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
                modifier = Modifier.background(if (currentTabIndex == index) selected else background)
                    .drawBehind {
                        drawLine(
                            selected,
                            Offset(size.width, 0f),
                            Offset(size.width, size.height),
                            2f
                        )
                    },
//                selectedContentColor = background,
//                unselectedContentColor = selected
            )
        }
    }
}