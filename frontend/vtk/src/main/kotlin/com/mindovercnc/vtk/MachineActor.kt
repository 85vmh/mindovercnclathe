package com.mindovercnc.vtk

import com.mindovercnc.model.MachineLimits
import vtk.vtkCubeAxesActor


class MachineActor : vtkCubeAxesActor() {
    val units = "mm"

    var machineLimits: MachineLimits = MachineLimits()
        set(value) {
            if (field != value) {
                field = value
                SetBounds(value.xMin, value.xMax, 0.0, 0.0, value.zMin, value.zMax)
                Modified()
            }
        }

    init {
        SetXLabelFormat("%6.3f")
        SetYLabelFormat("%6.3f")
        SetZLabelFormat("%6.3f")
        SetFlyModeToStaticEdges()

        //Hide the titles
        GetTitleTextProperty(0).SetOpacity(0.0)
        GetTitleTextProperty(2).SetOpacity(0.0)


        GetLabelTextProperty(0).apply {
            SetOrientation(90.0)
            SetColor(0.0, 1.0, 0.0)
            SetFontFamilyToCourier()
        }

        GetTitleTextProperty(2).SetOpacity(0.0)
        GetLabelTextProperty(2).SetColor(0.0, 0.0, 1.0)
        SetXUnits(units)
        SetYUnits(units)
        SetZUnits(units)
        DrawXGridlinesOn()
        DrawZGridlinesOn()
        GetXAxesGridlinesProperty()
            .apply {
                SetColor(0.0, 0.0, 0.0)
                SetOpacity(0.5)
            }
        GetYAxesGridlinesProperty().SetColor(0.0, 0.0, 0.0)
        GetZAxesGridlinesProperty().SetColor(0.0, 0.0, 0.0)
    }

    fun showMachineBounds(show: Boolean) {
        if (show) {
            XAxisVisibilityOn()
            YAxisVisibilityOn()
            ZAxisVisibilityOn()
        } else {
            XAxisVisibilityOff()
            YAxisVisibilityOff()
            ZAxisVisibilityOff()
        }
    }

    fun showMachineLabels(show: Boolean) {
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

    fun showGridLines(show: Boolean) {
        if (show) {
            DrawXGridlinesOn()
            DrawYGridlinesOn()
            DrawZGridlinesOn()
        } else {
            DrawZGridlinesOff()
            DrawZGridlinesOff()
            DrawZGridlinesOff()
        }
    }
}