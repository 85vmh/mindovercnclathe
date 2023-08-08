package components.filesystem

import androidx.compose.runtime.Stable
import kotlinx.datetime.Instant

@Stable
data class FileSystemItemData(
    val title: String,
    val isDirectory: Boolean,
    val lastModified: Instant?,
    val onClick: () -> Unit,
    val onCopy: () -> Unit
)
