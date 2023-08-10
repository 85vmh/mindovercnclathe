package ui.screen.tools.root.tabs.lathetool

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.TipOrientation

val pickerModifier = Modifier.size(50.dp)

val viewModifier = Modifier.size(20.dp)

val arrangement = Arrangement.spacedBy(4.dp)

@Composable
fun ToolOrientationPicker(
    selectedOrientation: TipOrientation?,
    orientationSelected: (TipOrientation) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = remember { TipOrientation.asMatrix() }
    Column(verticalArrangement = arrangement, modifier = modifier) {
        items.forEach { triple ->
            TipOrientationRow(
                selectedOrientation = selectedOrientation,
                items = triple,
                onItemClick = orientationSelected
            )
        }
    }
}

@Composable
private fun TipOrientationRow(
    selectedOrientation: TipOrientation?,
    items: Triple<TipOrientation, TipOrientation, TipOrientation>,
    onItemClick: (TipOrientation) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(horizontalArrangement = arrangement, modifier = modifier) {
        TipOrientationUi(
            orientation = items.first,
            active = selectedOrientation == items.first,
            onClick = onItemClick,
            modifier = pickerModifier
        )
        TipOrientationUi(
            orientation = items.second,
            active = selectedOrientation == items.second,
            onClick = onItemClick,
            modifier = pickerModifier
        )
        TipOrientationUi(
            orientation = items.third,
            active = selectedOrientation == items.third,
            onClick = onItemClick,
            modifier = pickerModifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipOrientationUi(
    orientation: TipOrientation,
    active: Boolean? = null,
    onClick: (TipOrientation) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val selectedTint =
        when (active) {
            true -> MaterialTheme.colorScheme.primary
            false -> MaterialTheme.colorScheme.surfaceVariant
            else -> LocalContentColor.current
        }

    val fileName = "position${orientation.orient}.xml"

    Surface(
        modifier = modifier,
        onClick = { onClick(orientation) },
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(4.dp),
        enabled = enabled
    ) {
        Icon(
            painter = painterResource(fileName),
            tint = selectedTint,
            contentDescription = "",
        )
    }
}
