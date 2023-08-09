package ui.screen.programs.programloaded

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import scroll.draggableScroll
import usecase.model.ActiveCode

@Composable
fun ActiveCodesView(
  activeCodes: List<ActiveCode>,
  modifier: Modifier = Modifier,
  onCodeClicked: (ActiveCode) -> Unit
) {
  val scrollState = rememberLazyGridState()
  val scope = rememberCoroutineScope()

  LazyHorizontalGrid(
    rows = GridCells.Fixed(2),
    state = scrollState,
    modifier = modifier.draggableScroll(scrollState, scope, Orientation.Horizontal),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    contentPadding = PaddingValues(8.dp)
  ) {
    items(activeCodes) { CodeView(activeCode = it, onClick = onCodeClicked) }
  }
}

@Composable
private fun CodeView(activeCode: ActiveCode, onClick: (ActiveCode) -> Unit) {
  val shape = RoundedCornerShape(8.dp)
  Surface(
    modifier =
      Modifier.width(40.dp).height(24.dp).clip(shape).clickable { onClick.invoke(activeCode) },
    shape = shape,
    border = BorderStroke(1.dp, SolidColor(Color.LightGray)),
  ) {
    Box(contentAlignment = Alignment.Center) {
      Text(text = activeCode.stringRepresentation, style = MaterialTheme.typography.labelSmall)
    }
  }
}
