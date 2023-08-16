package com.mindovercnc.linuxcnc.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.format.stripZeros
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import com.mindovercnc.linuxcnc.widgets.cards.CardWithTitle
import com.mindovercnc.model.SimpleCycleParameters

@Composable
fun SimpleCycleStatusUi(
    simpleCycleParameters: SimpleCycleParameters? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CardWithTitle(
        cardTitle = simpleCycleParameters!!.simpleCycle.name,
        onClick = onClick,
    ) {
        Column(modifier) {
            when (simpleCycleParameters) {
                is SimpleCycleParameters.TurningParameters -> Turning(simpleCycleParameters)
                is SimpleCycleParameters.BoringParameters -> Boring(simpleCycleParameters)
                is SimpleCycleParameters.FacingParameters -> Facing(simpleCycleParameters)
                is SimpleCycleParameters.ThreadingParameters -> Threading(simpleCycleParameters)
                else -> Unit
            }
        }
    }
}

@Composable
private fun Turning(turningParameters: SimpleCycleParameters.TurningParameters) {
    Text("End X: ${turningParameters.xEnd.toFixedDigitsString()}")
    Text("End Z: ${turningParameters.zEnd.toFixedDigitsString()}")
    Text("Depth of cut: ${turningParameters.doc.toFixedDigitsString()}")
    Text("Taper Angle: ${turningParameters.taperAngle}")
    Text("Fillet Radius: ${turningParameters.filletRadius}")
}

@Composable
private fun Boring(boringParameters: SimpleCycleParameters.BoringParameters) {
    Text("End X: ${boringParameters.xEnd.toFixedDigitsString()}")
    Text("End Z: ${boringParameters.zEnd.toFixedDigitsString()}")
    Text("Depth of cut: ${boringParameters.doc.toFixedDigitsString()}")
    Text("Taper Angle: ${boringParameters.taperAngle}")
    Text("Fillet Radius: ${boringParameters.filletRadius}")
}

@Composable
private fun Facing(facingParameters: SimpleCycleParameters.FacingParameters) {
    Text("End X: ${facingParameters.xEnd.toFixedDigitsString()}")
    Text("End Z: ${facingParameters.zEnd.toFixedDigitsString()}")
    Text("DOC: ${facingParameters.doc.toFixedDigitsString()}")
}

@Composable
private fun Threading(threadingParameters: SimpleCycleParameters.ThreadingParameters) {
    Text("Thread Pitch: ${threadingParameters.pitch.stripZeros()}")
    Text("End Z: ${threadingParameters.zEnd.toFixedDigitsString()}")
    Text("Major Diameter: ${threadingParameters.majorDiameter.toFixedDigitsString()}")
    Text("First Pass Depth: ${threadingParameters.firstPassDepth.stripZeros()}")
    Text("Final Thread Depth: ${threadingParameters.finalDepth.stripZeros()}")
}
