package ui.screen.tools.root.tabs.cuttinginsert

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.model.CuttingInsert
import di.rememberScreenModel
import org.kodein.di.bindProvider
import ui.screen.manual.Manual

class AddEditCuttingInsertScreen(
    private val cuttingInsert: CuttingInsert? = null, private val onChanges: () -> Unit
) : Manual(
    when (cuttingInsert) {
        null -> "Add Cutting Insert"
        else -> "Edit Cutting Insert #${cuttingInsert.code}"
    }
) {

    @Composable
    override fun Actions() {
        val screenModel: AddEditCuttingInsertScreenModel = when (cuttingInsert) {
            null -> rememberScreenModel()
            else -> rememberScreenModel { bindProvider { cuttingInsert } }
        }

        val navigator = LocalNavigator.currentOrThrow
        IconButton(modifier = iconButtonModifier, onClick = {
            screenModel.applyChanges()
            onChanges.invoke()
            navigator.pop()
        }) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "",
            )
        }
    }

    @Composable
    override fun Content() {
        val screenModel: AddEditCuttingInsertScreenModel = when (cuttingInsert) {
            null -> rememberScreenModel()
            else -> rememberScreenModel { bindProvider { cuttingInsert } }
        }

        val state by screenModel.state.collectAsState()
        AddEditCuttingInsertScreenContent(screenModel, state)
    }
}
