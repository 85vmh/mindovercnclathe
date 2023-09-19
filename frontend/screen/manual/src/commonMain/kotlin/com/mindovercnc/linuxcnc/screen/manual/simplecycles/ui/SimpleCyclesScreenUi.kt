package com.mindovercnc.linuxcnc.screen.manual.simplecycles.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.manual.simplecycles.SimpleCyclesComponent
import com.mindovercnc.model.SimpleCycleParameters

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
