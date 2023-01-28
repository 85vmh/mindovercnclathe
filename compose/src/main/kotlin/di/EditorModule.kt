package di

import com.mindovercnc.editor.EditorLoader
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val EditorModule = DI.Module("editor") { bindSingleton { EditorLoader(instance(), instance()) } }
