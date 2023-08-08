package editor

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import okio.Path

@Composable
expect fun FileNameHeader(
    file: Path,
    modifier: Modifier = Modifier,
)
