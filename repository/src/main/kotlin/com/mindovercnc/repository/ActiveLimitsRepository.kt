package com.mindovercnc.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface ActiveLimitsRepository {

    val isLimitsActive: Flow<Boolean>

    fun setActive(value: Boolean)
}