package ui.screen.tools.root.tabs.lathetool

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.SpindleDirection
import screen.composables.cards.CardWithTitle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpindleDirection(
  selectedDirection: SpindleDirection? = null,
  onDirectionSelected: (SpindleDirection) -> Unit
) {
  val itemModifier = Modifier.width(40.dp).height(50.dp)
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
      true -> Color(0xff04b55f)
      false -> Color(0x33000000)
      else -> LocalContentColor.current
    }

  Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
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
