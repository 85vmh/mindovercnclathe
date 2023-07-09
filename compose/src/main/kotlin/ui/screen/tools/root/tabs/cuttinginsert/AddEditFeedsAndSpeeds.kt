package ui.screen.tools.root.tabs.cuttinginsert

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.FeedsAndSpeeds
import com.mindovercnc.model.MaterialCategory
import screen.composables.DropDownSetting
import screen.uimodel.InputType
import ui.widget.NumericInputWithUnit

// TODO refactor duplicate code
@Composable
fun AddEditFeedsAndSpeeds(
    modifier: Modifier = Modifier,
    initialFeedsAndSpeeds: FeedsAndSpeeds? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = initialFeedsAndSpeeds?.materialName ?: "",
                onValueChange = {},
                label = { Text("Material") },
                singleLine = true
            )
            DropDownSetting(
                modifier = Modifier.width(300.dp),
                settingName = "Category",
                items = MaterialCategory.values().toList(),
                dropDownWidth = 150.dp,
                selectedItem = initialFeedsAndSpeeds?.materialCategory,
                onValueChanged = {}
            )
        }

        val inputModifier: Modifier = Modifier.width(100.dp)
        val labelModifier = Modifier.width(100.dp)
        val rowModifier = Modifier.fillMaxWidth()
        val rowAlignment = Alignment.CenterVertically
        val rowArrangement = Arrangement.spacedBy(16.dp)

        Row(modifier = rowModifier, verticalAlignment = rowAlignment, horizontalArrangement = rowArrangement) {
            Text(modifier = labelModifier, text = "DoC (ap)")

            Text("between")
            NumericInputWithUnit(
                value = initialFeedsAndSpeeds?.ap?.start.toString(),
                inputType = InputType.MIN_AP,
                verticalAlignment = Alignment.CenterVertically,
                onValueChanged = { },
                modifier = inputModifier
            )
            Text("and")
            NumericInputWithUnit(
                value = initialFeedsAndSpeeds?.ap?.endInclusive.toString(),
                inputType = InputType.MAX_AP,
                verticalAlignment = Alignment.CenterVertically,
                onValueChanged = { },
                modifier = inputModifier
            )
        }
        Row(modifier = rowModifier, verticalAlignment = rowAlignment, horizontalArrangement = rowArrangement) {
            Text(modifier = labelModifier, text = "Feed (fn)")

            Text("between")
            NumericInputWithUnit(
                value = initialFeedsAndSpeeds?.fn?.start.toString(),
                inputType = InputType.MIN_FN,
                verticalAlignment = Alignment.CenterVertically,
                onValueChanged = { },
                modifier = inputModifier
            )
            Text("and")
            NumericInputWithUnit(
                value = initialFeedsAndSpeeds?.fn?.endInclusive.toString(),
                inputType = InputType.MAX_FN,
                verticalAlignment = Alignment.CenterVertically,
                onValueChanged = { },
                modifier = inputModifier
            )
        }
        Row(modifier = rowModifier, verticalAlignment = rowAlignment, horizontalArrangement = rowArrangement) {
            Text(modifier = labelModifier, text = "Speed (vc)")

            Text("between")
            NumericInputWithUnit(
                value = initialFeedsAndSpeeds?.fn?.start.toString(),
                inputType = InputType.MIN_VC,
                verticalAlignment = Alignment.CenterVertically,
                onValueChanged = { },
                modifier = inputModifier
            )
            Text("and")
            NumericInputWithUnit(
                value = initialFeedsAndSpeeds?.fn?.endInclusive.toString(),
                inputType = InputType.MAX_VC,
                verticalAlignment = Alignment.CenterVertically,
                onValueChanged = { },
                modifier = inputModifier
            )
        }
    }
}