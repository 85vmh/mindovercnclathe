package com.mindovercnc.linuxcnc.domain

import actor.PathElement
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.linuxcnc.gcode.GCodeInterpreterRepository
import com.mindovercnc.model.Point3D
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import okio.Path

class GCodeUseCase(
    private val gCodeInterpreterRepository: GCodeInterpreterRepository,
    private val ioDispatcher: IoDispatcher,
) {

    suspend fun getPathElements(file: Path): List<PathElement> =
        withContext(ioDispatcher.dispatcher) {
            val pathElements = mutableListOf<PathElement>()
            var lastPoint: Point3D? = null

            for (command in gCodeInterpreterRepository.parseFile(file)) {
                when (command.name) {
                    "STRAIGHT_TRAVERSE",
                    "STRAIGHT_FEED" -> {
                        val args = command.arguments.split(", ")
                        // TODO: This creates an issue when rendering arcs, the lates operate in X
                        // and Z
                        val current =
                            Point3D(
                                x = args[0].toFloat(),
                                y = args[1].toFloat(),
                                z = args[2].toFloat(),
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
                            Point3D(
                                x = args[1].toFloat(),
                                y = 0f,
                                z = args[0].toFloat(),
                            )
                        val center =
                            Point3D(
                                x = args[3].toFloat(),
                                y = 0f,
                                z = args[2].toFloat(),
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
                                            if (args[4].toInt() > 0)
                                                PathElement.Arc.Direction.Clockwise
                                            else PathElement.Arc.Direction.CounterClockwise
                                    )
                                )
                                lastPoint = current
                            }
                        }
                    }
                    "COMMENT" -> {
                        /* no-op */
                    }
                    else -> {
                        LOG.debug { "Command not handled: ${command.name}" }
                    }
                }
            }
            pathElements
        }

    companion object {
        private val LOG = KotlinLogging.logger("GCodeUseCase")
    }
}
