package com.mindovercnc.linuxcnc.screen.tools.root.ui

import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.tools.model.ToolHolder

data class ToolHolderDeleteModel(val toolHolder: ToolHolder)

@Composable
fun ToolHolderDeleteDialog(
    deleteModel: ToolHolderDeleteModel,
    deleteClick: (ToolHolder) -> Unit,
    abortClick: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.width(400.dp),
        onDismissRequest = {},
        title = { Text(text = "Delete Tool Holder", style = MaterialTheme.typography.titleLarge) },
        text = {
            Text(
                text = "Tool: ${deleteModel.toolHolder}",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            Button(
                onClick = { deleteClick.invoke(deleteModel.toolHolder) },
            ) {
                Text("Delete")
            }
        },
        dismissButton = { Button(onClick = abortClick) { Text("Cancel") } },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    )
}
