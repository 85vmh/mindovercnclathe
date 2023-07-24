package components.filesystem

import androidx.compose.runtime.Stable

@Stable
data class FileSystemItemData(
    val title: String,
    val isDirectory: Boolean,
    val lastModified: Long?,
    val onClick: () -> Unit,
    val onCopy: () -> Unit
)
