package com.mindovercnc.linuxcnc

import com.mindovercnc.repository.ActiveLimitsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ActiveLimitsRepositoryImpl : ActiveLimitsRepository {
  private val _isLimitsActive = MutableStateFlow(false)

  override val isLimitsActive = _isLimitsActive.asStateFlow()

  override fun setActive(value: Boolean) {
    _isLimitsActive.value = value
  }
}
