package ui.screen.manual.simplecycles

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import di.rememberScreenModel
import org.kodein.di.bindProvider
import screen.uimodel.SimpleCycle
import ui.screen.manual.Manual
import usecase.model.SimpleCycleParameters

class SimpleCyclesScreen(
    private val simpleCycle: SimpleCycle
) : Manual(simpleCycle.displayableString) {

    @Composable
    override fun Actions() {
        val screenModel: SimpleCyclesScreenModel = rememberScreenModel {
            bindProvider { simpleCycle }
        }
        val navigator = LocalNavigator.currentOrThrow

        IconButton(
            modifier = iconButtonModifier,
            onClick = {
                screenModel.applyChanges()
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
        val screenModel: SimpleCyclesScreenModel = rememberScreenModel {
            bindProvider { simpleCycle }
        }
        val state by screenModel.state.collectAsState()

        state.simpleCycleParameters?.let {
            when (it) {
                is SimpleCycleParameters.FacingParameters -> FacingParametersView(screenModel, it)
                is SimpleCycleParameters.TurningParameters -> TurningParametersView(screenModel, it)
                is SimpleCycleParameters.BoringParameters -> BoringParametersView(screenModel, it)
                is SimpleCycleParameters.ThreadingParameters -> ThreadingParametersView(screenModel, it)
                is SimpleCycleParameters.DrillingParameters -> DrillingParametersView(screenModel, it)
                is SimpleCycleParameters.KeySlotParameters -> KeySlotParametersView(screenModel, it)
                else -> Unit
            }
        }
    }
}