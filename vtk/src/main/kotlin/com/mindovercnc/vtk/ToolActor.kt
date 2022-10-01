package com.mindovercnc.vtk

import com.mindovercnc.model.Point2D
import vtk.*

class ToolActor : vtkActor() {
    private val toolOffset = Point2D(0.0, 0.0)
    private val diamondLength = 4
    private val diamondOffset = 2

    var currentPoint: Point2D = Point2D(0.0, 0.0)
        set(value) {
            if (field != value) {
                field = value
                translateToPoint(value)
            }
        }

    init {
        val points = vtkPoints().apply {
            InsertNextPoint(-toolOffset.x, 0.0, -toolOffset.z)
            InsertNextPoint(-toolOffset.x, 0.0, -toolOffset.z - diamondLength)
            InsertNextPoint(-toolOffset.x + diamondLength, 0.0, -toolOffset.z - diamondLength - diamondOffset)
            InsertNextPoint(-toolOffset.x + diamondLength, 0.0, -toolOffset.z - diamondOffset)
        }

        val quad = vtkQuad().apply {
            GetPointIds().SetId(0, 0)
            GetPointIds().SetId(1, 1)
            GetPointIds().SetId(2, 2)
            GetPointIds().SetId(3, 3)
        }
        val polygons = vtkCellArray().apply {
            InsertNextCell(quad)
            Modified()
        }
        val polyData = vtkPolyData().apply {
            SetPoints(points)
            SetPolys(polygons)
            Modified()
        }
        val transform = vtkTransform().apply {
//            RotateWXYZ(90.0, 0.0, 0.0, 1.0)
            RotateX(180.0)
            RotateY(-10.0)
        }

        val transformFilter = vtkTransformPolyDataFilter().apply {
            SetTransform(transform)
            SetInputData(polyData)
            Update()
        }

        val dataMapper = vtkPolyDataMapper().apply {
//            SetInputData(polyData)
            SetInputConnection(transformFilter.GetOutputPort())
            Update()
        }
        SetMapper(dataMapper)
    }
}