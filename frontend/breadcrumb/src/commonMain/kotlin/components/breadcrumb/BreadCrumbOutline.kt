package components.breadcrumb

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path

internal fun BreadCrumbOutline(xOffset: Float, size: Size): Outline.Generic {
  val path =
    Path().apply {
      moveTo(xOffset, 0f)
      lineTo(0f, size.height)
      lineTo(size.width - xOffset, size.height)
      lineTo(size.width, 0f)
      lineTo(0f, 0f)
    }
  return Outline.Generic(path)
}
