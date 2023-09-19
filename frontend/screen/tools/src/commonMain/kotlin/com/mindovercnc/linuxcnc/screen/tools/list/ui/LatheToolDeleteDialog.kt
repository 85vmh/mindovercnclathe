package com.mindovercnc.linuxcnc.screen.tools.list.ui

import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.tools.model.LatheTool

data class LatheToolDeleteModel(
    val latheTool: LatheTool
)

@Composable
fun LatheToolDeleteDialog(
    deleteModel: LatheToolDeleteModel,
    deleteClick: (LatheTool) -> Unit,
    abortClick: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.width(400.dp),
        onDismissRequest = { },
        title = {
            Text(
                text = "Delete Lathe Tool",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Text(
                text = "Tool: ${deleteModel.latheTool}",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            Button(
                onClick = { deleteClick.invoke(deleteModel.latheTool) },
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(onClick = abortClick) {
                Text("Cancel")
            }
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    )
}