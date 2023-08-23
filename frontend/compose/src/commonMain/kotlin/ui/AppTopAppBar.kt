package ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.mindovercnc.linuxcnc.screen.AppScreen

private val iconButtonModifier = Modifier.size(48.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
    currentScreen: AppScreen,
    navigator: Navigator,
    onOpenDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                    IconButton(modifier = iconButtonModifier, onClick = onOpenDrawer) {
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
        actions = { with(currentScreen) { Actions() } },
        modifier = modifier.shadow(elevation = 8.dp)
    )
}
