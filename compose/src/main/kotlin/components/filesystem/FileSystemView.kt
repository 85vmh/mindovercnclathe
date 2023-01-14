package components.filesystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import extensions.draggableScroll
import java.text.SimpleDateFormat
import java.util.*
import screen.composables.platform.VerticalScrollbar

@Composable
fun FileSystemView(data: FileSystemData, modifier: Modifier = Modifier) {
  Surface(modifier = modifier) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Column {
      Divider(color = Color.LightGray, thickness = 0.5.dp)
      Row {
        Box {
          LazyColumn(modifier = Modifier.draggableScroll(scrollState, scope), state = scrollState) {
            items(data.items) { item ->
              FileSystemItemView(item)
              Divider(color = Color.LightGray, thickness = 0.5.dp)
            }
          }
          VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd),
            scrollState = scrollState,
            itemCount = data.items.size,
            averageItemSize = 60.dp
          )
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FileSystemItemView(item: FileSystemItemData, modifier: Modifier = Modifier) {
  val color =
    when {
      item.isDirectory -> MaterialTheme.colorScheme.tertiaryContainer
      else -> MaterialTheme.colorScheme.surfaceVariant
    }

  Surface(color = color, onClick = item.onClick, modifier = modifier) {
    Row(
      modifier = Modifier.padding(start = 8.dp).fillMaxWidth().height(60.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Start
    ) {
      val resourcePath =
        when {
          item.isDirectory -> "folder-icon.png"
          else -> "gcode.png"
        }
      Image(
        modifier = Modifier.width(40.dp).height(40.dp),
        contentDescription = "",
        bitmap = useResource(resourcePath) { loadImageBitmap(it) }
      )
      Column(
        modifier = Modifier.padding(start = 8.dp),
        verticalArrangement = Arrangement.SpaceEvenly
      ) {
        Text(
          textAlign = TextAlign.Left,
          fontSize = 14.sp,
          fontWeight = FontWeight.Medium,
          text = item.title
        )
        if (item.lastModified != null) {
          Text(
            textAlign = TextAlign.Left,
            fontSize = 10.sp,
            fontWeight = FontWeight.Light,
            text = millisToLastModified(item.lastModified)
          )
        }
      }
    }
  }
}

private fun millisToLastModified(millis: Long): String {
  //    when{
  //        DateUtils.isToday(long timeInMilliseconds)
  //    }

  return SimpleDateFormat("dd/MM/yyyy").format(Date(millis))
}
