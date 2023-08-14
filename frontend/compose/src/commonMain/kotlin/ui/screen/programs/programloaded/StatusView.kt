package ui.screen.programs.programloaded

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.format.toPercent

data class MachineStatus(
    val spindleOverride: Double = 0.0,
    val feedOverride: Double = 0.0,
    val actualSpindleSpeed: Int = 0,
)

@Composable
fun StatusView(machineStatus: MachineStatus, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text("Spindle Override: ${machineStatus.spindleOverride.toPercent()} %")
        Text("Actual Speed: ${machineStatus.actualSpindleSpeed} RPM")
        Text("Feed Override: ${machineStatus.feedOverride.toPercent()} %")
    }
}
