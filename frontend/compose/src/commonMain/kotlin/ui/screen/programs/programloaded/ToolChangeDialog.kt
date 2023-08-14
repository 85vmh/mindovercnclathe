package ui.screen.programs.programloaded

import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class ToolChangeModel(val requestedTool: Int)

@Composable
fun ToolChangeDialog(
    toolChangeModel: ToolChangeModel,
    confirmationClick: () -> Unit,
    abortClick: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.width(400.dp),
        onDismissRequest = {},
        title = {
            Text(text = "Tool Change Required", style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Text(
                text = "Clamp tool holder ${toolChangeModel.requestedTool}, then press Continue",
                style = MaterialTheme.typography.bodySmall
            )
        },
        confirmButton = {
            Button(
                onClick = confirmationClick,
            ) {
                Text("Continue")
            }
        },
        dismissButton = { Button(onClick = abortClick) { Text("Cancel") } },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    )
}
