package ui.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

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
    // TODO
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