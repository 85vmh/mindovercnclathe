package ui.screen.tools.root.tabs.lathetool

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.mindovercnc.model.CuttingInsert
import com.mindovercnc.model.LatheTool
import com.mindovercnc.model.SpindleDirection
import com.mindovercnc.model.ToolType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import usecase.ToolsUseCase
import com.mindovercnc.model.TipOrientation

class AddEditLatheToolScreenModel(
    val latheTool: LatheTool? = null,
    val toolsUseCase: ToolsUseCase
) : StateScreenModel<AddEditLatheToolScreenModel.State>(State()) {

    data class State(
        val latheToolId: Int? = null,
        val toolTypes: List<ToolType> = ToolType.values().toList(),
        val toolType: ToolType? = null,
        val cuttingInserts: List<CuttingInsert> = emptyList(),
        val cuttingInsert: CuttingInsert? = null,
        val spindleDirection: SpindleDirection? = null,
        val toolOrientation: Int = 0,
        val frontAngle: Int = 0,
        val backAngle: Int = 0,
        val minBoreDiameter: Double = 0.0,
        val bladeWidth: Double = 0.0,
        val maxZDepth: Double = 0.0,
        val maxXDepth: Double = 0.0,
        val toolDiameter: Double = 0.0,
        val minThreadPitch: Double = 0.0,
        val maxThreadPitch: Double = 0.0,
    )

    init {
        latheTool?.let { tool ->
            mutableState.update {
                val toolType = ToolType.fromLatheTool(tool)
                when (tool) {
                    is LatheTool.Turning -> it.copy(
                        latheToolId = tool.toolId,
                        toolType = toolType,
                        cuttingInsert = tool.insert,
                        toolOrientation = tool.tipOrientation.orient,
                        spindleDirection = tool.spindleDirection,
                        backAngle = tool.backAngle.toInt(),
                        frontAngle = tool.frontAngle.toInt(),
                    )
                    is LatheTool.Boring -> it.copy(
                        latheToolId = tool.toolId,
                        toolType = toolType,
                        cuttingInsert = tool.insert,
                        toolOrientation = tool.tipOrientation.orient,
                        spindleDirection = tool.spindleDirection,
                        backAngle = tool.backAngle.toInt(),
                        frontAngle = tool.frontAngle.toInt(),
                        minBoreDiameter = tool.minBoreDiameter,
                        maxZDepth = tool.maxZDepth
                    )
                    is LatheTool.Drilling -> it.copy(
                        latheToolId = tool.toolId,
                        toolType = toolType,
                        toolOrientation = tool.tipOrientation.orient,
                        spindleDirection = tool.spindleDirection,
                        toolDiameter = tool.toolDiameter,
                        maxZDepth = tool.maxZDepth
                    )
                    is LatheTool.Reaming -> it.copy(
                        latheToolId = tool.toolId,
                        toolType = toolType,
                        toolOrientation = tool.tipOrientation.orient,
                        spindleDirection = tool.spindleDirection,
                        toolDiameter = tool.toolDiameter,
                        maxZDepth = tool.maxZDepth
                    )
                    is LatheTool.Parting -> it.copy(
                        latheToolId = tool.toolId,
                        toolType = toolType,
                        cuttingInsert = tool.insert,
                        toolOrientation = tool.tipOrientation.orient,
                        spindleDirection = tool.spindleDirection,
                        bladeWidth = tool.bladeWidth,
                        maxXDepth = tool.maxXDepth
                    )
                    is LatheTool.Grooving -> it.copy(
                        latheToolId = tool.toolId,
                        toolType = toolType,
                        cuttingInsert = tool.insert,
                        toolOrientation = tool.tipOrientation.orient,
                        spindleDirection = tool.spindleDirection,
                        bladeWidth = tool.bladeWidth,
                        maxXDepth = tool.maxXDepth
                    )
                    is LatheTool.OdThreading -> it.copy(
                        latheToolId = tool.toolId,
                        toolType = toolType,
                        cuttingInsert = tool.insert,
                        toolOrientation = tool.tipOrientation.orient,
                        spindleDirection = tool.spindleDirection,
                        minThreadPitch = tool.minPitch,
                        maxThreadPitch = tool.maxPitch,
                    )
                    is LatheTool.IdThreading -> it.copy(
                        latheToolId = tool.toolId,
                        toolType = toolType,
                        cuttingInsert = tool.insert,
                        toolOrientation = tool.tipOrientation.orient,
                        spindleDirection = tool.spindleDirection,
                        minThreadPitch = tool.minPitch,
                        maxThreadPitch = tool.maxPitch,
                    )
                    is LatheTool.Slotting -> it.copy(
                        latheToolId = tool.toolId,
                        toolType = toolType,
                        toolOrientation = tool.tipOrientation.orient,
                        spindleDirection = tool.spindleDirection,
                        bladeWidth = tool.bladeWidth,
                        maxZDepth = tool.maxZDepth
                    )
                }
            }
        }

        toolsUseCase.getCuttingInserts()
            .onEach { insertsList ->
                mutableState.update {
                    it.copy(
                        cuttingInserts = insertsList,
                    )
                }
            }.launchIn(coroutineScope)
    }

    fun setToolId(value: Int) = mutableState.update {
        it.copy(
            latheToolId = value,
        )
    }

    fun setToolType(value: ToolType) = mutableState.update {
        it.copy(
            toolType = value,
        )
    }

    fun setCuttingInsert(value: CuttingInsert) = mutableState.update {
        it.copy(
            cuttingInsert = value,
        )
    }

    fun setToolOrientation(value: Int) = mutableState.update {
        it.copy(
            toolOrientation = value,
        )
    }

    fun setFrontAngle(value: Int) = mutableState.update {
        it.copy(
            frontAngle = value,
        )
    }

    fun setBackAngle(value: Int) = mutableState.update {
        it.copy(
            backAngle = value,
        )
    }

    fun setSpindleDirection(value: SpindleDirection) = mutableState.update {
        it.copy(
            spindleDirection = value,
        )
    }

    fun setMinBoreDiameter(value: Double) = mutableState.update {
        it.copy(
            minBoreDiameter = value,
        )
    }

    fun setBladeWidth(value: Double) = mutableState.update {
        it.copy(
            bladeWidth = value,
        )
    }

    fun setMaxXDepth(value: Double) = mutableState.update {
        it.copy(
            maxXDepth = value,
        )
    }

    fun setMaxZDepth(value: Double) = mutableState.update {
        it.copy(
            maxZDepth = value,
        )
    }

    fun setToolDiameter(value: Double) = mutableState.update {
        it.copy(
            toolDiameter = value,
        )
    }

    fun setMinThreadPitch(value: Double) = mutableState.update {
        it.copy(
            minThreadPitch = value,
        )
    }

    fun setMaxThreadPitch(value: Double) = mutableState.update {
        it.copy(
            maxThreadPitch = value,
        )
    }

    fun applyChanges() {
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
                ToolType.Turning -> LatheTool.Turning(
                    toolId = latheToolId,
                    insert = cuttingInsert!!,
                    tipOrientation = TipOrientation.getOrientation(toolOrientation),
                    frontAngle = frontAngle.toDouble(),
                    backAngle = backAngle.toDouble(),
                    spindleDirection = spindleDirection!!
                )
                ToolType.Boring -> LatheTool.Boring(
                    toolId = latheToolId,
                    insert = cuttingInsert!!,
                    tipOrientation = TipOrientation.getOrientation(toolOrientation),
                    frontAngle = frontAngle.toDouble(),
                    backAngle = backAngle.toDouble(),
                    spindleDirection = spindleDirection!!,
                    minBoreDiameter = minBoreDiameter,
                    maxZDepth = maxZDepth
                )
                ToolType.Drilling -> LatheTool.Drilling(
                    toolId = latheToolId,
                    toolDiameter = toolDiameter,
                    maxZDepth = maxZDepth
                )
                ToolType.Reaming -> LatheTool.Reaming(
                    toolId = latheToolId,
                    toolDiameter = toolDiameter,
                    maxZDepth = maxZDepth
                )
                ToolType.Parting -> LatheTool.Parting(
                    toolId = latheToolId,
                    bladeWidth = bladeWidth,
                    maxXDepth = maxXDepth,
                    insert = cuttingInsert!!,
                )
                ToolType.Grooving -> LatheTool.Grooving(
                    toolId = latheToolId,
                    bladeWidth = bladeWidth,
                    maxXDepth = maxXDepth,
                    insert = cuttingInsert!!,
                    tipOrientation = TipOrientation.getOrientation(toolOrientation),
                    spindleDirection = spindleDirection!!,
                )
                else -> null
            }
        }
    }
}