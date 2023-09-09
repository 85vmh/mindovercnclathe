package com.mindovercnc.linuxcnc.screen.tools.add

interface AddEditItemComponent<T> {

    val editItem: T?

    val isAdd: Boolean
        get() = editItem == null

    /**
     * Applies changes made to the item.
     *
     * @return Whether the action succeeded or not.
     */
    fun applyChanges(): Boolean
}
