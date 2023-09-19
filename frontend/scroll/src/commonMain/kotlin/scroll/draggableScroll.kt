package scroll

import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Modifier.draggableScroll(
    scrollState: ScrollableState = rememberScrollState(),
    scope: CoroutineScope = rememberCoroutineScope(),
    orientation: Orientation = Orientation.Vertical
): Modifier {
    return draggable(
        rememberDraggableState { delta -> scope.launch { scrollState.scrollBy(-delta) } },
        orientation = orientation
    )
}
