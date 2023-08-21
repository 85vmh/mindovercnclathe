package com.mindovercnc.linuxcnc.screen.manual.root

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.mindovercnc.linuxcnc.screen.manual.root.ui.FeedStatusCard
import com.mindovercnc.model.FeedUiModel

@Composable
@Preview
fun FeedStatusCardPreview() {
    FeedStatusCard(uiModel = FeedUiModel(), onClick = {})
}
