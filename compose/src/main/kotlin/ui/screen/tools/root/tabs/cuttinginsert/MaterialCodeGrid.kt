package ui.screen.tools.root.tabs.cuttinginsert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

private val headerModifier = Modifier.height(LegendTokens.CellHeight)
private val cellModifier = Modifier.height(LegendTokens.CellHeight)

/**
 * P - steel.
 *
 * M - stainless steel.
 *
 * K - cast iron.
 *
 * N - nonferrous.
 *
 * S - super alloys.
 *
 * H - hardened steel.
 */
@Composable
fun MaterialCodeGrid(modifier: Modifier = Modifier) {
  val materialCodes = remember { MaterialCode.values().toList() }

  Surface(modifier = modifier) {
    LazyVerticalGrid(
      columns = GridCells.Fixed(materialCodes.size + 1),
      modifier = Modifier.fillMaxWidth()
    ) {
      materialCodesHeader(materialCodes)
      materialCodesContent(materialCodes)
    }
  }
}

private fun LazyGridScope.materialCodesHeader(materialCodes: List<MaterialCode>) {
  item { Spacer(modifier = headerModifier) }

  items(materialCodes) { materialCode ->
    Row(
      modifier = headerModifier.background(materialCode.color).fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      Text(
        text = materialCode.name,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold
      )
    }
  }
}

private fun LazyGridScope.materialCodesContent(materialCodes: List<MaterialCode>) {
  item { HeaderCell(title = "Ap", subhead = "(mm)", modifier = cellModifier.fillMaxWidth()) }
  items(materialCodes) { materialCode -> ClosedRangeCell(materialCode.ap) }

  item { HeaderCell(title = "Fn", subhead = "(mm/rev)", modifier = cellModifier.fillMaxWidth()) }
  items(materialCodes) { materialCode -> ClosedRangeCell(materialCode.fn) }

  item { HeaderCell(title = "Vc", subhead = "(m/min)", modifier = cellModifier.fillMaxWidth()) }
  items(materialCodes) { materialCode -> ClosedRangeCell(materialCode.vc) }
}

@Composable
private fun HeaderCell(title: String, subhead: String, modifier: Modifier = Modifier) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = modifier
  ) {
    Text(text = title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    Text(text = subhead, style = MaterialTheme.typography.bodySmall)
  }
}

@Composable
private fun ClosedRangeCell(range: ClosedRange<*>) {
  Row(
    modifier = cellModifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
    Text(
      text = "${range.start} - ${range.endInclusive}",
      style = MaterialTheme.typography.bodySmall
    )
  }
}
