package com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert.add.feedsandspeeds

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.linuxcnc.screen.rememberScreenModel
import com.mindovercnc.linuxcnc.screen.tools.Tools
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert.add.feedsandspeeds.ui.AddEditFeedsAndSpeedsScreenContent
import com.mindovercnc.model.FeedsAndSpeeds
import org.kodein.di.bindProvider

class AddEditFeedsAndSpeedsScreen(
    private val feedsAndSpeeds: FeedsAndSpeeds?,
    private val onChanges: () -> Unit
) : Tools(createTitle(feedsAndSpeeds)) {

    @Composable
    override fun RowScope.Actions() {
        val screenModel: AddEditFeedsAndSpeedsScreenModel =
            when (feedsAndSpeeds) {
                null -> rememberScreenModel()
                else -> rememberScreenModel {  bindProvider { feedsAndSpeeds } }
            }

        val navigator = LocalNavigator.currentOrThrow
        IconButton(
            modifier = iconButtonModifier,
            onClick = {
                screenModel.applyChanges()
                onChanges.invoke()
                navigator.pop()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "",
            )
        }
    }

    @Composable
    override fun Content() {
        val screenModel: AddEditFeedsAndSpeedsScreenModel =
            when (feedsAndSpeeds) {
                null -> rememberScreenModel()
                else -> rememberScreenModel { bindProvider { feedsAndSpeeds } }
            }

        val state by screenModel.state.collectAsState()
        AddEditFeedsAndSpeedsScreenContent(screenModel, state)
    }
}

private fun createTitle(feedsAndSpeeds: FeedsAndSpeeds?) =
    when (feedsAndSpeeds) {
        null -> "Add Feeds & Speeds"
        else -> "Edit Feeds & Speeds"
    }