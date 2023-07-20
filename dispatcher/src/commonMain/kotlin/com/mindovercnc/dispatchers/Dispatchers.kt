package com.mindovercnc.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/** Wrapper over a [CoroutineDispatcher]. */
interface WrappedDispatcher {
  val dispatcher: CoroutineDispatcher
}

/** A wrapper for [Dispatchers.Main]. */
class MainDispatcher(override val dispatcher: CoroutineDispatcher) : WrappedDispatcher

/** A wrapper for [Dispatchers.IO]. */
class IoDispatcher(override val dispatcher: CoroutineDispatcher) : WrappedDispatcher

/** A wrapper for a new single thread dispatcher. */
class NewSingleThreadDispatcher(override val dispatcher: CoroutineDispatcher) : WrappedDispatcher

fun WrappedDispatcher.createScope() = CoroutineScope(dispatcher)
