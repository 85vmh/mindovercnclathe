package com.mindovercnc.database.module

import com.mindovercnc.database.initializer.DatabaseInitializer
import com.mindovercnc.database.initializer.KtCncInitializer
import com.mindovercnc.database.initializer.step.ConnectionInitializer
import com.mindovercnc.database.initializer.step.DummyToolsInitializer
import com.mindovercnc.database.initializer.step.SchemaInitializer
import org.kodein.di.DI
import org.kodein.di.bindSingleton

private const val DB_NAME = "LatheTools.db"

val DatabaseModule = DI.Module("db") {
    bindSingleton<DatabaseInitializer> {
        KtCncInitializer(
            ConnectionInitializer(DB_NAME),
            SchemaInitializer(true),
            DummyToolsInitializer(5)
        )
    }
}