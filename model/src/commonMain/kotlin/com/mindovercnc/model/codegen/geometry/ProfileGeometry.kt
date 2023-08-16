package com.mindovercnc.model.codegen.geometry

interface ProfileGeometry {

    fun getProfile(): List<String>

    val subroutineNumber: Int

    val hasPockets: Boolean
}

