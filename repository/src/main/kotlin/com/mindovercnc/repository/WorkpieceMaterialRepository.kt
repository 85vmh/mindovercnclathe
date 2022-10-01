package com.mindovercnc.repository

import com.mindovercnc.model.WorkpieceMaterial

interface WorkpieceMaterialRepository {
    fun insert(wpMaterial: WorkpieceMaterial)
    fun findAll(): List<WorkpieceMaterial>
}