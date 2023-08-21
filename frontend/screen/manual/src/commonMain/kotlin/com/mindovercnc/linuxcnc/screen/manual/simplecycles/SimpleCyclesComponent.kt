package com.mindovercnc.linuxcnc.screen.manual.simplecycles

import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import com.mindovercnc.model.SimpleCycleParameters

interface SimpleCyclesComponent : AppScreenComponent<SimpleCyclesState> {
    fun enterEditMode()
    fun exitEditMode()
    fun applyChanges()
    fun teachInXEnd()
    fun setXEnd(xEnd: Double)
    fun teachInMajorDiameter()
    fun setMajorDiameter(xCoordinate: Double)
    fun teachInMinorDiameter()
    fun setMinorDiameter(xCoordinate: Double)
    fun calculateFinalDepth()
    fun teachInZEnd()
    fun setZEnd(zCoordinate: Double)
    fun setDoc(doc: Double)
    fun setThreadPitch(threadPitch: Double)
    fun setFirstPassDepth(depth: Double)
    fun setFinalPassDepth(depth: Double)
    fun setThreadSpringPasses(passes: Int)
    fun setTaperAngle(angle: Double)
    fun setFilletRadius(radius: Double)
    fun setThreadLocation(isExternal: Boolean)
    fun setThreadType(threadType: SimpleCycleParameters.ThreadingParameters.ThreadType)
    fun setKeySlotCuttingFeed(feed: Double)
}
