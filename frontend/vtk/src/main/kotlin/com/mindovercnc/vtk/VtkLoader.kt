package com.mindovercnc.vtk

import vtk.vtkNativeLibrary

object VtkLoader {
    operator fun invoke() {
        //try loading libraries
        println(System.getProperty("java.library.path"))
        System.loadLibrary("jawt")
        if (!vtkNativeLibrary.LoadAllNativeLibraries()) {
            vtkNativeLibrary.values()
                .filter { !it.IsLoaded() }
                .forEach { lib ->
                    if (!lib.IsLoaded()) {
                        println(lib.GetLibraryName() + " not loaded")
                    }
                }
        }

        vtkNativeLibrary.DisableOutputWindow(null)
    }
}