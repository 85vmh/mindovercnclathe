package com.mindovercnc.linuxcnc.screen.tools.root

import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.CuttingInsert

data class CuttingInsertDeleteModel(
    val cuttingInsert: CuttingInsert
)

@Composable
fun CuttingInsertDeleteDialog(
    deleteModel: CuttingInsertDeleteModel,
    deleteClick: (CuttingInsert) -> Unit,
    abortClick: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.width(400.dp),
        onDismissRequest = { },
        title = {
            Text(
                text = "Delete Cutting Insert",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Text(
                text = "Cutting Insert: ${deleteModel.cuttingInsert}",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            Button(
                onClick = { deleteClick.invoke(deleteModel.cuttingInsert) },
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