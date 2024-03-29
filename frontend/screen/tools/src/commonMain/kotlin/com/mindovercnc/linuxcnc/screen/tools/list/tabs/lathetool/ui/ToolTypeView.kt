package com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.tools.model.ToolType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.orEmpty
import org.jetbrains.compose.resources.rememberImageBitmap
import org.jetbrains.compose.resources.resource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun ToolTypeView(
    type: ToolType,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: (ToolType) -> Unit
) {
    val imageResource =
        when (type) {
            ToolType.Turning -> "lathe-tool.png"
            ToolType.Boring -> "lathe-tool.png"
            ToolType.Drilling -> "lathe-tool.png"
            ToolType.Reaming -> "lathe-tool.png"
            ToolType.Grooving -> "lathe-tool.png"
            ToolType.Parting -> "lathe-tool.png"
            ToolType.OdThreading -> "lathe-tool.png"
            ToolType.IdThreading -> "lathe-tool.png"
            ToolType.Slotting -> "lathe-tool.png"
        }

    val color =
        if (isSelected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.primaryContainer
    Card(
        modifier = modifier.width(120.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = { onClick(type) },
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                bitmap = resource(imageResource).rememberImageBitmap().orEmpty(),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = type.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
