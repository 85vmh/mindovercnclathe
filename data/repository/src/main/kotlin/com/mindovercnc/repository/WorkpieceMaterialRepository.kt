package com.mindovercnc.repository

import com.mindovercnc.model.WorkpieceMaterial

/** Repository for [WorkpieceMaterial]. */
interface WorkpieceMaterialRepository {

    /** Insert a [WorkpieceMaterial] into the database. */
    fun insert(wpMaterial: WorkpieceMaterial)

    /** Finds all the [WorkpieceMaterial] in the database. */
    fun findAll(): List<WorkpieceMaterial>
}