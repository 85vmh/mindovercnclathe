package ui.screen.tools.root.tabs.toolholder

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.ToolHolderType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolHolderView(
  type: ToolHolderType,
  onClick: (ToolHolderType) -> Unit,
  isSelected: Boolean,
  modifier: Modifier = Modifier
) {
  val imageResource =
    when (type) {
      ToolHolderType.Generic -> "multifix_generic"
      ToolHolderType.Boring -> "multifix_bore"
      ToolHolderType.DrillHolder -> "multifix_drill"
      ToolHolderType.Parting -> "multifix_part"
    }

  val color =
    if (isSelected) MaterialTheme.colorScheme.primaryContainer
    else MaterialTheme.colorScheme.secondaryContainer
  Surface(
    modifier = modifier.width(120.dp),
    shape = RoundedCornerShape(8.dp),
    onClick = { onClick(type) },
    color = color,
    shadowElevation = 8.dp
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Image(
        modifier = Modifier.fillMaxWidth().aspectRatio(1f),
        painter = painterResource(imageResource),
        contentScale = ContentScale.FillBounds,
        contentDescription = ""
      )
      Text(
        modifier = Modifier.padding(4.dp),
        text = type.name,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.labelLarge
      )
    }
  }
}

@Preview
@Composable
private fun ToolHolderViewPreview() {
  ToolHolderView(
    ToolHolderType.Boring,
    onClick = {},
    isSelected = true,
  )
}
