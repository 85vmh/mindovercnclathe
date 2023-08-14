package codegen

import com.mindovercnc.linuxcnc.format.stripZeros

interface ProfileGeometry {

    fun getProfile(): List<String>

    val subroutineNumber: Int

    val hasPockets: Boolean
}

class OdProfileGeometry(
    private val xInitial: Double,
    private val xFinal: Double,
    val zStart: Double,
    private val zEnd: Double,
    private val filletRadius: Double
) : ProfileGeometry {

    override fun getProfile(): List<String> {
        return buildList {
            add("G0 X${xFinal.stripZeros()}")
            add("G1 Z${(filletRadius + zEnd).stripZeros()}")
            add(
                "G2 X${(filletRadius + xFinal).stripZeros()} Z${(zEnd).stripZeros()} R${filletRadius.stripZeros()}"
            )
            add("G1 X${xInitial.stripZeros()}")
        }
    }

    override val subroutineNumber: Int
        get() = 100

    override val hasPockets: Boolean
        get() = false
}
