package com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.widgets.DropDownClosedItem
import com.mindovercnc.linuxcnc.widgets.DropDownView
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.format.toFixedDigitsString

@Composable
fun DropDownInserts(
    settingName: String,
    items: List<CuttingInsert>,
    selected: CuttingInsert?,
    dropDownWidth: Dp,
    modifier: Modifier = Modifier,
    onValueChanged: (CuttingInsert) -> Unit
) {
    val alignment = Alignment.CenterVertically
    Row(verticalAlignment = alignment, modifier = modifier) {
        Text(text = settingName, modifier = Modifier.weight(1f))
        DropDownView(
            items = items,
            selected = selected,
            modifier =
                Modifier.width(dropDownWidth)
                    .border(
                        border = BorderStroke(1.dp, Color.LightGray),
                        shape = RoundedCornerShape(4.dp)
                    ),
            onSelected = onValueChanged,
            closedItemContent = {
                DropDownClosedItem(modifier = Modifier.height(40.dp)) {
                    if (it != null) {
                        InsertItem(
                            cuttingInsert = it,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                        )
                    } else {
                        Text("None")
                    }
                }
            },
            openedItemContent = {
                InsertItem(
                    cuttingInsert = it,
                    modifier = Modifier.width(dropDownWidth).padding(horizontal = 16.dp)
                )
            }
        )
    }
}

@Composable
private fun InsertItem(cuttingInsert: CuttingInsert, modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = cuttingInsert.code!!,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "∡${cuttingInsert.tipAngle.toFixedDigitsString(0)}°",
            style = MaterialTheme.typography.bodyMedium,
        )

        Text(
            text = "r${cuttingInsert.tipRadius.toFixedDigitsString(1)}",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
