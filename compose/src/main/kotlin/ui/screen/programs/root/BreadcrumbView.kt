package ui.screen.programs.root

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import extensions.draggableScroll
import java.io.File

private class ParallelogramShape(private val xOffset: Float = 10f) : Shape {

    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val path = Path().apply {
            moveTo(xOffset, 0f)
            lineTo(0f, size.height)
            lineTo(size.width - xOffset, size.height)
            lineTo(size.width, 0f)
            lineTo(0f, 0f)
        }
        return Outline.Generic(path)
    }
}

private class TrapezeShape(private val xOffset: Float = 10f) : Shape {

    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(0f, size.height)
            lineTo(size.width - xOffset, size.height)
            lineTo(size.width, 0f)
            lineTo(0f, 0f)
        }
        return Outline.Generic(path)
    }
}

fun File.directories(): List<File> {
    val list = mutableListOf<File>()
    if (isDirectory) {
        list += this
    }
    var parent = parentFile
    while (parent != null) {
        list += parent
        parent = parent.parentFile
    }
    return list.reversed()
}

@Composable
fun BreadcrumbView(
    item: File,
    onSelect: (File) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val items = item.directories()

    val trapezeShape = TrapezeShape()
    val parallelogramShape = ParallelogramShape()

    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LazyRow(
        modifier = modifier.draggableScroll(scrollState, scope, Orientation.Horizontal),
        state = scrollState,
        contentPadding = contentPadding
    ) {
        itemsIndexed(items) { index, item ->
            BreadcrumbItem(
                shape = if (index == 0) trapezeShape else parallelogramShape,
                item = item,
                onClick = {
                    onSelect(it)
                },
                modifier = Modifier.fillMaxHeight(),
                selected = index == items.lastIndex
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BreadcrumbItem(
    shape: Shape,
    item: File,
    selected: Boolean,
    onClick: (File) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        shape = shape,
        onClick = { onClick(item) },
        color = if (selected) MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.surface
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxHeight(),
        ) {
            Text(
                text = item.name.ifEmpty { "root" },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}