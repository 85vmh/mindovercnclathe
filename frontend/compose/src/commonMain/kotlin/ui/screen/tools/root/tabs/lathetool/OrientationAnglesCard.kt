package ui.screen.tools.root.tabs.lathetool

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.listitem.ValueSetting
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.model.TipOrientation
import com.mindovercnc.linuxcnc.widgets.cards.CardWithTitle

@Composable
fun OrientationAnglesCard(
    state: AddEditLatheToolScreenModel.State,
    orientationSelected: (TipOrientation) -> Unit,
    onFrontAngleChanged: (Int) -> Unit,
    onBackAngleChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    CardWithTitle(cardTitle = "Orientation & Angles", modifier = modifier) {
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            ToolOrientationPicker(state.toolOrientation, orientationSelected = orientationSelected)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ValueSetting(
                    settingName = "Front Angle",
                    value = state.frontAngle.toString(),
                    inputType = InputType.FRONT_ANGLE,
                    onValueChanged = {
                        val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                        onFrontAngleChanged(doubleValue.toInt())
                    },
                    inputModifier = Modifier.width(50.dp)
                )
                ValueSetting(
                    settingName = "Back Angle",
                    value = state.backAngle.toString(),
                    inputType = InputType.BACK_ANGLE,
                    onValueChanged = {
                        val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                        onBackAngleChanged(doubleValue.toInt())
                    },
                    inputModifier = Modifier.width(50.dp)
                )
            }
        }
    }
}
