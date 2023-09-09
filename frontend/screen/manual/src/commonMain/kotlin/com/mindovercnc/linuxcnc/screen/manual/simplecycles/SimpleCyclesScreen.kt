package com.mindovercnc.linuxcnc.screen.manual.simplecycles

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.linuxcnc.screen.manual.Manual
import com.mindovercnc.linuxcnc.screen.manual.simplecycles.ui.*
import com.mindovercnc.linuxcnc.screen.rememberScreenModel
import com.mindovercnc.model.SimpleCycle
import com.mindovercnc.model.SimpleCycleParameters
import org.kodein.di.bindProvider

class SimpleCyclesScreen(private val simpleCycle: SimpleCycle) :
    Manual(simpleCycle.displayableString) {

    @Composable
    override fun RowScope.Actions() {
        val screenModel: SimpleCyclesScreenModel = rememberScreenModel {
            bindProvider { simpleCycle }
        }
        val navigator = LocalNavigator.currentOrThrow

        IconButton(
            modifier = iconButtonModifier,
            onClick = {
                screenModel.applyChanges()
                navigator.pop()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
            )
        }
    }

    @Composable
    override fun Content() {
        val screenModel: SimpleCyclesScreenModel = rememberScreenModel {
            bindProvider { simpleCycle }
        }
        SimpleCyclesScreenUi(screenModel, Modifier.fillMaxSize())
    }
}

@Composable
fun SimpleCyclesScreenUi(screenModel: SimpleCyclesComponent, modifier: Modifier = Modifier) {
    val state by screenModel.state.collectAsState()

    state.simpleCycleParameters?.let { parameters ->
        when (parameters) {
            is SimpleCycleParameters.FacingParameters -> {
                FacingParametersView(screenModel, parameters, modifier)
            }
            is SimpleCycleParameters.TurningParameters -> {
                TurningParametersView(screenModel, parameters, modifier)
            }
            is SimpleCycleParameters.BoringParameters -> {
                BoringParametersView(screenModel, parameters, modifier)
            }
            is SimpleCycleParameters.ThreadingParameters -> {
                ThreadingParametersView(screenModel, parameters, modifier)
            }
            is SimpleCycleParameters.DrillingParameters -> {
                DrillingParametersView(screenModel, parameters, modifier)
            }
            is SimpleCycleParameters.KeySlotParameters -> {
                KeySlotParametersView(screenModel, parameters, modifier)
            }
            else -> {
                Unit
            }
        }
    }
}
