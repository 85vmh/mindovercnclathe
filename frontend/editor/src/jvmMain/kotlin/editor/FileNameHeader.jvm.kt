package editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
actual fun FileNameHeader(
    file: Path,
    modifier: Modifier,
) {
    TooltipArea(
        modifier = modifier,
        tooltip = {
            Surface(shape = RoundedCornerShape(8.dp), shadowElevation = 3.dp) {
                Text(file.toString(), modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
            }
        }
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            text = file.name,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
        )
    }
}