package ui.screen.tools.root.tabs.cuttinginsert

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.FeedsAndSpeeds
import com.mindovercnc.model.MaterialCategory
import screen.uimodel.InputType
import ui.widget.NumericInputWithUnit
import ui.widget.listitem.DropDownSetting

// TODO refactor duplicate code
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditFeedsAndSpeeds(
    initialFeedsAndSpeeds: FeedsAndSpeeds,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = initialFeedsAndSpeeds.materialName,
                onValueChange = {},
                label = { Text("Material") },
                singleLine = true
            )
            DropDownSetting(
                modifier = Modifier.width(300.dp),
                settingName = "Category",
                items = MaterialCategory.values().toList(),
                dropDownWidth = 150.dp,
                selectedItem = initialFeedsAndSpeeds.materialCategory,
                onValueChanged = {}
            )
        }

        val itemModifier = Modifier.fillMaxWidth()

        RangeView(
            "DoC (ap)",
            initialFeedsAndSpeeds.ap.start.toString(),
            InputType.MIN_AP,
            initialFeedsAndSpeeds.ap.start.toString(),
            InputType.MAX_AP,
            itemModifier
        )
        RangeView(
            "Feed (fn)",
            initialFeedsAndSpeeds.fn.start.toString(),
            InputType.MIN_FN,
            initialFeedsAndSpeeds.fn.start.toString(),
            InputType.MAX_FN,
            itemModifier
        )
        RangeView(
            "Speed (vc)",
            initialFeedsAndSpeeds.vc.first.toString(),
            InputType.MIN_VC,
            initialFeedsAndSpeeds.vc.first.toString(),
            InputType.MAX_VC,
            itemModifier
        )
    }
}

private val inputModifier: Modifier = Modifier.width(100.dp)

//TODO rename
@Composable
private fun RangeView(
    title: String,
    min: String,
    minType: InputType,
    max: String,
    maxType: InputType,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = title, modifier = Modifier.width(100.dp))

        Text("between")
        NumericInputWithUnit(
            value = min,
            inputType = minType,
            verticalAlignment = Alignment.CenterVertically,
            onValueChanged = { },
            modifier = inputModifier
        )
        Text("and")
        NumericInputWithUnit(
            value = max,
            inputType = maxType,
            verticalAlignment = Alignment.CenterVertically,
            onValueChanged = { },
            modifier = inputModifier
        )
    }
}