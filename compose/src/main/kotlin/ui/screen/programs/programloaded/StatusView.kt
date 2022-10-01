package ui.screen.programs.programloaded

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class MachineStatus(
    val spindleOverride: Int = 0,
    val feedOverride: Int = 0,
    val actualSpindleSpeed: Int = 0,
)

@Composable
fun StatusView(
    machineStatus: MachineStatus,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text("Spindle Override: ${machineStatus.spindleOverride} %")
        Text("Actual Speed: ${machineStatus.actualSpindleSpeed} RPM")
        Text("Feed Override: ${machineStatus.feedOverride} %")
    }
}