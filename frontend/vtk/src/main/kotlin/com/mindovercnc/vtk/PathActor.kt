package com.mindovercnc.vtk

import com.mindovercnc.model.PathElement
import actor.Point2D
import vtk.*

const val length = 2.5 //taken from py

class PathActor : vtkActor() {

    private data class PathColor(
        val alpha: Int,
        val red: Int,
        val green: Int,
        val blue: Int
    )

    private val multiPolyData: vtkAppendPolyData = vtkAppendPolyData()
    private val linesPolyData = vtkPolyData()
    private val lines = vtkCellArray()
    private val points = vtkPoints()

    private val lineColors = vtkUnsignedCharArray().apply {
        SetNumberOfComponents(4)
    }
    private val arcColors = vtkUnsignedCharArray().apply {
        SetNumberOfComponents(4)
    }

    private fun vtkUnsignedCharArray.addPathColor(color: PathColor) {
        // it seems that vtk expects the color in format of rgba
        InsertNextTuple4(color.red.toDouble(), color.green.toDouble(), color.blue.toDouble(), color.alpha.toDouble())
    }

    private val feedColor = PathColor(255, 255, 255, 255)
    private val rapidColor = PathColor(100, 255, 255, 255)

    var pathElements: List<PathElement> = emptyList()
        set(value) {
            if (field != value) {
                field = value
                setData(field)
                Modified()
            }
        }

    var currentPoint: Point2D = Point2D(0.0, 0.0)
        set(value) {
            if (field != value) {
                field = value
                translateToPoint(value)
            }
        }

    init {
        linesPolyData.apply {
            GetCellData().SetScalars(lineColors)
            SetPoints(points)
            SetLines(lines)
            Modified()
        }
        multiPolyData.AddInputData(linesPolyData)

        val dataMapper = vtkPolyDataMapper().apply {
            SetInputConnection(multiPolyData.GetOutputPort())
            SetColorModeToDirectScalars()
            SetScalarModeToUseCellData()
            Update()
        }
        SetMapper(dataMapper)
    }

    private fun setData(
        pathElements: List<PathElement>,
        multiplicationFactor: Double = 1.0
    ) {
        lines.Reset()
        points.Reset()

        pathElements.forEach {
            //println("----$it")
            when (it) {
                is PathElement.Line -> {
                    val startId = points.InsertNextPoint(it.startPoint.toDoubleArray(multiplicationFactor))
                    val endId = points.InsertNextPoint(it.endPoint.toDoubleArray(multiplicationFactor))
                    when (it.type) {
                        PathElement.Line.Type.Traverse -> lineColors.addPathColor(rapidColor)
                        PathElement.Line.Type.Feed -> lineColors.addPathColor(feedColor)
                    }
                    val line = vtkLine().apply {
                        GetPointIds().SetId(0, startId)
                        GetPointIds().SetId(1, endId)
                    }
                    lines.InsertNextCell(line)
                    Modified()
                }
                is PathElement.Arc -> {
                    arcColors.addPathColor(feedColor)
                    val arc = vtkArcSource().apply {
                        SetPoint1(it.startPoint.toDoubleArray(multiplicationFactor))
                        SetPoint2(it.endPoint.toDoubleArray(multiplicationFactor))
                        SetCenter(it.centerPoint.toDoubleArray(multiplicationFactor))
                        SetResolution(30)
                        Update()
                    }
                    val arcPolyData = arc.GetOutput().apply {
                        GetCellData().SetScalars(arcColors)
                    }
                    multiPolyData.AddInputData(arcPolyData)
                }
            }
        }
    }
}