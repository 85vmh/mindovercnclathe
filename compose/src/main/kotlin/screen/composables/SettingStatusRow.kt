package screen.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingStatusRow(
    settingText: String,
    settingValue: String,
    settingUnit: String? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = settingText,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
        Row(
            modifier = Modifier.width(250.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier,
                text = settingValue,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            settingUnit?.let {
                Spacer(Modifier.size(8.dp))
                Text(
                    text = it,
                    modifier = Modifier.width(100.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}