package com.mindovercnc.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ActiveLimitsRepository {
    private val _isLimitsActive = MutableStateFlow(false)

    val isLimitsActive = _isLimitsActive.asStateFlow()

    fun setActive(value: Boolean) {
        _isLimitsActive.value = value
    }
}