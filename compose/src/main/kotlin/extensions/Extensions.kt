package extensions

import androidx.compose.foundation.gestures.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import com.mindovercnc.model.Point2D
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Sets the displayable digits to max digits, which will be shown even if they are zero.
 */
fun Double.toFixedDigitsString(maxDigits: Int = 3): String {
    return BigDecimal(this).setScale(maxDigits, RoundingMode.HALF_EVEN).toString()
}

fun Double.toFixedDigits(maxDigits: Int = 3): Double {
    return BigDecimal(this).setScale(maxDigits, RoundingMode.HALF_EVEN).toDouble()
}

/**
 * Strips the decimal points digits that are zero.
 */
fun Double.stripZeros(maxDigits: Int = 3): String {
    return BigDecimal(this).setScale(maxDigits, RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString()
}

@Composable
fun Modifier.draggableScroll(
    scrollState: ScrollableState,
    scope: CoroutineScope,
    orientation: Orientation = Orientation.Vertical
): Modifier {
    return draggable(rememberDraggableState { delta ->
        scope.launch {
            scrollState.scrollBy(-delta)
        }
    }, orientation = orientation)
}

fun Point2D.toOffset(multiplicationFactor: Float = 1f) = Offset(
    x = (z * multiplicationFactor).toFloat(),
    y = (x * multiplicationFactor).toFloat()
)