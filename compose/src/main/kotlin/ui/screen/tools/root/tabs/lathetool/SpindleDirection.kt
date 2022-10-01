package ui.screen.tools.root.tabs.lathetool

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.SpindleDirection
import screen.composables.CardWithTitle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpindleDirection(
    selectedDirection: SpindleDirection? = null,
    onDirectionSelected: (SpindleDirection) -> Unit
) {
    val itemModifier = Modifier.width(40.dp).height(50.dp)

    CardWithTitle(
        cardTitle = "Spindle Direction",
        modifier = Modifier.width(200.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DirectionItem(
                spindleDirection = SpindleDirection.Reverse,
                active = selectedDirection == SpindleDirection.Reverse,
                modifier = itemModifier.onClick { onDirectionSelected.invoke(SpindleDirection.Reverse) }
            )
            DirectionItem(
                spindleDirection = SpindleDirection.Both,
                active = selectedDirection == SpindleDirection.Both,
                modifier = itemModifier.onClick { onDirectionSelected.invoke(SpindleDirection.Both) }
            )
            DirectionItem(
                spindleDirection = SpindleDirection.Forward,
                active = selectedDirection == SpindleDirection.Forward,
                modifier = itemModifier.onClick { onDirectionSelected.invoke(SpindleDirection.Forward) }
            )
        }
    }
}

@Composable
fun DirectionItem(
    spindleDirection: SpindleDirection,
    active: Boolean? = null,
    modifier: Modifier = Modifier
) {
    val direction = when (spindleDirection) {
        SpindleDirection.Reverse -> "rev"
        SpindleDirection.Forward -> "fwd"
        SpindleDirection.Both -> "both"
        SpindleDirection.None -> "none"
    }
    val fileName = "spindle_${direction}.xml"

    val selectedTint = when(active) {
        true -> Color(0xff04b55f)
        false -> Color(0x33000000)
        else -> LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            painter = painterResource(fileName),
            tint = selectedTint,
            contentDescription = "",
        )
        Text(
            text = direction.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = selectedTint
        )
    }
}


