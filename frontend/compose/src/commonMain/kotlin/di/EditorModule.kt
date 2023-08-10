package di

import com.mindovercnc.editor.EditorLoader
import com.mindovercnc.editor.type.EditorFileTypeHandler
import com.mindovercnc.editor.type.EditorFileTypeHandlerImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val EditorModule = DI.Module("editor") {
    bindSingleton { EditorLoader(instance()) }

    //    bindSingleton<EditorReader> { PathEditorReader(instance()) }
    bindSingleton<EditorFileTypeHandler> { EditorFileTypeHandlerImpl }
}
