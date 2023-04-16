package ui.screen.manual.root

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import screen.composables.VerticalDivider
import screen.uimodel.InputType
import ui.screen.manual.tapersettings.TaperSettingsScreen

@Composable
fun ManualTurningFooter(
  state: ManualTurningState,
  screenModel: ManualTurningScreenModel,
  navigator: Navigator,
  modifier: Modifier = Modifier
) {

  Row(modifier = modifier, horizontalArrangement = Arrangement.Start) {
    Button(
      modifier = Modifier.width(200.dp),
      onClick = {
        screenModel.openNumPad(InputType.WORKPIECE_ZERO_COORDINATE) {
          screenModel.setWorkpieceZ(it)
        }
      },
    ) {
      Text("Set Z Datum")
    }

    Column {
      Surface(
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
        shadowElevation = 8.dp
      ) {
        val height = 120.dp
        Row(
          modifier = Modifier.height(height).padding(8.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          HandWheelStatus(uiModel = state.handWheelsUiModel, modifier = Modifier.size(height))
          VerticalDivider()
          JoystickStatus(modifier = Modifier.size(height), isTaper = state.taperTurningActive)
          VerticalDivider()
          TaperStatusView(
            taperAngle = state.taperTurningAngle,
            modifier =
              Modifier.clickable(
                enabled = state.taperTurningActive,
                onClick = { navigator.push(TaperSettingsScreen()) }
              ),
            taperTurningActive = state.taperTurningActive,
            onCheckedChange = screenModel::setTaperTurningActive
          )
        }
      }
    }
  }
}
