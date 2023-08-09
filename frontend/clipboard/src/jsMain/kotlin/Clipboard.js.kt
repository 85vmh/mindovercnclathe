package clipboard

import kotlinx.browser.window

actual object Clipboard {

    actual fun write(text: String) {
        window.navigator.clipboard.writeText(text)
    }
}