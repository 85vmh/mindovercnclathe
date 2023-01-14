package ui.tab

import TabViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import di.rememberScreenModel
import kotlinx.coroutines.launch
import ui.screen.AppScreen

private val tabs =
  arrayOf<AppTab<*>>(ManualTab, ConversationalTab, ProgramsTab, ToolsTab, StatusTab)

@Suppress("UNCHECKED_CAST")
abstract class AppTab<S : AppScreen>(private val rootScreen: S) : Tab {

  private val iconButtonModifier = Modifier.size(48.dp)

  @OptIn(ExperimentalMaterial3Api::class)
  val drawerState: DrawerState = DrawerState(DrawerValue.Closed)

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  final override fun Content() {
    val tabNavigator = LocalTabNavigator.current
    val viewModel = rememberScreenModel<TabViewModel>()
    val uiState by viewModel.state.collectAsState()

    if (this == StatusTab && uiState.isBottomBarEnabled) {
      StatusTab.previousTab?.let {
        tabNavigator.current = it
        StatusTab.previousTab = null
      }
    }
    if (!uiState.isBottomBarEnabled && this != StatusTab) {
      StatusTab.previousTab = this
      tabNavigator.current = StatusTab
    }

    Navigator(rootScreen) { navigator ->
      val currentScreen = navigator.lastItem as S
      ModalNavigationDrawer(
        drawerContent = {
          ModalDrawerSheet(drawerShape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)) {
            with(currentScreen) { DrawerContent(drawerState) }
          }
        },
        modifier = Modifier,
        drawerState = drawerState,
        gesturesEnabled = currentScreen.drawerEnabled,
      ) {
        //        ModalBottomSheetLayout(
        //          sheetState = currentScreen.sheetState,
        //          sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        //          sheetContent = {
        //            Surface(
        //              Modifier.defaultMinSize(minHeight = 1.dp),
        //              color = MaterialTheme.colorScheme.surface
        //            ) {
        //              currentScreen.SheetContent(currentScreen.sheetState)
        //            }
        //          },
        //          sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        //          sheetContentColor = MaterialTheme.colorScheme.onSurface
        //        ) {
        Scaffold(
          modifier = Modifier.fillMaxSize(),
          topBar = {
            CenterAlignedTopAppBar(
              title = {
                when (currentScreen.hasCustomTitle) {
                  true -> currentScreen.Title()
                  false -> Text(currentScreen.title.value)
                }
              },
              navigationIcon = {
                when {
                  currentScreen.drawerEnabled -> {
                    val scope = rememberCoroutineScope()
                    IconButton(
                      modifier = iconButtonModifier,
                      onClick = { scope.launch { drawerState.open() } }
                    ) {
                      Icon(Icons.Default.Menu, contentDescription = "")
                    }
                  }
                  navigator.canPop -> {
                    IconButton(modifier = iconButtonModifier, onClick = { navigator.pop() }) {
                      Icon(Icons.Default.ArrowBack, contentDescription = "")
                    }
                  }
                }
              },
              actions = { currentScreen.Actions() },
              modifier = Modifier.shadow(elevation = 8.dp)
            )
          },
          bottomBar = {
            BottomBar(
              modifier = Modifier.height(60.dp),
              enabled = uiState.isBottomBarEnabled,
              selected = this,
              currentTool = uiState.currentTool,
              onClick = { tabNavigator.current = it }
            )
          },
          floatingActionButton = { currentScreen.Fab() }
        ) {
          Box(modifier = Modifier.fillMaxSize().padding(it)) { CurrentScreen() }
        }
      }
    }
  }
}

@Composable
private fun BottomBar(
  modifier: Modifier = Modifier,
  enabled: Boolean,
  currentTool: Int,
  selected: AppTab<*>,
  onClick: (Tab) -> Unit
) {
  NavigationBar(
    modifier = modifier,
    containerColor = MaterialTheme.colorScheme.secondaryContainer,
    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
  ) {
    tabs.forEach { tab ->
      TabNavigationItem(
        tab = tab,
        badgeValue =
          when (tab) {
            is ToolsTab -> "T$currentTool"
            else -> null
          },
        enabled = enabled,
        selected = tab == selected,
        onClick = onClick
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RowScope.TabNavigationItem(
  tab: Tab,
  badgeValue: String? = null,
  enabled: Boolean,
  selected: Boolean,
  onClick: (Tab) -> Unit
) {
  val tabColor =
    when {
      selected -> MaterialTheme.colorScheme.primary
      !enabled -> MaterialTheme.colorScheme.secondary
      else -> MaterialTheme.colorScheme.onPrimaryContainer
    }

  NavigationBarItem(
    label = {
      Text(
        color = tabColor,
        text = tab.options.title,
      )
    },
    enabled = enabled,
    selected = selected,
    onClick = { onClick(tab) },
    icon = {
      if (badgeValue != null) {
        BadgedBox(
          badge = {
            Badge(containerColor = MaterialTheme.colorScheme.secondary) {
              Text(text = badgeValue, style = MaterialTheme.typography.bodyMedium)
            }
          }
        ) {
          Icon(painter = tab.options.icon!!, contentDescription = "", tint = tabColor)
        }
      } else {
        Icon(painter = tab.options.icon!!, contentDescription = "", tint = tabColor)
      }
    },
  )
}
