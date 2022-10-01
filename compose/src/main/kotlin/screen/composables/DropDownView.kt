package screen.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropDownView(
    items: List<T>,
    selected: T?,
    onSelected: (T) -> Unit,
    closedItemContent: @Composable (T?) -> Unit,
    openedItemContent: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    dropDownListModifier: Modifier = Modifier
) {
    var isOpen by remember {
        mutableStateOf(false)
    }

    Surface(
        modifier = modifier,
        onClick = {
            isOpen = true
        }
    ) {
        closedItemContent(selected)
        DropDownList(
            modifier = dropDownListModifier,
            requestToOpen = isOpen,
            list = items,
            onDismiss = { isOpen = false },
            onSelected = onSelected,
            itemContent = openedItemContent
        )
    }
}

@Composable
fun DropDownClosedItem(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        content.invoke()
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            tint = Color.LightGray,
            contentDescription = null
        )
    }
}

@Composable
private fun <T> DropDownList(
    modifier: Modifier = Modifier,
    columnModifier: Modifier = Modifier,
    requestToOpen: Boolean = false,
    list: List<T>,
    onDismiss: () -> Unit,
    onSelected: (T) -> Unit,
    itemContent: @Composable (T) -> Unit
) {
    DropdownMenu(
        modifier = columnModifier,
        expanded = requestToOpen,
        onDismissRequest = onDismiss,
    ) {
        list.forEach { content ->
            DropdownMenuItem(
                modifier = modifier,
                onClick = {
                    onDismiss()
                    onSelected(content)
                }
            ) {
                itemContent(content)
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    DropDownClosedItem(
        modifier = Modifier.size(200.dp)
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "A Test",
        )
    }
}