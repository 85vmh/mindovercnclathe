package com.mindovercnc.model

enum class MaterialCategory(val material: String) {
    P("Steel"),
    M("Stainless Steel"),
    K("Cast Iron"),
    N("Non Ferrous"),
    S("Super Alloys"),
    H("Hardened Steel");


    override fun toString(): String {
        return "($name) $material"
    }
}