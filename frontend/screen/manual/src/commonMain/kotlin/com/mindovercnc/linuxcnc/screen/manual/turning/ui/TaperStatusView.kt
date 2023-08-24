package com.mindovercnc.linuxcnc.screen.manual.turning.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.format.stripZeros

@Composable
fun TaperStatusView(
    taperAngle: Double,
    taperTurningActive: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier,
            style = MaterialTheme.typography.titleSmall,
            text = "Taper Turning"
        )
        Switch(checked = taperTurningActive, onCheckedChange = onCheckedChange)
        if (taperTurningActive) {
            Text(
                modifier = Modifier,
                style = MaterialTheme.typography.titleMedium,
                text = taperAngle.stripZeros()
            )
        }
    }
}
