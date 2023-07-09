package ui.widget.handle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DividerDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.SplitPaneScope
import java.awt.Cursor

fun Modifier.cursorForHorizontalResize(): Modifier =
    pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))

@OptIn(ExperimentalSplitPaneApi::class)
fun SplitPaneScope.defaultSplitter() {
    splitter {
        visiblePart {
            Box(
                Modifier.width(DividerDefaults.Thickness)
                    .fillMaxHeight()
                    .background(DividerDefaults.color)
            )
        }
        handle {
            Modifier.markAsHandle()
                .cursorForHorizontalResize()
                .background(SolidColor(Color.Gray), alpha = 0.50f)
                .width(16.dp)
                .fillMaxHeight()
        }
    }
}
