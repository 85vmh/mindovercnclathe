package components.filesystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun FileSystemItemView(item: FileSystemItemData, modifier: Modifier = Modifier)