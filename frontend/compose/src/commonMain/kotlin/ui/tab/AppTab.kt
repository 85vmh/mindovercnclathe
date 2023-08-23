package ui.tab

import TabViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import com.mindovercnc.linuxcnc.screen.AppScreen
import com.mindovercnc.linuxcnc.screen.rememberScreenModel
import kotlinx.coroutines.launch
import mu.KotlinLogging
import ui.AppBottomBar
import ui.AppTopAppBar

@Suppress("UNCHECKED_CAST")
abstract class AppTab<S : AppScreen>(private val rootScreen: S) : Tab {
    private val logger = KotlinLogging.logger("AppTab")

    private val iconButtonModifier = Modifier.size(48.dp)

    val drawerState: DrawerState = DrawerState(DrawerValue.Closed)

    @OptIn(ExperimentalMaterialApi::class)
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
                    ModalDrawerSheet(
                        //              drawerShape = RoundedCornerShape(topEnd = 8.dp, bottomEnd =
                        // 8.dp)
                        ) {
                        with(currentScreen) { DrawerContent(drawerState) }
                    }
                },
                modifier = Modifier,
                drawerState = drawerState,
                gesturesEnabled = currentScreen.drawerEnabled,
            ) {
                ModalBottomSheetLayout(
                    modifier = Modifier.fillMaxWidth(),
                    sheetState = currentScreen.sheetState,
                    sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                    sheetContent = {
                        Surface(
                            Modifier.defaultMinSize(minHeight = 1.dp),
                            color = MaterialTheme.colorScheme.surface
                        ) {
                            currentScreen.SheetContent(currentScreen.sheetState)
                        }
                    },
                    sheetBackgroundColor = MaterialTheme.colorScheme.surface,
                    sheetContentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = { TopAppBar(currentScreen, navigator) },
                        bottomBar = {
                            AppBottomBar(
                                modifier = Modifier.height(60.dp),
                                enabled = uiState.isBottomBarEnabled,
                                selected = this,
                                badgeValue = { tab ->
                                    when (tab) {
                                        is ToolsTab -> "T${uiState.currentTool}"
                                        else -> null
                                    }
                                },
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
    private fun <S : AppScreen> TopAppBar(currentScreen: S, navigator: Navigator) {
        val scope = rememberCoroutineScope()
        AppTopAppBar(
            navigator = navigator,
            currentScreen = currentScreen,
            onOpenDrawer = { scope.launch { drawerState.open() } },
            modifier = Modifier.shadow(elevation = 8.dp)
        )
    }
}
