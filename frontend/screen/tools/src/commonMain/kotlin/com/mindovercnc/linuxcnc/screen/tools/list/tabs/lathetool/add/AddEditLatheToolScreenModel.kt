package com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.add

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.ToolsUseCase
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolType
import com.mindovercnc.model.SpindleDirection
import com.mindovercnc.model.TipOrientation
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.kodein.di.DI
import org.kodein.di.instance
import org.kodein.di.instanceOrNull

class AddEditLatheToolScreenModel(di: DI, componentContext: ComponentContext) :
    StateScreenModel<AddEditLatheToolState>(AddEditLatheToolState()), AddEditLatheToolComponent {

    private val toolsUseCase: ToolsUseCase by di.instance()
    val latheTool: LatheTool? by di.instanceOrNull()

    init {
        latheTool?.let { tool ->
            mutableState.update {
                val toolType = tool.type
                when (tool) {
                    is LatheTool.Turning ->
                        it.copy(
                            latheToolId = tool.toolId,
                            toolType = toolType,
                            cuttingInsert = tool.insert,
                            toolOrientation = tool.tipOrientation,
                            spindleDirection = tool.spindleDirection,
                            backAngle = tool.backAngle.toInt(),
                            frontAngle = tool.frontAngle.toInt(),
                        )
                    is LatheTool.Boring ->
                        it.copy(
                            latheToolId = tool.toolId,
                            toolType = toolType,
                            cuttingInsert = tool.insert,
                            toolOrientation = tool.tipOrientation,
                            spindleDirection = tool.spindleDirection,
                            backAngle = tool.backAngle.toInt(),
                            frontAngle = tool.frontAngle.toInt(),
                            minBoreDiameter = tool.minBoreDiameter,
                            maxZDepth = tool.maxZDepth
                        )
                    is LatheTool.Drilling ->
                        it.copy(
                            latheToolId = tool.toolId,
                            toolType = toolType,
                            toolOrientation = tool.tipOrientation,
                            spindleDirection = tool.spindleDirection,
                            toolDiameter = tool.toolDiameter,
                            maxZDepth = tool.maxZDepth
                        )
                    is LatheTool.Reaming ->
                        it.copy(
                            latheToolId = tool.toolId,
                            toolType = toolType,
                            toolOrientation = tool.tipOrientation,
                            spindleDirection = tool.spindleDirection,
                            toolDiameter = tool.toolDiameter,
                            maxZDepth = tool.maxZDepth
                        )
                    is LatheTool.Parting ->
                        it.copy(
                            latheToolId = tool.toolId,
                            toolType = toolType,
                            cuttingInsert = tool.insert,
                            toolOrientation = tool.tipOrientation,
                            spindleDirection = tool.spindleDirection,
                            bladeWidth = tool.bladeWidth,
                            maxXDepth = tool.maxXDepth
                        )
                    is LatheTool.Grooving ->
                        it.copy(
                            latheToolId = tool.toolId,
                            toolType = toolType,
                            cuttingInsert = tool.insert,
                            toolOrientation = tool.tipOrientation,
                            spindleDirection = tool.spindleDirection,
                            bladeWidth = tool.bladeWidth,
                            maxXDepth = tool.maxXDepth
                        )
                    is LatheTool.OdThreading ->
                        it.copy(
                            latheToolId = tool.toolId,
                            toolType = toolType,
                            cuttingInsert = tool.insert,
                            toolOrientation = tool.tipOrientation,
                            spindleDirection = tool.spindleDirection,
                            minThreadPitch = tool.minPitch,
                            maxThreadPitch = tool.maxPitch,
                        )
                    is LatheTool.IdThreading ->
                        it.copy(
                            latheToolId = tool.toolId,
                            toolType = toolType,
                            cuttingInsert = tool.insert,
                            toolOrientation = tool.tipOrientation,
                            spindleDirection = tool.spindleDirection,
                            minThreadPitch = tool.minPitch,
                            maxThreadPitch = tool.maxPitch,
                        )
                    is LatheTool.Slotting ->
                        it.copy(
                            latheToolId = tool.toolId,
                            toolType = toolType,
                            toolOrientation = tool.tipOrientation,
                            spindleDirection = tool.spindleDirection,
                            bladeWidth = tool.bladeWidth,
                            maxZDepth = tool.maxZDepth
                        )
                }
            }
        }

        toolsUseCase
            .getCuttingInserts()
            .onEach { insertsList ->
                mutableState.update {
                    it.copy(
                        cuttingInserts = insertsList,
                    )
                }
            }
            .launchIn(coroutineScope)
    }

    override fun setToolId(value: Int) =
        mutableState.update {
            it.copy(
                latheToolId = value,
            )
        }

    override fun setToolType(value: ToolType) =
        mutableState.update {
            it.copy(
                toolType = value,
            )
        }

    override fun setCuttingInsert(value: CuttingInsert) =
        mutableState.update {
            it.copy(
                cuttingInsert = value,
            )
        }

    override fun setToolOrientation(orientation: TipOrientation) =
        mutableState.update {
            it.copy(
                toolOrientation = orientation,
            )
        }

    override fun setFrontAngle(value: Int) =
        mutableState.update {
            it.copy(
                frontAngle = value,
            )
        }

    override fun setBackAngle(value: Int) =
        mutableState.update {
            it.copy(
                backAngle = value,
            )
        }

    override fun setSpindleDirection(value: SpindleDirection) =
        mutableState.update {
            it.copy(
                spindleDirection = value,
            )
        }

    override fun setMinBoreDiameter(value: Double) =
        mutableState.update {
            it.copy(
                minBoreDiameter = value,
            )
        }

    override fun setBladeWidth(value: Double) =
        mutableState.update {
            it.copy(
                bladeWidth = value,
            )
        }

    override fun setMaxXDepth(value: Double) =
        mutableState.update {
            it.copy(
                maxXDepth = value,
            )
        }

    override fun setMaxZDepth(value: Double) =
        mutableState.update {
            it.copy(
                maxZDepth = value,
            )
        }

    override fun setToolDiameter(value: Double) =
        mutableState.update {
            it.copy(
                toolDiameter = value,
            )
        }

    override fun setMinThreadPitch(value: Double) =
        mutableState.update {
            it.copy(
                minThreadPitch = value,
            )
        }

    override fun setMaxThreadPitch(value: Double) =
        mutableState.update {
            it.copy(
                maxThreadPitch = value,
            )
        }

    override fun applyChanges() {
        with(mutableState.value) {
            getLatheTool(toolType)?.let {
                when (latheTool) {
                    null -> toolsUseCase.createLatheTool(it)
                    else -> toolsUseCase.updateLatheTool(it)
                }
            }
        }
    }

    private fun getLatheTool(toolType: ToolType?): LatheTool? {
        with(mutableState.value) {
            return when (toolType) {
                ToolType.Turning ->
                    LatheTool.Turning(
                        toolId = latheToolId,
                        insert = cuttingInsert!!,
                        tipOrientation = toolOrientation!!,
                        frontAngle = frontAngle.toDouble(),
                        backAngle = backAngle.toDouble(),
                        spindleDirection = spindleDirection!!
                    )
                ToolType.Boring ->
                    LatheTool.Boring(
                        toolId = latheToolId,
                        insert = cuttingInsert!!,
                        tipOrientation = toolOrientation!!,
                        frontAngle = frontAngle.toDouble(),
                        backAngle = backAngle.toDouble(),
                        spindleDirection = spindleDirection!!,
                        minBoreDiameter = minBoreDiameter,
                        maxZDepth = maxZDepth
                    )
                ToolType.Drilling ->
                    LatheTool.Drilling(
                        toolId = latheToolId,
                        toolDiameter = toolDiameter,
                        maxZDepth = maxZDepth
                    )
                ToolType.Reaming ->
                    LatheTool.Reaming(
                        toolId = latheToolId,
                        toolDiameter = toolDiameter,
                        maxZDepth = maxZDepth
                    )
                ToolType.Parting ->
                    LatheTool.Parting(
                        toolId = latheToolId,
                        bladeWidth = bladeWidth,
                        maxXDepth = maxXDepth,
                        insert = cuttingInsert!!,
                    )
                ToolType.Grooving ->
                    LatheTool.Grooving(
                        toolId = latheToolId,
                        bladeWidth = bladeWidth,
                        maxXDepth = maxXDepth,
                        insert = cuttingInsert!!,
                        tipOrientation = toolOrientation!!,
                        spindleDirection = spindleDirection!!,
                    )
                else -> null
            }
        }
    }
}
