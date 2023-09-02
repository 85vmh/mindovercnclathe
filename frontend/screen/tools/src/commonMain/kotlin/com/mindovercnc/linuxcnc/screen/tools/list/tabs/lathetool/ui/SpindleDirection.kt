package com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.SpindleDirection
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.orEmpty
import org.jetbrains.compose.resources.rememberImageVector
import org.jetbrains.compose.resources.resource
import com.mindovercnc.linuxcnc.widgets.cards.CardWithTitle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpindleDirection(
    selectedDirection: SpindleDirection? = null,
    onDirectionSelected: (SpindleDirection) -> Unit
) {
    val itemModifier = Modifier.size(50.dp)
    val items = remember {
        listOf(SpindleDirection.Reverse, SpindleDirection.Both, SpindleDirection.Forward)
    }

    CardWithTitle(cardTitle = "Spindle Direction", modifier = Modifier.width(200.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            items.forEach { item ->
                DirectionItem(
                    spindleDirection = item,
                    active = selectedDirection == item,
                    modifier = itemModifier.onClick { onDirectionSelected(item) }
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DirectionItem(
    spindleDirection: SpindleDirection,
    active: Boolean? = null,
    modifier: Modifier = Modifier
) {
    val direction =
        when (spindleDirection) {
            SpindleDirection.Reverse -> "rev"
            SpindleDirection.Forward -> "fwd"
            SpindleDirection.Both -> "both"
            SpindleDirection.None -> "none"
        }
    val fileName = "spindle_${direction}.xml"

    val selectedTint =
        when (active) {
            true -> MaterialTheme.colorScheme.primary
            false -> MaterialTheme.colorScheme.surfaceVariant
            else -> LocalContentColor.current
        }

    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        Icon(
            modifier = Modifier.size(50.dp),
            imageVector = resource(fileName).rememberImageVector(LocalDensity.current).orEmpty(),
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
