package ui.screen.tools.root.tabs.cuttinginsert

import androidx.compose.ui.graphics.Color

enum class MaterialCode(
  val materialName: String,
  val ap: ClosedFloatingPointRange<Float>,
  val fn: ClosedFloatingPointRange<Float>,
  val vc: IntRange,
  val color: Color
) {
  P(
    materialName = "Steel",
    ap = 0.5f..2.5f,
    fn = 0.1f..0.6f,
    vc = 100..300,
    color = Color(0xff009fe0)
  ),
  M(
    materialName = "Stainless Steel",
    ap = 0.2f..2.5f,
    fn = 0.2f..0.8f,
    vc = 140..300,
    color = Color(0xffffee02)
  ),
  K(
    materialName = "Cast Iron",
    ap = 0.2f..2.5f,
    fn = 0.2f..0.8f,
    vc = 140..300,
    color = Color(0xffe40515)
  ),
  N(
    materialName = "Non Ferrous",
    ap = 0.2f..2.5f,
    fn = 0.2f..0.8f,
    vc = 140..300,
    color = Color(0xff9cc5b5)
  ),
  S(
    materialName = "Super Alloys",
    ap = 0.2f..2.5f,
    fn = 0.2f..0.8f,
    vc = 140..300,
    color = Color(0xfff6c18b)
  ),
  H(
    materialName = "Hardened Steel",
    ap = 0.2f..2.5f,
    fn = 0.2f..0.8f,
    vc = 140..300,
    color = Color(0xffc6d3db)
  ),
}