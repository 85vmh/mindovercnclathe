package components.filesystem

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
actual fun FileSystemItemView(item: FileSystemItemData, modifier: Modifier) {
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
                    text = lastModified.toString()
                )
            }
        },
        leadingContent = { FileImage(item) }
    )
}