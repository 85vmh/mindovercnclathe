package clipboard

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

actual object Clipboard {

    private val clipboard = Toolkit.getDefaultToolkit().systemClipboard

    actual fun write(text: String) {
        clipboard.setContents(StringSelection(text), null)
    }
}