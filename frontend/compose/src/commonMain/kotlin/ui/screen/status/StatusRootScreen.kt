package ui.screen.status

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.domain.model.Message
import com.mindovercnc.linuxcnc.screen.rememberScreenModel
import scroll.draggableScroll

class StatusRootScreen : Status("Status") {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val scrollState = rememberLazyListState()
        val screenModel = rememberScreenModel<StatusRootScreenModel>()

        val state by screenModel.state.collectAsState()

        LazyColumn(modifier = Modifier.draggableScroll(scrollState, scope), state = scrollState) {
            stickyHeader { MessagesHeader() }
            items(state.messages) { item -> MessageRow(item) }
        }
    }
}

@Composable
private fun MessagesHeader(modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier =
                    Modifier.width(100.dp).border(width = 0.5.dp, Color.LightGray).padding(8.dp),
                textAlign = TextAlign.Center,
                text = "Type"
            )
            Text(
                modifier =
                    Modifier.weight(1f).border(width = 0.5.dp, Color.LightGray).padding(8.dp),
                textAlign = TextAlign.Center,
                text = "Message"
            )
        }
    }
}

@Composable
private fun MessageRow(item: Message, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.clickable {}
    ) {
        Text(
            modifier = Modifier.width(100.dp).border(width = 0.5.dp, Color.LightGray).padding(8.dp),
            textAlign = TextAlign.Center,
            text = item.level.name
        )
        Text(
            item.text,
            modifier = Modifier.weight(1f).border(width = 0.5.dp, Color.LightGray).padding(8.dp),
        )
    }
}
