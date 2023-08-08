package editor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditorEmptyView(modifier: Modifier = Modifier) {
  Box(modifier) {
    Column(Modifier.align(Alignment.Center)) {
      Icon(
        Icons.Default.Favorite,
        contentDescription = null,
        tint = LocalContentColor.current.copy(alpha = 0.60f),
        modifier = Modifier.align(Alignment.CenterHorizontally)
      )

      Text(
        "Select a program to view & load",
        color = LocalContentColor.current.copy(alpha = 0.60f),
        fontSize = 20.sp,
        modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
      )
    }
  }
}
