package di

import com.mindovercnc.editor.EditorLoader
import com.mindovercnc.editor.reader.EditorReader
import com.mindovercnc.editor.reader.FileEditorReader
import com.mindovercnc.editor.type.EditorFileTypeHandler
import com.mindovercnc.editor.type.EditorFileTypeHandlerImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val EditorModule = DI.Module("editor") {
    bindSingleton { EditorLoader(instance()) }

    //TODO change based on platform
    bindSingleton<EditorReader> { FileEditorReader }
    //    bindSingleton<EditorReader> { PathEditorReader(instance()) }
    bindSingleton<EditorFileTypeHandler> { EditorFileTypeHandlerImpl }
}
