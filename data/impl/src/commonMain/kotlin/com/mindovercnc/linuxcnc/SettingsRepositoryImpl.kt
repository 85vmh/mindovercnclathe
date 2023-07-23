package com.mindovercnc.linuxcnc

import com.mindovercnc.model.BooleanKey
import com.mindovercnc.model.DoubleKey
import com.mindovercnc.model.IntegerKey
import com.mindovercnc.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import mu.KotlinLogging

/** Implementation for [SettingsRepository]. */
class SettingsRepositoryImpl : SettingsRepository {

    init {
        LOG.warn { "Remote implementation is missing" }
    }

    override fun put(intKey: IntegerKey, value: Int) {
        /* no-op */
    }

    override fun put(doubleKey: DoubleKey, value: Double) {
        /* no-op */
    }

    override fun put(booleanKey: BooleanKey, value: Boolean) {
        /* no-op */
    }

    override fun get(intKey: IntegerKey, defaultValue: Int): Int {
        return 0
    }

    override fun get(doubleKey: DoubleKey, defaultValue: Double): Double {
        return 0.0
    }

    override fun get(booleanKey: BooleanKey, defaultValue: Boolean): Boolean {
        return false
    }

    override fun flow(intKey: IntegerKey, defaultValue: Int): Flow<Int> {
        return emptyFlow()
    }

    override fun flow(doubleKey: DoubleKey, defaultValue: Double): Flow<Double> {
        return emptyFlow()
    }

    override fun flow(booleanKey: BooleanKey, defaultValue: Boolean): Flow<Boolean> {
        return emptyFlow()
    }

    companion object {
        private val LOG = KotlinLogging.logger("SettingsRepository")
    }
}
