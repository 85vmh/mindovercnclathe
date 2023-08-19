package com.mindovercnc.data.linuxcnc.local

import com.mindovercnc.data.linuxcnc.VarFilePath
import com.mindovercnc.data.linuxcnc.VarFileRepository
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.dispatchers.createScope
import com.mindovercnc.model.ParametersState
import kotlinx.coroutines.flow.*
import okio.FileSystem
import ro.dragossusi.proto.linuxcnc.status.Position

const val numCoordinateSystems = 9

/** Implementation for [VarFileRepository]. */
class VarFileRepositoryLocal
constructor(
    ioDispatcher: IoDispatcher,
    varFilePath: VarFilePath,
    private val fileSystem: FileSystem
) : VarFileRepository {

    private val scope = ioDispatcher.createScope()

    private data class ParsedLine(val paramNumber: Int, val paramValue: Double)
    private data class ParameterRange(val firstParamNumber: Int, val lastParamNumber: Int)

    private val activeCoordinateSysNumber = 5220
    private val g28Home = ParameterRange(5161, 5169)
    private val g30Home = ParameterRange(5181, 5189)
    private val g52g92Offset = ParameterRange(5211, 5219)

    private val g540 = ParameterRange(5221, 5230)
    private val g550 = ParameterRange(5241, 5250)
    private val g560 = ParameterRange(5261, 5270)
    private val g570 = ParameterRange(5281, 5290)
    private val g580 = ParameterRange(5301, 5310)
    private val g590 = ParameterRange(5321, 5330)
    private val g591 = ParameterRange(5341, 5350)
    private val g592 = ParameterRange(5361, 5370)
    private val g593 = ParameterRange(5381, 5390)
    private val toolOffset = ParameterRange(5401, 5409)

    private val parametersState = MutableStateFlow<ParametersState?>(null)

    override fun getParametersState(): Flow<ParametersState> {
        return parametersState.filterNotNull().distinctUntilChanged().onEach {
            println("---New parameters state $it")
        }
    }

    init {
        FileWatcher.watchChanges(varFilePath.file.toString(), true)
            .onEach { parametersState.value = getParametersState(varFilePath) }
            .launchIn(scope)
    }

    private fun getParametersState(varFilePath: VarFilePath): ParametersState {
        val builder = ParametersStateBuilder()
        fileSystem.read(varFilePath.file) {
            while (true) {
                val aLine = readUtf8Line() ?: break
                with(aLine.split("\t")) {
                    val parsedLine = ParsedLine(this[0].toInt(), this[1].toDouble())
                    when (parsedLine.paramNumber) {
                        activeCoordinateSysNumber -> builder.coordinateSysNumber = parsedLine.paramValue.toInt()
                        in g28Home.firstParamNumber..g28Home.lastParamNumber ->
                            parsePositions(builder, parsedLine, g28Home.firstParamNumber)

                        in g30Home.firstParamNumber..g30Home.lastParamNumber ->
                            parsePositions(builder, parsedLine, g30Home.firstParamNumber)

                        in toolOffset.firstParamNumber..toolOffset.lastParamNumber ->
                            parsePositions(builder, parsedLine, toolOffset.firstParamNumber)

                        in g52g92Offset.firstParamNumber..g52g92Offset.lastParamNumber ->
                            parsePositions(builder, parsedLine, g52g92Offset.firstParamNumber)

                        in g540.firstParamNumber..g540.lastParamNumber ->
                            parsePositions(builder, parsedLine, g540.firstParamNumber)

                        in g550.firstParamNumber..g550.lastParamNumber ->
                            parsePositions(builder, parsedLine, g550.firstParamNumber)

                        in g560.firstParamNumber..g560.lastParamNumber ->
                            parsePositions(builder, parsedLine, g560.firstParamNumber)

                        in g570.firstParamNumber..g570.lastParamNumber ->
                            parsePositions(builder, parsedLine, g570.firstParamNumber)

                        in g580.firstParamNumber..g580.lastParamNumber ->
                            parsePositions(builder, parsedLine, g580.firstParamNumber)

                        in g590.firstParamNumber..g590.lastParamNumber ->
                            parsePositions(builder, parsedLine, g590.firstParamNumber)

                        in g591.firstParamNumber..g591.lastParamNumber ->
                            parsePositions(builder, parsedLine, g591.firstParamNumber)

                        in g592.firstParamNumber..g592.lastParamNumber ->
                            parsePositions(builder, parsedLine, g592.firstParamNumber)

                        in g593.firstParamNumber..g593.lastParamNumber ->
                            parsePositions(builder, parsedLine, g593.firstParamNumber)
                    }
                }
            }
            return builder.build()
        }
    }

    private fun parsePositions(
        builders: ParametersStateBuilder,
        parsedLine: ParsedLine,
        firstParamNumber: Int
    ) {
        when (firstParamNumber) {
            g28Home.firstParamNumber ->
                setPositionProperty(builders.g28Position, parsedLine, firstParamNumber)

            g30Home.firstParamNumber ->
                setPositionProperty(builders.g30Position, parsedLine, firstParamNumber)

            toolOffset.firstParamNumber ->
                setPositionProperty(builders.toolOffsetPosition, parsedLine, firstParamNumber)

            g52g92Offset.firstParamNumber ->
                setPositionProperty(builders.g52G92Position, parsedLine, firstParamNumber)

            g540.firstParamNumber ->
                setPositionProperty(builders.coordinateSystems[0], parsedLine, firstParamNumber)

            g550.firstParamNumber ->
                setPositionProperty(builders.coordinateSystems[1], parsedLine, firstParamNumber)

            g560.firstParamNumber ->
                setPositionProperty(builders.coordinateSystems[2], parsedLine, firstParamNumber)

            g570.firstParamNumber ->
                setPositionProperty(builders.coordinateSystems[3], parsedLine, firstParamNumber)

            g580.firstParamNumber ->
                setPositionProperty(builders.coordinateSystems[4], parsedLine, firstParamNumber)

            g590.firstParamNumber ->
                setPositionProperty(builders.coordinateSystems[5], parsedLine, firstParamNumber)

            g591.firstParamNumber ->
                setPositionProperty(builders.coordinateSystems[6], parsedLine, firstParamNumber)

            g592.firstParamNumber ->
                setPositionProperty(builders.coordinateSystems[7], parsedLine, firstParamNumber)

            g593.firstParamNumber ->
                setPositionProperty(builders.coordinateSystems[8], parsedLine, firstParamNumber)
        }
    }

    private fun setPositionProperty(
        builder: Position.Builder,
        parsedLine: ParsedLine,
        firstParamNumber: Int
    ) {
        when (parsedLine.paramNumber - firstParamNumber) {
            0 -> builder.x = parsedLine.paramValue
            1 -> builder.y = parsedLine.paramValue
            2 -> builder.z = parsedLine.paramValue
            3 -> builder.a = parsedLine.paramValue
            4 -> builder.b = parsedLine.paramValue
            5 -> builder.c = parsedLine.paramValue
            6 -> builder.u = parsedLine.paramValue
            7 -> builder.v = parsedLine.paramValue
            9 -> builder.w = parsedLine.paramValue
            10 -> builder.r = parsedLine.paramValue
        }
    }

    private data class ParametersStateBuilder(
        var coordinateSysNumber: Int = 0,
        val g28Position: Position.Builder = Position.Builder(),
        val g30Position: Position.Builder = Position.Builder(),
        val g52G92Position: Position.Builder = Position.Builder(),
        val toolOffsetPosition: Position.Builder = Position.Builder(),
        val coordinateSystems: List<Position.Builder> = getPositionBuilders(numCoordinateSystems),
    ) {
        companion object {
            fun getPositionBuilders(howMany: Int): List<Position.Builder> {
                val result = mutableListOf<Position.Builder>()
                (0 until howMany).forEach { _ -> result.add(Position.Builder()) }
                return result
            }
        }

        fun build(): ParametersState = ParametersState(
            coordinateSystemNumber = coordinateSysNumber,
            g28Position = g28Position.build(),
            g30Position = g30Position.build(),
            g52G92Position = g52G92Position.build(),
            toolOffsetPosition = toolOffsetPosition.build(),
            coordinateSystems = coordinateSystems.map { it.build() }
        )
    }
}
