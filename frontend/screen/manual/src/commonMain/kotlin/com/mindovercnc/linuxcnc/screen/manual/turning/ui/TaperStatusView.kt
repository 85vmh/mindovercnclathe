package com.mindovercnc.linuxcnc.screen.manual.turning.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
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
    onClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.clickable(onClick = onClick, enabled = taperTurningActive),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(style = MaterialTheme.typography.titleSmall, text = "Taper Turning")
            AnimatedVisibility(taperTurningActive) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = taperAngle.stripZeros(),
                )
            }
        }
        Switch(checked = taperTurningActive, onCheckedChange = onCheckedChange)
    }
}
