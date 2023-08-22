package com.mindovercnc.database.di

import com.mindovercnc.database.initializer.ConnectionInitializer
import com.mindovercnc.database.initializer.DummyToolsInitializer
import com.mindovercnc.database.initializer.SchemaInitializer
import initializer.Initializer
import initializer.SimpleInitializer
import okio.Path
import org.kodein.di.DI
import org.kodein.di.bindSingleton

private const val DB_NAME = "LatheTools.db"

fun databaseModule(path: Path) = DI.Module("db") {
    bindSingleton<Initializer>("database") {
        SimpleInitializer(
            ConnectionInitializer(path.div(DB_NAME)),
            SchemaInitializer(true),
            DummyToolsInitializer(5)
        )
    }
}