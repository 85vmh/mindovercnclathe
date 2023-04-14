package com.mindovercnc.model

import ro.dragossusi.proto.linuxcnc.status.Position

data class ParametersState(
  val g28Position: Position,
  val g30Position: Position,
  val g52G92Position: Position,
  val toolOffsetPosition: Position,
  val coordinateSystemNumber: Int,
  val coordinateSystems: List<Position>,
)
