package com.mindovercnc.linuxcnc.screen.tools.list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.screen.tools.list.ToolsListComponent

private val shape = RoundedCornerShape(24.dp) // define border radius here

@Composable
fun ToolTabsView(
    currentTab: ToolsListComponent.Config,
    onTabSelected: (ToolsListComponent.Config) -> Unit,
    modifier: Modifier = Modifier,
) {
    val entries = remember {
        listOf(
            ToolsListComponent.Config.Holders,
            ToolsListComponent.Config.Lathe,
            ToolsListComponent.Config.CuttingInserts,
        )
    }
    val background = MaterialTheme.colorScheme.background
    val selectedColor = MaterialTheme.colorScheme.primary

    TabRow(
        selectedTabIndex = entries.indexOf(currentTab),
        modifier = modifier.clip(shape = shape).border(1.dp, selectedColor, shape = shape),
        indicator = { TabRowDefaults.Indicator(color = Color.Transparent) }
    ) {
        entries.forEach { tabItem ->
            val selected = tabItem == currentTab
            Tab(
                selected = selected,
                onClick = { onTabSelected(tabItem) },
                text = {
                    Text(
                        text = tabItem.tabTitle,
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                modifier =
                    Modifier.background(if (selected) selectedColor else background).drawBehind {
                        drawLine(
                            selectedColor,
                            Offset(size.width, 0f),
                            Offset(size.width, size.height),
                            2f
                        )
                    },
                selectedContentColor = contentColorFor(selectedColor),
                unselectedContentColor = contentColorFor(background)
            )
        }
    }
}
