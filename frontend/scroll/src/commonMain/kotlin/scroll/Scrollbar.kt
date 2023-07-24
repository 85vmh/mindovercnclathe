package scroll

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun VerticalScrollbar(
    modifier: Modifier,
    scrollState: LazyListState
) = androidx.compose.foundation.VerticalScrollbar(
    rememberScrollbarAdapter(scrollState),
    modifier
)