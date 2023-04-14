package ui.screen.tools.root.tabs.cuttinginsert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.InsertClearance
import com.mindovercnc.model.InsertShape
import com.mindovercnc.model.MountingAndChipBreaker
import com.mindovercnc.model.ToleranceClass
import screen.composables.VerticalDivider

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
  Row(modifier = modifier) {
    VerticalDivider()
    FeedsAndSpeedsLegend(modifier = Modifier.weight(1f))
    VerticalDivider()
    MaterialCode.values().forEach { item ->
      MaterialFeedsAndSpeeds(item, modifier = Modifier.weight(1f))
      VerticalDivider()
    }
  }
}

@Composable
private fun FeedsAndSpeedsLegend(modifier: Modifier = Modifier) {
  Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
    Divider(color = Color.LightGray)

    Spacer(modifier = headerModifier)

    Divider(color = Color.LightGray)

    HeaderCell(title = "Ap", subhead = "(mm)", modifier = cellModifier.fillMaxWidth())
    Divider(color = Color.LightGray)

    HeaderCell(title = "Fn", subhead = "(mm/rev)", modifier = cellModifier.fillMaxWidth())
    Divider(color = Color.LightGray)

    HeaderCell(title = "Vc", subhead = "(m/min)", modifier = cellModifier.fillMaxWidth())
    Divider(color = Color.LightGray)
  }
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
private fun MaterialFeedsAndSpeeds(materialCode: MaterialCode, modifier: Modifier = Modifier) {

  val headerModifier: Modifier = cellModifier.background(materialCode.color).fillMaxWidth()

  Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
    Row(
      modifier = headerModifier,
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      Text(
        text = materialCode.name,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold
      )
    }
    Divider(color = Color.LightGray)
    FloatRangeCell(materialCode.ap)
    Divider(color = Color.LightGray)
    FloatRangeCell(materialCode.fn)
    Divider(color = Color.LightGray)
    FloatRangeCell(materialCode.vc)
    Divider(color = Color.LightGray)
  }
}

@Composable
private fun FloatRangeCell(range: ClosedRange<*>) {
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

@Composable
fun StandardInsert(
  state: AddEditCuttingInsertScreenModel.State,
  insertShapeChange: (InsertShape) -> Unit,
  insertClearanceChange: (InsertClearance) -> Unit,
  toleranceClassChange: (ToleranceClass) -> Unit,
  mountingChipBreakerChange: (MountingAndChipBreaker) -> Unit
) {
  Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
    InsertLetter(
      modifier = Modifier.width(50.dp),
      items = state.insertShapes,
      dropDownWidth = 50.dp,
      nothingSelectedString = "--",
      selectedItem = state.insertShape,
      onValueChanged = insertShapeChange
    ) {
      Row(
        modifier = Modifier.width(150.dp).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Text(text = it.name, style = MaterialTheme.typography.bodyMedium)
        Text(
          text = "${it.shape}${it.angle?.let { " $it°" } ?: ""}",
          style = MaterialTheme.typography.bodySmall
        )
      }
    }
    InsertLetter(
      modifier = Modifier.width(50.dp),
      items = state.insertClearances,
      dropDownWidth = 50.dp,
      nothingSelectedString = "--",
      selectedItem = state.insertClearance,
      onValueChanged = insertClearanceChange
    ) {
      Row(
        modifier = Modifier.width(50.dp).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Text(text = it.name, style = MaterialTheme.typography.bodyMedium)
        Text(text = "${it.angle}°", style = MaterialTheme.typography.bodySmall)
      }
    }
    InsertLetter(
      modifier = Modifier.width(50.dp),
      items = state.toleranceClasses,
      dropDownWidth = 50.dp,
      nothingSelectedString = "--",
      selectedItem = state.toleranceClass,
      onValueChanged = toleranceClassChange
    ) {
      Row(
        modifier = Modifier.width(50.dp).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Text(text = it.name, style = MaterialTheme.typography.bodyMedium)
      }
    }
    InsertLetter(
      modifier = Modifier.width(50.dp),
      items = state.mountingAndChipBreakerLists,
      dropDownWidth = 50.dp,
      nothingSelectedString = "--",
      selectedItem = state.mountingAndChipBreaker,
      onValueChanged = mountingChipBreakerChange
    ) {
      Row(
        modifier = Modifier.width(200.dp).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        Text(it.name, style = MaterialTheme.typography.bodyMedium)
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Mounting: ",
              style = MaterialTheme.typography.bodySmall,
              fontWeight = FontWeight.Bold
            )
            Text(text = "${it.mountingType}", style = MaterialTheme.typography.bodySmall)
          }
          Row(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Chip Break: ",
              style = MaterialTheme.typography.bodySmall,
              fontWeight = FontWeight.Bold
            )
            Text(text = "${it.chipBreaker}", style = MaterialTheme.typography.bodySmall)
          }
        }
      }
    }
  }
}
