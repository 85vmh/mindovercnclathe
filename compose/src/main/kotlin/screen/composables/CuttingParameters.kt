package screen.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import extensions.toFixedDigitsString
import screen.composables.tabconversational.InputSetting
import screen.uimodel.CuttingStrategy
import screen.uimodel.InputType
import usecase.model.CuttingParametersState

@Composable
fun CuttingParametersView(
    cuttingStrategy: CuttingStrategy,
    state: CuttingParametersState,
    modifier: Modifier = Modifier,
    contentSpacing: Dp = 8.dp,
) {
    var toolNo by state.toolNo
    var cssSpeed by state.cssSpeed
    var feed by state.feed
    var doc by state.doc

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 20.sp,
                    text = cuttingStrategy.text
                )
            }
            Divider(
                modifier = Modifier,
                color = Color.DarkGray,
                thickness = 1.dp
            )
            Column(
                modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(contentSpacing)
            ) {
                InputSetting(inputType = InputType.TOOL_NUMBER, value = toolNo.toString()) {
                    toolNo = it.toDouble().toInt()
                }
                InputSetting(inputType = InputType.CSS, value = cssSpeed.toString()) {
                    cssSpeed = it.toDouble().toInt()
                }
                InputSetting(inputType = InputType.FEED_PER_REV, value = feed.toFixedDigitsString()) {
                    feed = it.toDouble()
                }
                InputSetting(inputType = InputType.DOC, value = doc.toFixedDigitsString()) {
                    doc = it.toDouble()
                }
            }
        }
    }
}