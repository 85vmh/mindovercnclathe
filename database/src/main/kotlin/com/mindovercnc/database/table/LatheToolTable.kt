package com.mindovercnc.database.table

import com.mindovercnc.model.SpindleDirection
import com.mindovercnc.model.ToolType
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object LatheToolTable : IntIdTable() {
    val insertId = reference("insertId", CuttingInsertTable, onDelete = ReferenceOption.CASCADE).nullable()
    val type = enumeration("type", ToolType::class)
    val tipOrientation = integer("orientation")
    val frontAngle = double("front_angle").nullable()
    val backAngle = double("back_angle").nullable()
    val spindleDirection = enumeration("spindle_direction", SpindleDirection::class)
    val toolDiameter = double("tool_diameter").nullable()
    val minBoreDiameter = double("min_bore_diameter").nullable()
    val maxZDepth = double("max_z_depth").nullable()
    val bladeWidth = double("blade_width").nullable()
    val maxXDepth = double("max_x_depth").nullable()
    val minThreadPitch = double("min_thread_pitch").nullable()
    val maxThreadPitch = double("max_thread_pitch").nullable()
}