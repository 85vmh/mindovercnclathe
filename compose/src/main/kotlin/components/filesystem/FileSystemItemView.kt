package components.filesystem

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileSystemItemView(item: FileSystemItemData, modifier: Modifier = Modifier) {
    val color =
        when {
            item.isDirectory -> MaterialTheme.colorScheme.tertiaryContainer
            else -> MaterialTheme.colorScheme.surfaceVariant
        }

    ContextMenuArea(
        items = {
            listOf(ContextMenuItem("Copy", item.onCopy))
        }
    ) {
        ListItem(
            modifier = modifier.clickable(onClick = item.onClick),
            colors = ListItemDefaults.colors(containerColor = color),
            headlineText = {
                Text(
                    textAlign = TextAlign.Left,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    text = item.title
                )
            },
            supportingText = item.lastModified?.let { lastModified ->
                {
                    Text(
                        textAlign = TextAlign.Left,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                        text = millisToLastModified(lastModified)
                    )
                }
            },
            leadingContent = { FileImage(item) }
        )
    }
}

@Composable
private fun FileImage(item: FileSystemItemData) {
    val resourcePath = remember(item.isDirectory) {
        when {
            item.isDirectory -> "folder-icon.png"
            else -> "gcode.png"
        }
    }

    Image(
        modifier = Modifier.width(40.dp).height(40.dp),
        contentDescription = "",
        bitmap = useResource(resourcePath) { loadImageBitmap(it) }
    )
}

private fun millisToLastModified(millis: Long): String {
    return SimpleDateFormat("dd/MM/yyyy").format(Date(millis))
}