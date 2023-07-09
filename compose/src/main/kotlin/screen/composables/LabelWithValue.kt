package screen.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelWithValue(
    label: String,
    value: String,
    paddingStart: Dp = 16.dp,
    paddingEnd: Dp = 16.dp,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineText = {

            Text(
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(start = paddingStart),
                text = label
            )
        },
        supportingText = {

            Text(
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(end = paddingEnd),
                text = value
            )
        },
        modifier = modifier.fillMaxWidth(),
    )
}