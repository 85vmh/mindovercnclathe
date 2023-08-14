//package codegen
//
//import com.mindovercnc.linuxcnc.format.toFixedDigitsString
//
//class DrillingOperation(
//    private val toolNumber: Int,
//    private val spindleSpeed: Int,
//    private val feedRate: Double,
//    private val zStartPosition: Double,
//    private val zEndPosition: Double,
//    private val increment: Double,
//    private val repeat: Int? = null
//) : Operation {
//
//    override fun getStartComment(): List<String> {
//        return mutableListOf<String>().apply {
//            add("(---BEGIN---Drilling operation-----------)")
//        }
//    }
//
//    override fun getEndComment(): List<String> {
//        return mutableListOf<String>().apply {
//            add("(----END----Drilling operation-----------)")
//        }
//    }
//
//
//    override fun getOperationCode() = mutableListOf<String>()
//        .apply {
//            add("G95 F${feedRate.toFixedDigitsString()}")
//            add("G97 S$spindleSpeed")
//
//            add("M6 T$toolNumber G43")
//            add("G0 Z${zStartPosition.toFixedDigitsString()} (move to Z start position)")
//            add("G0 X0") //when drilling always move to x=0
//            add(
//                "G73 " +
//                        "Z${zEndPosition.toFixedDigitsString()} " +
//                        "R${zStartPosition.toFixedDigitsString()} " +
//                        "Q$increment " +
//                        if (repeat != null) "L$repeat" else ""
//            )
//        }
//}