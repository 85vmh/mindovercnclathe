package ui.widget

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.AlertDialog as MaterialAlertDialog

@OptIn(ExperimentalMaterialApi::class)
@Composable
actual fun AlertDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    title: @Composable () -> Unit,
    text: @Composable () -> Unit,
    buttons: @Composable () -> Unit,
    backgroundColor: Color,
    contentColor: Color
) {
    MaterialAlertDialog(
        onDismissRequest = {},
        buttons = buttons,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
actual fun AlertDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    title: @Composable () -> Unit,
    text: @Composable () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    backgroundColor: Color,
    contentColor: Color
) {
    MaterialAlertDialog(
        onDismissRequest = {},
        modifier = modifier,
        title = title,
        text = text,
        confirmButton = confirmButton,
        dismissButton = dismissButton,
        backgroundColor = backgroundColor,
        contentColor = contentColor
    )
}