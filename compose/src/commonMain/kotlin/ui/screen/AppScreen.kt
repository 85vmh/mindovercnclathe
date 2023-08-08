package ui.screen

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen

abstract class AppScreen(title: String = "") : Screen {

  open val title = mutableStateOf(title)

  open val drawerEnabled
    @Composable get() = false

  val iconButtonModifier = Modifier.size(48.dp)

  @OptIn(ExperimentalMaterialApi::class)
  val sheetState: ModalBottomSheetState = ModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

  val hasCustomTitle = title == ""

  @Composable
  open fun Title() {
    // by default, use the string title
  }

  @Composable
  open fun Actions() {
    // by default, no actions
  }

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  open fun ColumnScope.DrawerContent(drawerState: DrawerState) {
    // by default, no content
  }

  @OptIn(ExperimentalMaterialApi::class)
  @Composable
  open fun SheetContent(sheetState: ModalBottomSheetState) {
    // by default, no content
  }

  @Composable
  open fun Fab() {
    // by default, no content
  }
}
