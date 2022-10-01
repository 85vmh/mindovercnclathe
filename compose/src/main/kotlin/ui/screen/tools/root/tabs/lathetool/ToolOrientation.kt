package ui.screen.tools.root.tabs.lathetool

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.TipOrientation

val pickerModifier = Modifier.size(50.dp)
    .border(border = BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(4.dp))
    .padding(8.dp)

val viewModifier = Modifier.size(20.dp)
    .padding(8.dp)

val arrangement = Arrangement.spacedBy(4.dp)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToolOrientationPicker(
    selectedOrientation: Int,
    orientationSelected: (Int) -> Unit
) {
    Column(
        verticalArrangement = arrangement
    ) {
        Row(
            horizontalArrangement = arrangement
        ) {
            TipOrientation(
                orientation = TipOrientation.Position2,
                active = selectedOrientation == 2,
                modifier = pickerModifier.onClick { orientationSelected.invoke(2) }
            )
            TipOrientation(
                orientation = TipOrientation.Position6,
                active = selectedOrientation == 6,
                modifier = pickerModifier.onClick { orientationSelected.invoke(6) })
            TipOrientation(
                orientation = TipOrientation.Position1,
                active = selectedOrientation == 1,
                modifier = pickerModifier.onClick { orientationSelected.invoke(1) })
        }
        Row(
            horizontalArrangement = arrangement
        ) {
            TipOrientation(
                orientation = TipOrientation.Position7,
                active = selectedOrientation == 7,
                modifier = pickerModifier.onClick { orientationSelected.invoke(7) })
            TipOrientation(
                orientation = TipOrientation.Position9,
                active = selectedOrientation == 9,
                modifier = pickerModifier.onClick { orientationSelected.invoke(9) })
            TipOrientation(
                orientation = TipOrientation.Position5,
                active = selectedOrientation == 5,
                modifier = pickerModifier.onClick { orientationSelected.invoke(5) })
        }
        Row(
            horizontalArrangement = arrangement
        ) {
            TipOrientation(
                orientation = TipOrientation.Position3,
                active = selectedOrientation == 3,
                modifier = pickerModifier.onClick { orientationSelected.invoke(3) })
            TipOrientation(
                orientation = TipOrientation.Position8,
                active = selectedOrientation == 8,
                modifier = pickerModifier.onClick { orientationSelected.invoke(8) })
            TipOrientation(
                orientation = TipOrientation.Position4,
                active = selectedOrientation == 4,
                modifier = pickerModifier.onClick { orientationSelected.invoke(4) })
        }
    }
}

@Composable
fun TipOrientation(
    orientation: TipOrientation,
    active: Boolean? = null,
    modifier: Modifier = Modifier
) {
    val selectedTint = when(active) {
        true -> Color(0xff04b55f)
        false -> Color(0x33000000)
        else -> LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
    }

    val fileName = "position${orientation.orient}.xml"

    Box(
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(fileName),
            tint = selectedTint,
            contentDescription = "",
        )
    }
}