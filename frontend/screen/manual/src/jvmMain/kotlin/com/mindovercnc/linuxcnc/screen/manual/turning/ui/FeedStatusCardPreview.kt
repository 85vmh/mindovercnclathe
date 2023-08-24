package com.mindovercnc.linuxcnc.screen.manual.turning.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.mindovercnc.model.FeedUiModel

@Composable
@Preview
fun FeedStatusCardPreview() {
    FeedStatusCard(uiModel = FeedUiModel(), onClick = {})
}
