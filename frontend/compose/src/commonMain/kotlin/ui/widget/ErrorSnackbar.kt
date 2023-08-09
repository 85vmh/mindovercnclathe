package ui.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorSnackbar(
  error: String?,
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier,
  state: SnackbarHostState = remember { SnackbarHostState() }
) {
  SnackbarHost(
    state,
    modifier = modifier.padding(8.dp),
  )
  LaunchedEffect(error) {
    if (error != null) {
      val result = state.showSnackbar(error)
      onDismiss()
    }
  }
}
