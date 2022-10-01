package ui.screen.manual.root

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import extensions.stripZeros
import extensions.toFixedDigitsString
import usecase.model.SimpleCycleParameters

@Composable
fun SimpleCycleStatusView(
    simpleCycleParameters: SimpleCycleParameters? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
        shadowElevation = 16.dp
    ) {

        Column(modifier) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium,
                text = simpleCycleParameters!!.simpleCycle.name
            )
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.DarkGray,
                thickness = 1.dp
            )
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
fun Turning(turningParameters: SimpleCycleParameters.TurningParameters) {
    Text("End X: ${turningParameters.xEnd.toFixedDigitsString()}")
    Text("End Z: ${turningParameters.zEnd.toFixedDigitsString()}")
    Text("Depth of cut: ${turningParameters.doc.toFixedDigitsString()}")
    Text("Taper Angle: ${turningParameters.taperAngle}")
    Text("Fillet Radius: ${turningParameters.filletRadius}")
}

@Composable
fun Boring(boringParameters: SimpleCycleParameters.BoringParameters) {
    Text("End X: ${boringParameters.xEnd.toFixedDigitsString()}")
    Text("End Z: ${boringParameters.zEnd.toFixedDigitsString()}")
    Text("Depth of cut: ${boringParameters.doc.toFixedDigitsString()}")
    Text("Taper Angle: ${boringParameters.taperAngle}")
    Text("Fillet Radius: ${boringParameters.filletRadius}")
}

@Composable
fun Facing(facingParameters: SimpleCycleParameters.FacingParameters) {
    Text("End X: ${facingParameters.xEnd.toFixedDigitsString()}")
    Text("End Z: ${facingParameters.zEnd.toFixedDigitsString()}")
    Text("DOC: ${facingParameters.doc.toFixedDigitsString()}")
}

@Composable
fun Threading(threadingParameters: SimpleCycleParameters.ThreadingParameters) {
    Text("Thread Pitch: ${threadingParameters.pitch.stripZeros()}")
    Text("End Z: ${threadingParameters.zEnd.toFixedDigitsString()}")
    Text("Major Diameter: ${threadingParameters.majorDiameter.toFixedDigitsString()}")
    Text("First Pass Depth: ${threadingParameters.firstPassDepth.stripZeros()}")
    Text("Final Thread Depth: ${threadingParameters.finalDepth.stripZeros()}")
}