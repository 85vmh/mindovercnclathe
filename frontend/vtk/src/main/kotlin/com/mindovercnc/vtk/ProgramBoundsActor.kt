package com.mindovercnc.vtk

import vtk.vtkCubeAxesActor

class ProgramBoundsActor(pathActor: PathActor) : vtkCubeAxesActor() {
    init {
        SetBounds(pathActor.GetBounds())

//        SetXLabelFormat("%6.3f") //not sure about this format
//        SetZLabelFormat("%6.3f")

        SetFlyModeToStaticEdges()

        GetTitleTextProperty(0).SetColor(1.0, 0.0, 0.0)
        GetLabelTextProperty(0).SetColor(1.0, 0.0, 0.0)

        GetTitleTextProperty(2).SetColor(0.0, 0.0, 1.0)
        GetLabelTextProperty(2).SetColor(0.0, 0.0, 1.0)
        showProgramLabels(false)
    }

    fun showProgramLabels(show: Boolean) {
        if (show) {
            XAxisLabelVisibilityOn()
            YAxisLabelVisibilityOn()
            ZAxisLabelVisibilityOn()
        } else {
            XAxisLabelVisibilityOff()
            YAxisLabelVisibilityOff()
            ZAxisLabelVisibilityOff()
        }
    }

    fun showProgramTicks(show: Boolean) {
        if (show) {
            XAxisTickVisibilityOn()
            XAxisTickVisibilityOn()
            XAxisTickVisibilityOn()
        } else {
            XAxisTickVisibilityOff()
            XAxisTickVisibilityOff()
            XAxisTickVisibilityOff()
        }
    }

    fun showProgramBounds(show: Boolean) {
        if (show) {
            XAxisVisibilityOn()
            YAxisVisibilityOn()
            ZAxisVisibilityOn()
        } else {
            XAxisVisibilityOff()
            XAxisVisibilityOff()
            XAxisVisibilityOff()
        }
    }

}
