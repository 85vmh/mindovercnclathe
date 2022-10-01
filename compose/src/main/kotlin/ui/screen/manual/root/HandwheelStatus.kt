package ui.screen.manual.root

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HandWheelStatus(
    uiModel: HandWheelsUiModel?
) {
    val activeStatus = uiModel?.let {
        if (it.active && it.increment > 0) "Active" else "Inactive"
    } ?: "Inactive"

    val increment = uiModel?.let {
        "${it.increment} ${it.units}"
    }

    Row(
        modifier = Modifier
            .padding(8.dp),
        //.border(BorderStroke(0.5.dp, SolidColor(Color.DarkGray))),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource("hwheel.xml"),
            contentDescription = ""
        )
        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                text = "HandWheels: $activeStatus"
            )
            Text(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                text = "Increment: $increment"
            )
        }
    }
}