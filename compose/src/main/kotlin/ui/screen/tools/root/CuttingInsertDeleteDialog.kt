package ui.screen.tools.root

import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.PopupAlertDialogProvider
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.CuttingInsert
import com.mindovercnc.model.LatheTool

data class CuttingInsertDeleteModel(
    val cuttingInsert: CuttingInsert
)

@OptIn(ExperimentalMaterialApi::class)
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
        dialogProvider = PopupAlertDialogProvider,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    )
}