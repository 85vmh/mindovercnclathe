package com.mindovercnc.database.module

import com.mindovercnc.database.initializer.ConnectionInitializer
import com.mindovercnc.database.initializer.DummyToolsInitializer
import com.mindovercnc.database.initializer.SchemaInitializer
import initializer.Initializer
import initializer.SimpleInitializer
import org.kodein.di.DI
import org.kodein.di.bindSingleton

private const val DB_NAME = "LatheTools.db"

val DatabaseModule = DI.Module("db") {
    bindSingleton<Initializer> {
        SimpleInitializer(
            ConnectionInitializer(DB_NAME),
            SchemaInitializer(true),
            DummyToolsInitializer(5)
        )
    }
}