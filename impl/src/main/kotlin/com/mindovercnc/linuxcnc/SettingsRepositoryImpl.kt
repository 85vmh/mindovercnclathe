package com.mindovercnc.linuxcnc

import com.mindovercnc.model.BooleanKey
import com.mindovercnc.model.DoubleKey
import com.mindovercnc.model.IntegerKey
import com.mindovercnc.repository.SettingsRepository
import java.util.prefs.PreferenceChangeListener
import java.util.prefs.Preferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

/** Implementation for [SettingsRepository]. */
class SettingsRepositoryImpl(private val prefs: Preferences) : SettingsRepository {

  override fun put(intKey: IntegerKey, value: Int) {
    prefs.putInt(intKey.name, value)
  }

  override fun put(doubleKey: DoubleKey, value: Double) {
    prefs.putDouble(doubleKey.name, value)
  }

  override fun put(booleanKey: BooleanKey, value: Boolean) {
    prefs.putBoolean(booleanKey.name, value)
  }

  override fun get(intKey: IntegerKey, defaultValue: Int): Int {
    return prefs.getInt(intKey.name, defaultValue)
  }

  override fun get(doubleKey: DoubleKey, defaultValue: Double): Double {
    return prefs.getDouble(doubleKey.name, defaultValue)
  }

  override fun get(booleanKey: BooleanKey, defaultValue: Boolean): Boolean {
    return prefs.getBoolean(booleanKey.name, defaultValue)
  }

  override fun flow(intKey: IntegerKey, defaultValue: Int): Flow<Int> {
    return prefs.intFlow(intKey, defaultValue).distinctUntilChanged()
  }

  override fun flow(doubleKey: DoubleKey, defaultValue: Double): Flow<Double> {
    return prefs.doubleFlow(doubleKey, defaultValue).distinctUntilChanged()
  }

  override fun flow(booleanKey: BooleanKey, defaultValue: Boolean): Flow<Boolean> {
    return prefs.booleanFlow(booleanKey, defaultValue).distinctUntilChanged()
  }
}

fun Preferences.doubleFlow(key: DoubleKey, defaultValue: Double): Flow<Double> {
  return callbackFlow {
    send(getDouble(key.name, defaultValue))
    val listener = PreferenceChangeListener {
      if (it.key == key.name) {
        trySend(it.newValue.toDouble())
      }
    }
    addPreferenceChangeListener(listener)
    awaitClose { removePreferenceChangeListener(listener) }
  }
}

fun Preferences.booleanFlow(key: BooleanKey, defaultValue: Boolean): Flow<Boolean> {
  return callbackFlow {
    send(getBoolean(key.name, defaultValue))
    val listener = PreferenceChangeListener {
      if (it.key == key.name) {
        trySend(it.newValue.toBoolean())
      }
    }
    addPreferenceChangeListener(listener)
    awaitClose { removePreferenceChangeListener(listener) }
  }
}

fun Preferences.intFlow(key: IntegerKey, defaultValue: Int): Flow<Int> {
  return callbackFlow {
    send(getInt(key.name, defaultValue))
    val listener = PreferenceChangeListener {
      if (it.key == key.name) {
        trySend(it.newValue.toInt())
      }
    }
    addPreferenceChangeListener(listener)
    awaitClose { removePreferenceChangeListener(listener) }
  }
}
