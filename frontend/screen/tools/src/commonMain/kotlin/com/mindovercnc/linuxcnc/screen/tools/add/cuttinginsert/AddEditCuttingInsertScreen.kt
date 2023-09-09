package com.mindovercnc.linuxcnc.screen.tools.add.cuttinginsert

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.linuxcnc.screen.rememberScreenModel
import com.mindovercnc.linuxcnc.screen.tools.Tools
import com.mindovercnc.linuxcnc.screen.tools.add.cuttinginsert.ui.AddEditCuttingInsertScreenUi
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import org.kodein.di.bindProvider

class AddEditCuttingInsertScreen(
    private val cuttingInsert: CuttingInsert? = null,
) : Tools(createTitle(cuttingInsert)) {

    @Composable
    override fun RowScope.Actions() {
        val screenModel: AddEditCuttingInsertScreenModel =
            when (cuttingInsert) {
                null -> rememberScreenModel()
                else -> rememberScreenModel { bindProvider { cuttingInsert } }
            }

        val navigator = LocalNavigator.currentOrThrow
        IconButton(
            modifier = iconButtonModifier,
            onClick = {
                screenModel.applyChanges()
                navigator.pop()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
            )
        }
    }

    @Composable
    override fun Content() {
        val screenModel: AddEditCuttingInsertScreenModel =
            when (cuttingInsert) {
                null -> rememberScreenModel()
                else -> rememberScreenModel { bindProvider { cuttingInsert } }
            }

        AddEditCuttingInsertScreenUi(screenModel)
    }

    companion object {

        private fun createTitle(cuttingInsert: CuttingInsert?) =
            when (cuttingInsert) {
                null -> "Add Cutting Insert"
                else -> "Edit Cutting Insert #${cuttingInsert.code}"
            }
    }
}
