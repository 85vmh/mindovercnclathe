package components.filesystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.orEmpty
import org.jetbrains.compose.resources.rememberImageBitmap
import org.jetbrains.compose.resources.resource

@Composable
expect fun FileSystemItemView(item: FileSystemItemData, modifier: Modifier = Modifier)


@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun FileImage(item: FileSystemItemData) {
    val resourcePath = remember(item.isDirectory) {
        when {
            item.isDirectory -> "folder-icon.png"
            else -> "gcode.png"
        }
    }

    Image(
        modifier = Modifier.width(40.dp).height(40.dp),
        contentDescription = "",
        bitmap = resource(resourcePath).rememberImageBitmap().orEmpty()
    )
}

@Composable
internal fun colorFor(item: FileSystemItemData) =
    when {
        item.isDirectory -> MaterialTheme.colorScheme.tertiaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }