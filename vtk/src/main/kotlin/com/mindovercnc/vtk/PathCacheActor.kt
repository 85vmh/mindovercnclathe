package com.mindovercnc.vtk

import com.mindovercnc.model.Point2D
import vtk.*


class PathCacheActor(private val point: Point2D) : vtkActor() {
    private val numPoints = 2
    private val points = vtkPoints()
    private val lines = vtkCellArray()
    private val polygonData = vtkPolyData()
    private val polyMapper = vtkPolyDataMapper()
    private var index = 0

    init {
        points.InsertNextPoint(point.toDoubleArray())
        lines.InsertNextCell(1)
        lines.InsertCellPoint(0)
        with(GetProperty()) {
            //SetColor(Color.Cyan.toDoubleArray())
            SetLineWidth(2.5)
            SetOpacity(0.5)
        }
        SetMapper(polyMapper)
        with(polyMapper) {
            SetInputData(polygonData)
            Update()
        }
    }

    fun addLinePoint(point: Point2D) {
        index++
        with(points) {
            InsertNextPoint(point.toDoubleArray())
            Modified()
        }
        with(lines) {
            InsertNextCell(numPoints)
            InsertCellPoint(index - 1)
            InsertCellPoint(index)
            Modified()
        }
    }

//    private fun Color.toDoubleArray() =
//        doubleArrayOf(this.red.toDouble(), this.green.toDouble(), this.blue.toDouble())

}