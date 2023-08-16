package ui.screen.tools.root.tabs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.format.stripZeros
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import com.mindovercnc.model.CuttingInsert
import com.mindovercnc.model.LatheTool
import com.mindovercnc.model.MadeOf
import com.mindovercnc.linuxcnc.widgets.SettingStatusRow
import ui.screen.tools.root.tabs.lathetool.DirectionItem

private val iconModifier =
    Modifier.padding(2.dp)
        .size(40.dp)
        .border(border = BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(4.dp))
        .padding(4.dp)

@Composable
internal fun LatheToolView(
    latheTool: LatheTool,
    modifier: Modifier = Modifier,
    onSelected: ((LatheTool) -> Unit)? = null,
    isSelected: Boolean = false,
) {
    val selectedItemColor = if (isSelected) Color.LightGray else Color.Unspecified

    var itemModifier = modifier.fillMaxWidth().background(selectedItemColor)

    if (onSelected != null) {
        itemModifier = itemModifier.clickable { onSelected.invoke(latheTool) }
    }

    ListItem(
        modifier = itemModifier,
        leadingContent = {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = "#${latheTool.toolId}",
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        headlineContent = {
            Column {
                when (latheTool) {
                    is LatheTool.Turning -> TurningToolView(latheTool)
                    is LatheTool.Boring -> BoringToolView(latheTool)
                    is LatheTool.Parting -> PartingToolView(latheTool)
                    is LatheTool.Grooving -> GroovingToolView(latheTool)
                    is LatheTool.Drilling -> DrillingToolView(latheTool)
                    is LatheTool.Reaming -> ReamingToolView(latheTool)
                    is LatheTool.OdThreading -> ODThreadingToolView(latheTool)
                    is LatheTool.IdThreading -> IDThreadingToolView(latheTool)
                    is LatheTool.Slotting -> SlottingToolView(latheTool)
                }
            }
        },
        trailingContent = {
            /* TODO replace with something.
            TipOrientation(
              modifier = iconsModifier,
              orientation = latheTool.tipOrientation,
            )*/
            DirectionItem(modifier = iconModifier, spindleDirection = latheTool.spindleDirection)
        }
    )
}

@Composable
internal fun TurningToolView(tool: LatheTool.Turning) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = "Turning", style = MaterialTheme.typography.labelLarge)
            cuttingInsertView(tool.insert)
        }
    }
}

@Composable
internal fun BoringToolView(tool: LatheTool.Boring) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = "Boring", style = MaterialTheme.typography.labelLarge)
            cuttingInsertView(tool.insert)
        }
        val settingsRowModifier = Modifier.width(300.dp)
        Spacer(modifier = Modifier.height(4.dp))
        SettingStatusRow(
            modifier = settingsRowModifier,
            settingText = "Min Bore Diameter:",
            settingValue = tool.minBoreDiameter.toFixedDigitsString(1),
            settingUnit = "mm"
        )
        SettingStatusRow(
            modifier = settingsRowModifier,
            settingText = "Max Z Depth:",
            settingValue = tool.maxZDepth.toFixedDigitsString(1),
            settingUnit = "mm"
        )
    }
}

@Composable
internal fun PartingToolView(tool: LatheTool.Parting) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = "Parting", style = MaterialTheme.typography.labelLarge)
            cuttingInsertView(tool.insert)
        }
        val settingsRowModifier = Modifier.width(300.dp)
        Spacer(modifier = Modifier.height(4.dp))
        SettingStatusRow(
            modifier = settingsRowModifier,
            settingText = "Blade Width:",
            settingValue = tool.bladeWidth.stripZeros(),
            settingUnit = "mm"
        )
        SettingStatusRow(
            modifier = settingsRowModifier,
            settingText = "Max X Depth:",
            settingValue = tool.maxXDepth.stripZeros(),
            settingUnit = "mm"
        )
    }
}

@Composable
internal fun GroovingToolView(tool: LatheTool.Grooving) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = "Grooving", style = MaterialTheme.typography.labelLarge)
            cuttingInsertView(tool.insert)
        }
        val settingsRowModifier = Modifier.width(300.dp)
        Spacer(modifier = Modifier.height(4.dp))
        SettingStatusRow(
            modifier = settingsRowModifier,
            settingText = "Blade Width:",
            settingValue = tool.bladeWidth.stripZeros(),
            settingUnit = "mm"
        )
        SettingStatusRow(
            modifier = settingsRowModifier,
            settingText = "Max X Depth:",
            settingValue = tool.maxXDepth.stripZeros(),
            settingUnit = "mm"
        )
    }
}

