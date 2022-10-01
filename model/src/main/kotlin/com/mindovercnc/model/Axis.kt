package com.mindovercnc.model

enum class Axis(val index: Int) {
    //When jogging, the axes are considered as X=0, Y=1, Z=2
    X(0), Z(2)
}