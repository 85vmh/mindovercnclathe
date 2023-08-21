package com.mindovercnc.linuxcnc.screen.manual.root.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.SimpleCycle
import org.jetbrains.compose.resources.*
import scroll.draggableScroll

@Composable
fun SimpleCyclesGrid(
    items: List<SimpleCycle>,
    onCycleSelected: (SimpleCycle) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val scrollState = rememberLazyGridState()
    val scope = rememberCoroutineScope()

    LazyVerticalGrid(
        modifier = modifier.draggableScroll(scrollState, scope),
        state = scrollState,
        contentPadding = contentPadding,
        columns = GridCells.Adaptive(128.dp),
    ) {
        items(items.size) { index ->
            Cycle(
                op = items[index],
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
            ) {
                onCycleSelected.invoke(items[index])
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Cycle(op: SimpleCycle, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(4.dp).fillMaxWidth(),
        ) {
            val imageSize = 100.dp
            val imgName = op.imgName
            when {
                imgName?.endsWith(".png") == true -> {
                    Image(
                        modifier =
                            Modifier.size(imageSize)
                                .background(
                                    color = MaterialTheme.colorScheme.background,
                                    shape = RoundedCornerShape(6.dp),
                                ),
                        contentDescription = "",
                        bitmap = resource(imgName).rememberImageBitmap().orEmpty()
                    )
                }
                imgName?.endsWith(".xml") == true -> {
                    Image(
                        modifier =
                            Modifier.size(imageSize)
                                .background(
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(6.dp),
                                ),
                        contentDescription = "",
                        imageVector =
                            resource(imgName).rememberImageVector(LocalDensity.current).orEmpty()
                    )
                }
                else -> {
                    Box(
                        modifier =
                            Modifier.size(imageSize)
                                .background(
                                    color = MaterialTheme.colorScheme.background,
                                    shape = RoundedCornerShape(6.dp),
                                )
                    )
                }
            }
            Text(
                text = op.displayableString,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
