package com.mindovercnc.vtk

import com.mindovercnc.model.MachineLimits
import com.mindovercnc.model.PathElement
import com.mindovercnc.model.Point2D
import vtk.vtkCamera
import vtk.vtkPanel
import vtk.vtkTransform

class LatheVtkPanel : vtkPanel() {
    private val machineActor: MachineActor = MachineActor()
    private val toolActor: ToolActor = ToolActor()
    private val pathActor: PathActor = PathActor()
    private val axesActor: AxesActor = AxesActor()

    init {
        val transform = vtkTransform().apply {
            RotateX(90.0)
            RotateZ(90.0)
        }
        val camera = vtkCamera().apply {
            ApplyTransform(transform)
//            ParallelProjectionOn()
//            OrthogonalizeViewUp()
        }
        GetRenderer().apply {
            SetActiveCamera(camera)
            machineActor.SetCamera(camera)
            AddActor(machineActor)
            AddActor(axesActor)
            AddActor(toolActor)
            AddActor(pathActor)
        }
    }

//    fun refresh() {
//        GetRenderer().Render()
//        renderWindow.Render()
//    }

    fun setPathElements(pathElements: List<PathElement>) {
        pathActor.pathElements = pathElements
    }

    fun setToolPosition(toolPosition: Point2D) {
        toolActor.currentPoint = toolPosition
    }

    fun setMachineLimits(limits: MachineLimits?) {
        if (limits != null) {
            machineActor.machineLimits = limits
        }
    }

    fun setWcsPosition(wcsPosition: Point2D) {
        pathActor.currentPoint = wcsPosition
        axesActor.currentPoint = wcsPosition
    }
}