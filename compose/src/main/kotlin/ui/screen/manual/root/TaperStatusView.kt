package ui.screen.manual.root

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import extensions.stripZeros

@Composable
fun TaperStatusView(
    taperAngle: Double,
    taperTurningActive: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(modifier = Modifier, style = MaterialTheme.typography.titleSmall, text = "Taper Turning")
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
