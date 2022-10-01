package com.mindovercnc.model

enum class InsertShape(val shape: String, val angle: Int? = null) {
    H("Hexagonal"),
    O("Octogonal"),
    P("Pentagonal"),
    S("Square"),
    T("Triangular"),
    C("Rhombic", 80),
    D("Rhombic", 55),
    E("Rhombic", 75),
    F("Rhombic", 50),
    M("Rhombic", 86),
    V("Rhombic", 35),
    W("Trigon"),
    L("Rectangular"),
    A("Parallelogram", 85),
    B("Parallelogram", 82),
    K("Parallelogram", 55),
    R("Round"),
    X("Special Design");

    companion object {
        fun fromCode(code: String?): InsertShape? {
            return when {
                code != null && code.length == 4 -> InsertShape.values().find { it.name == code[0].toString() }
                else -> null
            }
        }
    }
}

enum class InsertClearance(val angle: Int? = null) {
    A(3),
    B(5),
    C(7),
    D(15),
    E(20),
    F(25),
    G(30),
    N(0),
    P(11),
    O;

    companion object {
        fun fromCode(code: String?): InsertClearance? {
            return when {
                code != null && code.length == 4 -> InsertClearance.values().find { it.name == code[1].toString() }
                else -> null
            }
        }
    }
}

enum class ToleranceClass {
    A, F, C, H, E, G, J, K, L, M, N, U;

    companion object {
        fun fromCode(code: String?): ToleranceClass? {
            return when {
                code != null && code.length == 4 -> ToleranceClass.values().find { it.name == code[2].toString() }
                else -> null
            }
        }
    }
}

enum class MountingAndChipBreaker(val mountingType: MountingType? = null, val chipBreaker: ChipBreaker? = null) {
    W(MountingType.WithHole, ChipBreaker.None),
    T(MountingType.WithHole, ChipBreaker.OneSided),
    Q(MountingType.WithHole, ChipBreaker.None),
    U(MountingType.WithHole, ChipBreaker.DoubleSided),
    B(MountingType.WithHole, ChipBreaker.None),
    H(MountingType.WithHole, ChipBreaker.OneSided),
    C(MountingType.WithHole, ChipBreaker.None),
    J(MountingType.WithHole, ChipBreaker.DoubleSided),
    A(MountingType.WithHole, ChipBreaker.None),
    M(MountingType.WithHole, ChipBreaker.OneSided),
    G(MountingType.WithHole, ChipBreaker.DoubleSided),
    N(MountingType.WithoutHole, ChipBreaker.None),
    R(MountingType.WithoutHole, ChipBreaker.OneSided),
    F(MountingType.WithoutHole, ChipBreaker.DoubleSided),
    X;

    companion object {
        fun fromCode(code: String?): MountingAndChipBreaker? {
            return when {
                code != null && code.length == 4 -> MountingAndChipBreaker.values().find { it.name == code[3].toString() }
                else -> null
            }
        }
    }
}

enum class MountingType {
    WithHole, WithoutHole
}

enum class ChipBreaker {
    None, OneSided, DoubleSided
}