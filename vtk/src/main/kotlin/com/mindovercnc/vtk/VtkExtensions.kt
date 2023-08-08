package com.mindovercnc.vtk

import actor.Point2D
import vtk.vtkActor
import vtk.vtkAxesActor
import vtk.vtkTransform

fun vtkActor.translateToPoint(point: Point2D) {
    val transform = vtkTransform().apply {
        Translate(point.x, 0.0, point.z)
    }
    SetUserTransform(transform)
    Modified()
}

fun vtkAxesActor.translateToPoint(point: Point2D) {
    val transform = vtkTransform().apply {
        Translate(point.x, 0.0, point.z)
    }
    SetUserTransform(transform)
    Modified()
}

fun Point2D.toDoubleArray(multiplicationFactor: Double = 1.0) = doubleArrayOf(
    x * multiplicationFactor,
    0.0,
    z * multiplicationFactor
)