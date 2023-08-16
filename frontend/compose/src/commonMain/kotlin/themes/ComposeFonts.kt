package themes

import androidx.compose.ui.text.font.FontFamily

// TODO resolve fonts for mpp
@Deprecated("Remove")
object ComposeFonts {
//    val position =  getFont("OxygenMono-Regular", FontWeight.Normal, FontStyle.Normal)

//    private fun getFont(res: String, weight: FontWeight, style: FontStyle) = Font("font/$res.ttf", weight, style)

    object Family {
        //        val position = FontFamily(ComposeFonts.position)
        val position = FontFamily.Monospace
    }
}