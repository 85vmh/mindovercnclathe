package com.mindovercnc.model.tool

import com.mindovercnc.model.LatheTool
import kotlin.reflect.KClass

enum class ToolType(val displayableValue: String, val type: KClass<*>) {
    Turning("Turning", LatheTool.Turning::class),
    Boring("Boring", LatheTool.Boring::class),
    Drilling("Drilling", LatheTool.Drilling::class),
    Reaming("Reaming", LatheTool.Reaming::class),
    Parting("Parting", LatheTool.Parting::class),
    Grooving("Grooving", LatheTool.Grooving::class),
    OdThreading("Od Threading", LatheTool.OdThreading::class),
    IdThreading("Id Threading", LatheTool.IdThreading::class),
    Slotting("Key Slotting", LatheTool.Slotting::class);

    companion object {
        fun fromLatheTool(latheTool: LatheTool): ToolType {
            return entries.find { it.type == latheTool::class }!!
        }
    }
}