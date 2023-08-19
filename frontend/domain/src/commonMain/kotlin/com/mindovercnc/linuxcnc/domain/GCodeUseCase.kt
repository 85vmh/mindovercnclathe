package com.mindovercnc.linuxcnc.domain

import actor.PathElement
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.linuxcnc.gcode.GCodeInterpreterRepository
import kotlinx.coroutines.withContext
import okio.Path
import org.jetbrains.skia.Point

class GCodeUseCase(private val gCodeInterpreterRepository: GCodeInterpreterRepository, private val ioDispatcher: IoDispatcher) {

    suspend fun getPathElements(file: Path): List<PathElement> =
        withContext(ioDispatcher.dispatcher) {
            val pathElements = mutableListOf<PathElement>()
            var lastPoint: Point? = null

            gCodeInterpreterRepository.parseFile(file).forEach { command ->
                when (command.name) {
                    "STRAIGHT_TRAVERSE",
                    "STRAIGHT_FEED" -> {
                        val args = command.arguments.split(", ")
                        val current =
                            Point(
                                args[0].toFloat(),
                                args[2].toFloat()
                            )

                        when (lastPoint) {
                            null -> lastPoint = current
                            else -> {
                                pathElements.add(
                                    PathElement.Line(
                                        startPoint = lastPoint!!,
                                        endPoint = current,
                                        type =
                                        when (command.name) {
                                            "STRAIGHT_FEED" -> PathElement.Line.Type.Feed
                                            else -> PathElement.Line.Type.Traverse
                                        }
                                    )
                                )
                                lastPoint = current
                            }
                        }
                    }

                    "ARC_FEED" -> {
                        val args = command.arguments.split(", ")
                        val current =
                            Point(
                                args[1].toFloat(),
                                args[0].toFloat()
                            )
                        val center =
                            Point(
                                args[3].toFloat(),
                                args[2].toFloat()
                            )
                        when (lastPoint) {
                            null -> lastPoint = current
                            else -> {
                                pathElements.add(
                                    PathElement.Arc(
                                        startPoint = lastPoint!!,
                                        endPoint = current,
                                        centerPoint = center,
                                        direction =
                                        if (args[4].toInt() > 0) PathElement.Arc.Direction.Clockwise
                                        else PathElement.Arc.Direction.CounterClockwise
                                    )
                                )
                                lastPoint = current
                            }
                        }
                    }

                    else -> {}
                }
            }
            pathElements
        }
}
