package screen.composables.editor

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.mindovercnc.editor.Editor
import com.mindovercnc.editor.EditorThemeVariant
import extensions.draggableScroll
import kotlin.text.Regex.Companion.fromLiteral
import okio.Path
import screen.composables.common.Fonts
import screen.composables.common.Settings
import screen.composables.platform.VerticalScrollbar
import screen.composables.util.loadableScoped
import screen.composables.util.withoutWidthConstraints

@Composable
fun EditorView(
  model: Editor,
  settings: Settings,
  showFileName: Boolean = true,
  modifier: Modifier = Modifier
) =
  key(model) {
    val editorTheme = LocalEditorTheme.current
    Surface(
      modifier = modifier,
      color = editorTheme.background.toColor(),
    ) {
      val lines by loadableScoped(model.lines)

      if (lines != null) {
        Lines(
          file = model.file,
          lines = lines!!,
          showFileName = showFileName,
          settings = settings,
          modifier = Modifier.fillMaxSize()
        )
      } else {
        CircularProgressIndicator(modifier = Modifier.size(36.dp).padding(4.dp))
      }
    }
  }

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Lines(
  file: Path,
  lines: Editor.Lines,
  showFileName: Boolean,
  settings: Settings,
  modifier: Modifier = Modifier
) =
  with(LocalDensity.current) {
    val maxNum =
      remember(lines.lineNumberDigitCount) {
        (1..lines.lineNumberDigitCount).joinToString(separator = "") { "9" }
      }

    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {
      val scrollState = rememberLazyListState()
      val lineHeight = settings.fontSize.toDp() * 2f

      LazyColumn(
        modifier = Modifier.fillMaxSize().draggableScroll(scrollState, scope),
        state = scrollState
      ) {
        if (showFileName) {
          stickyHeader {
            Surface(
              modifier = Modifier.fillMaxWidth(),
              border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
              FileNameHeader(file, modifier = Modifier.fillMaxWidth())
            }
          }
        }
        items(lines.size) { index ->
          Box(modifier = Modifier.height(lineHeight)) {
            Line(
              maxNum = maxNum,
              line = lines[index],
              settings = settings,
              modifier = Modifier.align(Alignment.CenterStart)
            )
          }
        }
      }

      VerticalScrollbar(
        Modifier.align(Alignment.CenterEnd).width(20.dp),
        scrollState,
        lines.size,
        lineHeight
      )
    }
  }

@Composable
private fun Line(
  maxNum: String,
  line: Editor.Line,
  settings: Settings,
  modifier: Modifier = Modifier
) {
  Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
    DisableSelection {
      val background = LocalEditorTheme.current.lineNumber.background.toColor()
      Box(modifier = Modifier.background(background)) {
        LineNumber(
          number = maxNum,
          settings = settings,
          modifier = Modifier.alpha(0f).padding(start = 12.dp)
        )
        LineNumber(
          number = line.number.toString(),
          settings = settings,
          modifier = Modifier.align(Alignment.CenterEnd).padding(start = 12.dp)
        )
      }
      LineContent(
        line.content,
        modifier =
          Modifier.weight(1f).withoutWidthConstraints().padding(start = 28.dp, end = 12.dp),
        settings = settings
      )
    }
  }
}

@Composable
private fun LineNumber(number: String, settings: Settings, modifier: Modifier = Modifier) =
  Text(
    text = number,
    fontSize = settings.fontSize,
    fontFamily = Fonts.jetbrainsMono(),
    color = LocalEditorTheme.current.lineNumber.text.toColor(),
    modifier = modifier
  )

@Composable
private fun LineContent(
  content: Editor.Content,
  settings: Settings,
  modifier: Modifier = Modifier
) {
  val editorTheme = LocalEditorTheme.current
  val contentValue by content.value.collectAsState()
  Text(
    text =
      if (content.isGCode) {
        codeString(editorTheme, contentValue)
      } else {
        val style = editorTheme.text.toSpanStyle()
        buildAnnotatedString { withStyle(style) { append(contentValue) } }
      },
    fontSize = settings.fontSize,
    fontFamily = Fonts.jetbrainsMono(),
    modifier = modifier,
    softWrap = false
  )
}

private fun codeString(editorTheme: EditorThemeVariant, str: String): AnnotatedString {
  val keyword = editorTheme.keyword.toSpanStyle()
  val punctuation = editorTheme.punctuation.toSpanStyle()
  val value = editorTheme.value.toSpanStyle()
  val comment = editorTheme.comment.toSpanStyle()
  val variable = editorTheme.variable.toSpanStyle()
  val gcode = editorTheme.gcode.toSpanStyle()

  val strFormatted = str.replace("\t", "    ")

  return buildAnnotatedString {
    withStyle(editorTheme.text.toSpanStyle()) {
      append(strFormatted)
      addStyle(punctuation, strFormatted, ":")
      addStyle(punctuation, strFormatted, "=")
      addStyle(punctuation, strFormatted, "\"")
      addStyle(punctuation, strFormatted, "[")
      addStyle(punctuation, strFormatted, "]")
      addStyle(punctuation, strFormatted, "{")
      addStyle(punctuation, strFormatted, "}")

      // gcode
      addGcodeStyle(gcode, strFormatted)

      // variable
      addStyle(variable, strFormatted, Regex("[#oO]<(.+)>"))

      // keywords
      addKeywords(keyword, strFormatted)

      // value
      addStyle(value, strFormatted, "true")
      addStyle(value, strFormatted, "false")

      // number
      addStyle(value, strFormatted, Regex("(^|\\s+)(-?\\d+.?\\d*)(?=\\s+|\$)"))

      // addStyle(AppTheme.code.value, strFormatted, Regex("[0-9]*"))
      // addStyle(AppTheme.code.annotation, strFormatted, Regex("^@[a-zA-Z_]*"))
      addStyle(comment, strFormatted, Regex("^\\s*; .*"))
      addStyle(comment, strFormatted, Regex("\\((.*)\\)"))
    }
  }
}

private fun AnnotatedString.Builder.addKeywords(style: SpanStyle, text: String) {
  val words =
    arrayOf(
      "return",
      "if",
      "else",
      "endif",
      "while",
      "endwhile",
      "sub",
      "endsub",
      "repeat",
      "endrepeat",
    )
  words.forEach {
    addStyle(style, text, Regex("(^|\\s+)($it)(?=\\s+|\$)"))
    addStyle(style, text, Regex("(^|\\s+)(${it.uppercase()})(?=\\s+|\$)"))
  }
}

private fun AnnotatedString.Builder.addGcodeStyle(style: SpanStyle, text: String) {
  val letters =
    charArrayOf(
      'G',
      'M',
      'X',
      'Y',
      'Z',
      'I',
      'J',
      'K',
      'F',
      'S',
      'T',
      'P',
      'L',
      'Q',
      'N',
      'E',
      'D',
      'R',
      'B',
      'O',
      'A',
    )
  val letterString =
    letters.joinToString(separator = "", prefix = "[", postfix = "]") { "$it${it.lowercaseChar()}" }
  addStyle(style, text, Regex("(^|\\s+)($letterString)(-?\\d+\\.?\\d*)(?=\\s+|\$)"))
}

private fun AnnotatedString.Builder.addStyle(style: SpanStyle, text: String, regexp: String) {
  addStyle(style, text, fromLiteral(regexp))
}

private fun AnnotatedString.Builder.addStyle(style: SpanStyle, text: String, regexp: Regex) {
  for (result in regexp.findAll(text)) {
    addStyle(style, result.range.first, result.range.last + 1)
  }
}
