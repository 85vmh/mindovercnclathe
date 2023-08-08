package com.mindovercnc.editor

import kotlin.concurrent.Volatile

/** Compact version of List<Int> (without unboxing Int and using IntArray under the hood) */
class IntList(initialCapacity: Int = 16) {
  @Volatile
  private var array = IntArray(initialCapacity)

  @Volatile
  var size: Int = 0
    private set

  fun clear(capacity: Int) {
    array = IntArray(capacity)
    size = 0
  }

  fun add(value: Int) {
    if (size == array.size) {
      doubleCapacity()
    }
    array[size++] = value
  }

  fun update(capacity: Int, block: (IntList) -> Unit) {
    clear(capacity)
    block(this)
    compact()
  }

  operator fun get(index: Int) = array[index]

  private fun doubleCapacity() {
    val newArray = IntArray(array.size * 2 + 1)
    array.copyInto(destination = newArray, endIndex = size)
    array = newArray
  }

  fun compact() {
    array = array.copyOfRange(0, size)
  }
}
