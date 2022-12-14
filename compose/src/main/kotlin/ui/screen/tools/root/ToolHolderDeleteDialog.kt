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
import com.mindovercnc.model.LatheTool
import com.mindovercnc.model.ToolHolder

data class ToolHolderDeleteModel(
    val toolHolder: ToolHolder
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ToolHolderDeleteDialog(
    deleteModel: ToolHolderDeleteModel,
    deleteClick: (ToolHolder) -> Unit,
    abortClick: () -> Unit
) {

    AlertDialog(
        modifier = Modifier.width(400.dp),
        onDismissRequest = { },
        title = {
            Text(
                text = "Delete Tool Holder",
                style = MaterialTheme.typography.titleLarge
            )
        },
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