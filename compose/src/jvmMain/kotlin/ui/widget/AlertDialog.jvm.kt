package ui.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.AlertDialog as MaterialAlertDialog

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
}