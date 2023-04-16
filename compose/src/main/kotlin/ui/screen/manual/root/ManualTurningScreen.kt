package ui.screen.manual.root

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import di.rememberScreenModel
import kotlinx.coroutines.launch
import screen.composables.InputDialogView
import screen.uimodel.SimpleCycle
import ui.screen.manual.Manual
import ui.screen.manual.simplecycles.SimpleCyclesScreen

class ManualTurningScreen : Manual("Manual Turning") {

  override val drawerEnabled: Boolean
    @Composable get() = true

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  override fun ColumnScope.DrawerContent(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    val navigator = LocalNavigator.currentOrThrow
    val items = remember { SimpleCycle.values() }

    Text(
      modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
      textAlign = TextAlign.Center,
      text = "Simple Cycles",
      style = MaterialTheme.typography.headlineSmall,
    )
    Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp))
    SimpleCyclesList(
      items = items,
      onCycleSelected = {
        scope.launch {
          drawerState.close()
          navigator.push(SimpleCyclesScreen(it))
        }
      },
      contentPadding = PaddingValues(16.dp)
    )
  }

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  override fun Actions() {
    val screenModel = rememberScreenModel<ManualTurningScreenModel>()
    val state by screenModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    val iconColor =
      when {
        state.virtualLimitsUiModel != null -> Color.Green
        state.virtualLimitsAvailable.not() -> Color.LightGray
        else -> LocalContentColor.current
      }

    state.wcsUiModel?.let {
      IconButton(modifier = iconButtonModifier, onClick = { scope.launch { TODO("show sheet") } }) {
        BadgedBox(
          badge = {
            Badge(containerColor = MaterialTheme.colorScheme.secondary) {
              Text(fontSize = 14.sp, text = it.activeOffset)
            }
          }
        ) {
          Icon(
            imageVector = Icons.Default.Face,
            contentDescription = "",
          )
        }
      }
    }
    IconButton(
      enabled = state.virtualLimitsAvailable,
      modifier = iconButtonModifier,
      onClick = { screenModel.setVirtualLimitsActive(state.virtualLimitsUiModel == null) }
    ) {
      Icon(
        tint = iconColor,
        imageVector = Icons.Default.Star,
        contentDescription = "",
      )
    }
  }

  @OptIn(ExperimentalMaterialApi::class)
  @Composable
  override fun Content() {
    val screenModel = rememberScreenModel<ManualTurningScreenModel>()
    val state by screenModel.state.collectAsState()
    val navigator = LocalNavigator.currentOrThrow
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
      sheetContent = {
        state.wcsUiModel?.let { wcsUiModel ->
          ManualTurningSheet(
            sheetState = sheetState,
            wcsUiModel = wcsUiModel,
            onOffsetClick = screenModel::setActiveWcs
          )
        }
      },
    ) {
      ManualTurningContent(screenModel, state, navigator)
    }

    state.numPadState?.let { numPadState ->
      InputDialogView(
        numPadState = numPadState,
        onCancel = { screenModel.closeNumPad() },
        onSubmit = {
          numPadState.onSubmitAction(it)
          screenModel.closeNumPad()
        }
      )
    }
  }
}

@Composable
private fun ChuckView(modifier: Modifier = Modifier, chuckClicked: () -> Unit) {
  Surface(
    shape = RoundedCornerShape(4.dp),
    modifier = modifier.height(250.dp).width(150.dp).clickable { chuckClicked.invoke() },
    border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
    shadowElevation = 16.dp
  ) {
    Column(modifier = Modifier.padding(8.dp)) {
      Text(text = "Set:", fontSize = 14.sp, textAlign = TextAlign.Left)
      Text(text = "Actual:", fontSize = 14.sp, textAlign = TextAlign.Left)
    }
  }
}
