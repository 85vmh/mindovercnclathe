package com.mindovercnc.database.initializer

import initializer.InitializerStep
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.Connection

internal class ConnectionInitializer(
    private val databaseName: String
) : InitializerStep {
    override suspend fun initialise() {
        Database.connect(url = "jdbc:sqlite:${databaseName}", driver = "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    }
}