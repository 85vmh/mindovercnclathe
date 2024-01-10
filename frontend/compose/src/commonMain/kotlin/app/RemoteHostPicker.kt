package app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RemoteHostPicker(
    onHostPick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (host, setHost) = remember { mutableStateOf("localhost") }
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        OutlinedTextField(
            value = host,
            onValueChange = setHost,
            label = { Text("Host") },
        )
        Button(onClick = { onHostPick(host) }, enabled = host.isNotBlank()) { Text("Continue") }
    }
}
