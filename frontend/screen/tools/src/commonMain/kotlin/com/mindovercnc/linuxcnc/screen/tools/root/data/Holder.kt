package com.mindovercnc.linuxcnc.screen.tools.root.data

data class Holder(
    val number: Int,
    val type: Type
) {
    enum class Type {
        Generic,
        Parting,
        Drilling
    }
}