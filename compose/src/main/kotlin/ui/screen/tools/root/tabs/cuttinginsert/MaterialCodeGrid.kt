package ui.screen.tools.root.tabs.cuttinginsert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val headerModifier = Modifier.height(LegendTokens.HeaderHeight)
private val cellModifier = Modifier.height(LegendTokens.CellHeight)

private val materialCodes = listOf(
    MaterialFeedsSpeeds(
        letter = 'P',
        material = "Steel",
        color = Color(0xff009fe0),
    ),
    MaterialFeedsSpeeds(
        letter = 'M',
        material = "Stainless Steel",
        color = Color(0xffffee02),
    ),
    MaterialFeedsSpeeds(
        letter = 'K',
        material = "Cast Iron",
        color = Color(0xffe40515),
    ),
    MaterialFeedsSpeeds(
        letter = 'N',
        material = "Non Ferrous",
        color = Color(0xff9cc5b5),
    ),
    MaterialFeedsSpeeds(
        letter = 'S',
        material = "Super Alloys",
        color = Color(0xfff6c18b),
    ),
    MaterialFeedsSpeeds(
        letter = 'H',
        material = "Hardened Steel",
        color = Color(0xffc6d3db),
    )
)

@Composable
fun MaterialCodeGrid(modifier: Modifier = Modifier) {


    val materialCodes = remember { materialCodes.toList() }

    Surface(modifier = modifier) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(materialCodes.size + 1),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            materialCodesHeader(materialCodes)
            materialCodesContent(materialCodes)
        }
    }
}

private fun LazyGridScope.materialCodesHeader(materialCodes: List<MaterialFeedsSpeeds>) {
    item { Spacer(modifier = headerModifier) }

    items(materialCodes) { materialCode ->
        Row(
            modifier = headerModifier.background(materialCode.color).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = materialCode.letter.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun LazyGridScope.materialCodesContent(materialCodes: List<MaterialFeedsSpeeds>) {
    item {
        Divider(modifier = Modifier.fillMaxWidth())
        HeaderCell(title = "Ap", subhead = "(mm)", modifier = cellModifier.fillMaxWidth())
    }
    items(materialCodes) { materialCode ->
        Divider(modifier = Modifier.fillMaxWidth())
        ClosedRangeCell(materialCode.ap)
    }

    item {
        Divider(modifier = Modifier.fillMaxWidth())
        HeaderCell(title = "Fn", subhead = "(mm/rev)", modifier = cellModifier.fillMaxWidth())
    }
    items(materialCodes) { materialCode ->
        Divider(modifier = Modifier.fillMaxWidth())
        ClosedRangeCell(materialCode.fn)
    }

    item {
        Divider(modifier = Modifier.fillMaxWidth())
        HeaderCell(title = "Vc", subhead = "(m/min)", modifier = cellModifier.fillMaxWidth())
    }
    items(materialCodes) { materialCode ->
        Divider(modifier = Modifier.fillMaxWidth())
        ClosedRangeCell(materialCode.vc)
    }
}

@Composable
private fun HeaderCell(title: String, subhead: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        Text(text = subhead, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun ClosedRangeCell(
    range: ClosedRange<*>?,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = cellModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (range != null) {
            Text(
                text = "${range.start} - ${range.endInclusive}",
                style = MaterialTheme.typography.bodySmall
            )
        } else {
            IconButton(
                modifier = Modifier.size(48.dp),
                onClick = onClick
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "",
                )
            }
        }
    }
}
