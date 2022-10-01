package codegen

import screen.uimodel.Wcs
import java.util.*

class ConversationalProgram(
    private val programName: String,
    private val creationDate: Date,
    private val wcs: Wcs = Wcs.G54,
//    private val stockDetails: StockDetails,
    private val operations: List<Operation>
) {

    fun generateGCode(): List<String> {
        val lines = mutableListOf<String>()
        lines.add("(MindOverCNC Lathe - Conversational)")
        lines.add("(Program Name: $programName)")
        lines.add("(Creation Date: $creationDate)")
        lines.add("\r")
        lines.add("G7  (Diameter Mode)")
        lines.add("G18 (XZ Plane)")
        lines.add("G21 (Metric Units)")
        lines.add("G90 (Absolute Distance Mode)")
        lines.add("G95 (Feed in units/rev)")
        lines.add("${wcs.text} (Workpiece Coordinate System)")
        lines.add("\r")
        operations.forEach {
            with(it) {
                lines.addAll(this.getStartComment())
                lines.add("\r")
                lines.addAll(this.getOperationCode())
                lines.add("\r")
                lines.addAll(this.getEndComment())
            }
        }
        lines.add("M5 (Stop Spindle)")
        lines.add("M2 (End Program)")
        return lines
    }
}