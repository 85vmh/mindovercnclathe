package components.breadcrumb

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

internal class TrapezeShape(private val xOffset: Float = 10f) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path =
            Path().apply {
                moveTo(0f, 0f)
                lineTo(0f, size.height)
                lineTo(size.width - xOffset, size.height)
                lineTo(size.width, 0f)
                lineTo(0f, 0f)
            }
        return Outline.Generic(path)
    }
}