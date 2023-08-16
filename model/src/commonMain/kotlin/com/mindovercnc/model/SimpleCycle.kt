package com.mindovercnc.model

enum class SimpleCycle(
    val displayableString: String,
    val imgName: String? = null,
) {
    Turning("OD Turning", "od_turn.xml"),
    Boring("ID Turning", "id_turn.xml"),
    OdChamfer("OD Chamfer", "od_chamfer.xml"),
    IdChamfer("ID Chamfer", "id_chamfer.xml"),
    OdRadius("OD Radius", "od_radius.xml"),
    IdRadius("ID Radius", "id_radius.xml"),
    Facing("Facing", "facing.xml"),
    Threading("Threading", "threading.png"),
    Drilling("Drilling/Reaming"),
    KeySlot("Slotting", "slotting.png"),
}
