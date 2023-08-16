package com.mindovercnc.linuxcnc.screen.programs.programloaded

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import okio.Path

@Composable
fun ProgramLoadedTitle(path: Path, modifier: Modifier = Modifier) {
    val titleStyle = MaterialTheme.typography.titleLarge
    val text = remember(titleStyle) {
        buildAnnotatedString {
            append("Program loaded ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("[${path.displayableFilePath()}]")
            }
        }
    }
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

private fun Path.displayableFilePath(): String {
    val elements = segments
    return if (elements.size > 2) {
        "../${elements.subList(elements.size - 2, elements.size).joinToString("/")}"
    } else {
        toString()
    }
}