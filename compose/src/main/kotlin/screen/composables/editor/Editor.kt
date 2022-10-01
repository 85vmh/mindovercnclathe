package screen.composables.editor

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import com.mindovercnc.model.EmptyTextLines
import com.mindovercnc.model.readTextLines
import screen.composables.util.SingleSelection
import java.io.File

class Editor(
    val file: File,
    val fileName: String,
    val lines: CoroutineScope.() -> Lines,
) {
    var close: (() -> Unit)? = null

    lateinit var selection: SingleSelection

    val isActive: Boolean
        get() = selection.selected === this

    fun activate() {
        selection.selected = this
    }

    class Line(val number: Int, val content: Content)

    interface Lines {
        val lineNumberDigitCount: Int get() = size.toString().length
        val size: Int
        operator fun get(index: Int): Line
    }

    class Content(val value: State<String>, val isGCode: Boolean)
}

fun Editor(file: File) = Editor(
    file = file,
    fileName = file.name
) {
    val textLines = try {
        file.readTextLines(this)
    } catch (e: Throwable) {
        e.printStackTrace()
        EmptyTextLines
    }
    val isGCode = file.extension.endsWith("ngc", ignoreCase = true) || file.extension.endsWith("nc", ignoreCase = true)

    fun content(index: Int): Editor.Content {
        val text = textLines.get(index)
        val state = mutableStateOf(text)
        return Editor.Content(state, isGCode)
    }

    object : Editor.Lines {
        override val size get() = textLines.size

        override fun get(index: Int) = Editor.Line(
            number = index + 1,
            content = content(index)
        )
    }
}
