package com.mindovercnc.linuxcnc.widgets.cards

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

@Composable
fun ExpandIcon(
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedRotation by animateFloatAsState(targetValue = if (expanded) 180f else 0f)
    IconButton(
        modifier = modifier,
        onClick = { onExpandChange(!expanded) }
    ) {
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = if (expanded) "Collapse" else "Expand",
            modifier = Modifier.rotate(animatedRotation),
        )
    }
}