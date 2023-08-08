package editor

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import okio.Path

@Composable
actual fun FileNameHeader(
    file: Path,
    modifier: Modifier,
) {
    Text(
        modifier = modifier.fillMaxWidth().padding(8.dp),
        text = file.name,
        style = MaterialTheme.typography.titleSmall,
        textAlign = TextAlign.Center,
    )
}