package com.mindovercnc.linuxcnc.tools

import com.mindovercnc.linuxcnc.tools.model.WorkpieceMaterial


/** Repository for [WorkpieceMaterial]. */
interface WorkpieceMaterialRepository {

    /** Insert a [WorkpieceMaterial] into the database. */
    fun insert(wpMaterial: WorkpieceMaterial)

    /** Finds all the [WorkpieceMaterial] in the database. */
    fun findAll(): List<WorkpieceMaterial>
}