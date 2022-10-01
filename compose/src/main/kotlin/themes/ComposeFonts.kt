package themes

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

object ComposeFonts {
    val position =  getFont("OxygenMono-Regular", FontWeight.Normal, FontStyle.Normal)

    private fun getFont(res: String, weight: FontWeight, style: FontStyle) = Font("font/$res.ttf", weight, style)

    object Family{
        val position = FontFamily(ComposeFonts.position)
    }
}