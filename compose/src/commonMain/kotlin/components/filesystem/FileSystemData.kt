package components.filesystem

import androidx.compose.runtime.Stable

@Stable
data class FileSystemData(val items: List<FileSystemItemData>) {
  companion object {
    val Empty = FileSystemData(emptyList())
  }
}