@Composable
internal fun DrillingToolView(tool: LatheTool.Drilling) {
    Column {
        Text(text = "Drilling", style = MaterialTheme.typography.labelLarge)
        val settingsRowModifier = Modifier.width(300.dp)
        Spacer(modifier = Modifier.height(4.dp))
        SettingStatusRow(
            modifier = settingsRowModifier,
            settingText = "Diameter:",
            settingValue = tool.toolDiameter.stripZeros(),
            settingUnit = "mm"
        )
        SettingStatusRow(
            modifier = settingsRowModifier,
            settingText = "Max Z Depth:",
            settingValue = tool.maxZDepth.stripZeros(),
            settingUnit = "mm"
        )
    }
}

@Composable
internal fun ReamingToolView(tool: LatheTool.Reaming) {
    Column {
        Text(text = "Drilling", style = MaterialTheme.typography.labelLarge)
        val settingsRowModifier = Modifier.width(300.dp)
        Spacer(modifier = Modifier.height(4.dp))
        SettingStatusRow(
            modifier = settingsRowModifier,
            settingText = "Diameter:",
            settingValue = tool.toolDiameter.stripZeros(),
            settingUnit = "mm"
        )
        SettingStatusRow(
            modifier = settingsRowModifier,
            settingText = "Max Z Depth:",
            settingValue = tool.maxZDepth.stripZeros(),
            settingUnit = "mm"
        )
    }
}

@Composable
internal fun ODThreadingToolView(tool: LatheTool.OdThreading) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = "OD Threading", style = MaterialTheme.typography.labelLarge)
            cuttingInsertView(tool.insert)
        }
        val settingsRowModifier = Modifier.width(300.dp)
        Spacer(modifier = Modifier.height(4.dp))
        SettingStatusRow(
            modifier = settingsRowModifier,
            settingText = "Min Pitch:",
            settingValue = tool.minPitch.stripZeros(),
            settingUnit = "mm"
        )
        SettingStatusRow(
            modifier = settingsRowModifier,
            settingText = "Max Pitch:",
            settingValue = tool.maxPitch.stripZeros(),
            settingUnit = "mm"
        )
    }
}

@Composable
internal fun IDThreadingToolView(tool: LatheTool.IdThreading) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = "ID Threading", style = MaterialTheme.typography.labelLarge)
            cuttingInsertView(tool.insert)
        }
        val settingsRowModifier = Modifier.width(300.dp)
        Spacer(modifier = Modifier.height(4.dp))
        SettingStatusRow(
            modifier = settingsRowModifier,
            settingText = "Min Pitch:",
            settingValue = tool.minPitch.stripZeros(),
            settingUnit = "mm"
        )
        SettingStatusRow(
            modifier = settingsRowModifier,
            settingText = "Max Pitch:",
            settingValue = tool.maxPitch.stripZeros(),
            settingUnit = "mm"
        )
        tool.maxZDepth?.let {
            SettingStatusRow(
                modifier = settingsRowModifier,
                settingText = "Max Z Depth:",
                settingValue = it.stripZeros(),
                settingUnit = "mm"
            )
        }
    }
}

@Composable
internal fun SlottingToolView(tool: LatheTool.Slotting) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = "Slotting", style = MaterialTheme.typography.labelLarge)
            tool.insert?.let { cuttingInsertView(it) }
        }
        val settingsRowModifier = Modifier.width(300.dp)
        Spacer(modifier = Modifier.height(4.dp))
        SettingStatusRow(
            modifier = settingsRowModifier,
            settingText = "Blade Width:",
            settingValue = tool.bladeWidth.stripZeros(),
            settingUnit = "mm"
        )
        SettingStatusRow(
            modifier = settingsRowModifier,
            settingText = "Max Z Depth:",
            settingValue = tool.maxZDepth.stripZeros(),
            settingUnit = "mm"
        )
    }
}

@Composable
internal fun cuttingInsertView(insert: CuttingInsert) {
    val cuttingTipText =
        when (insert.madeOf) {
            MadeOf.Carbide,
            MadeOf.Ceramic,
            MadeOf.Cbn,
            MadeOf.Diamond -> insert.code ?: "----"
            else -> "Custom Ground"
        }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = cuttingTipText,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "(${insert.tipAngle.stripZeros()}°)",
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "r${insert.tipRadius}",
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "${insert.madeOf}",
            style = MaterialTheme.typography.labelLarge
        )
    }
}
