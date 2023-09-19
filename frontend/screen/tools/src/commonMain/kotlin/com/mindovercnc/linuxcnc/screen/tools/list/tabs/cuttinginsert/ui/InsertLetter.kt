package com.mindovercnc.linuxcnc.screen.tools.list.tabs.cuttinginsert.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.widgets.DropDownClosedItem
import com.mindovercnc.linuxcnc.widgets.DropDownView
import scroll.draggableScroll

@Composable
internal fun <T> InsertLetter(
    items: List<T>,
    selectedItem: T?,
    dropDownWidth: Dp,
    nothingSelectedString: String,
    onValueChanged: (T) -> Unit,
    modifier: Modifier = Modifier,
    openedItemContent: @Composable (T) -> Unit
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    DropDownView(
        items = items,
        selected = selectedItem,
        dropDownListModifier = Modifier.draggableScroll(scrollState, scope),
        modifier =
            modifier
                .width(dropDownWidth)
                .border(
                    border = BorderStroke(1.dp, Color.LightGray),
                    shape = RoundedCornerShape(4.dp)
                ),
        onSelected = onValueChanged,
        closedItemContent = {
            DropDownClosedItem(modifier = Modifier.size(height = 40.dp, width = dropDownWidth)) {
                val text =
                    when {
                        it != null -> it.toString()
                        else -> nothingSelectedString
                    }
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        },
        openedItemContent = openedItemContent
    )
}
