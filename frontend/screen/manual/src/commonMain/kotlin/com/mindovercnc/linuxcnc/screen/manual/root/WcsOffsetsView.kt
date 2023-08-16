package com.mindovercnc.linuxcnc.screen.manual.root

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import scroll.draggableScroll
import com.mindovercnc.linuxcnc.listitem.LabelWithValue
import com.mindovercnc.model.WcsOffset

val offsetItemModifier = Modifier.wrapContentHeight()
    .width(200.dp)
    .padding(8.dp)

@Composable
fun WcsOffsetsView(
    wcsOffsets: List<WcsOffset>,
    selected: WcsOffset?,
    onOffsetClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    itemModifier: Modifier = offsetItemModifier
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    LazyRow(
        state = scrollState,
        modifier = modifier.draggableScroll(scrollState, scope, Orientation.Horizontal),
        contentPadding = contentPadding
    ) {
        items(wcsOffsets) {
            WorkpieceOffset(
                item = it,
                selected = selected == it,
                modifier = itemModifier,
                onSelected = { onOffsetClick(it.coordinateSystem) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkpieceOffset(
    item: WcsOffset,
    selected: Boolean,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    onSelected: () -> Unit
) {
    Surface(
        color = when {
            selected -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.primaryContainer
        },
        modifier = modifier,
        shape = shape,
        shadowElevation = 16.dp,
        onClick = onSelected
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp).wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                text = item.coordinateSystem
            )
            Divider(color = Color.LightGray, thickness = 0.5.dp)
            LabelWithValue(
                modifier = Modifier.padding(top = 8.dp),
                paddingStart = 0.dp,
                paddingEnd = 0.dp,
                label = "X offset:",
                value = item.xOffset.toFixedDigitsString(),
            )
            Spacer(modifier = Modifier.size(8.dp))
            LabelWithValue(
                modifier = Modifier.padding(bottom = 8.dp),
                paddingStart = 0.dp,
                paddingEnd = 0.dp,
                label = "Z offset:",
                value = item.zOffset.toFixedDigitsString(),
            )
        }
    }
}
