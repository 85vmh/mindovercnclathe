package ui.screen.manual.root

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import components.axis.AxisCoordinates
import screen.uimodel.InputType
import ui.screen.manual.simplecycles.SimpleCyclesScreen
import ui.screen.manual.turningsettings.TurningSettingsScreen
import ui.screen.manual.virtuallimits.VirtualLimitsScreen
import ui.widget.simplecycle.SimpleCycleStatusUi

@Composable
fun ManualTurningContent(
  screenModel: ManualTurningScreenModel,
  state: ManualTurningState,
  navigator: Navigator,
  modifier: Modifier = Modifier
) {
  Column(
    verticalArrangement = Arrangement.SpaceBetween,
    horizontalAlignment = Alignment.End,
    modifier = modifier
  ) {
    AxisCoordinates(
      state.xCoordinateUiModel,
      state.zCoordinateUiModel,
      xToolOffsetsClicked = {
        screenModel.openNumPad(
          inputType = InputType.TOOL_X_COORDINATE,
          onSubmitAction = screenModel::setToolOffsetX
        )
      },
      zToolOffsetsClicked = {
        screenModel.openNumPad(
          inputType = InputType.TOOL_Z_COORDINATE,
          onSubmitAction = screenModel::setToolOffsetZ
        )
      },
      onZeroPosX = screenModel::setZeroPosX,
      onZeroPosZ = screenModel::setZeroPosZ,
      onToggleAbsRelX = screenModel::toggleXAbsRel,
      onToggleAbsRelZ = screenModel::toggleZAbsRel,
    )

    Row(
      modifier = Modifier.fillMaxWidth().padding(8.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      state.spindleUiModel?.let {
        SpindleStatusView(
          uiModel = it,
          onClick = { navigator.push(TurningSettingsScreen()) },
          modifier = Modifier.weight(1f)
        )
      }
      state.feedUiModel?.let {
        FeedStatusView(
          uiModel = it,
          onClick = { navigator.push(TurningSettingsScreen()) },
          modifier = Modifier.weight(1f)
        )
      }
    }
    state.virtualLimitsUiModel?.let {
      VirtualLimitsStatusView(
        virtualLimits = it,
        modifier =
          Modifier.width(380.dp)
            .padding(8.dp)
            .clickable(onClick = { navigator.push(VirtualLimitsScreen()) })
      )
    }
    state.simpleCycleUiModel?.let {
      with(it.simpleCycleParameters) {
        SimpleCycleStatusUi(
          simpleCycleParameters = this,
          modifier =
            Modifier.width(380.dp)
              .padding(8.dp)
              .clickable(onClick = { navigator.push(SimpleCyclesScreen(simpleCycle)) })
        )
      }
    }

    Spacer(modifier = Modifier.weight(1f))

    ManualTurningFooter(
      state,
      screenModel,
      navigator,
      modifier = Modifier.align(Alignment.CenterHorizontally)
    )
  }
}
