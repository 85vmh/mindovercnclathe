package ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable

@Composable
fun Table(
    rowCount: Int,
    columnCount: Int,
    itemContent: @Composable (Int, Int) -> Unit
) {
    LazyColumn {
        items(rowCount) { row ->
            Row {
                repeat(columnCount) { column ->
                    itemContent(row, column)
                }
            }
        }
    }
}