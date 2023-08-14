package components.filesystem

import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
actual fun FileSystemItemView(item: FileSystemItemData, modifier: Modifier) {
    ContextMenuArea(
        items = {
            listOf(ContextMenuItem("Copy", item.onCopy))
        }
    ) {
        ListItem(
            modifier = modifier.clickable(onClick = item.onClick),
            colors = ListItemDefaults.colors(containerColor = colorFor(item)),
            headlineContent = {
                Text(
                    textAlign = TextAlign.Left,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    text = item.title
                )
            },
            supportingContent = item.lastModified?.let { lastModified ->
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

private val formatter = DateTimeFormatter.ISO_DATE.withZone(ZoneId.systemDefault())

internal fun millisToLastModified(instant: Instant): String {
    return formatter.format(instant.toJavaInstant())
}