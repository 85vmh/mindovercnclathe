// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
@file:Suppress("FunctionName")

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import app.AppWindow
import startup.ArgProcessor
import startup.Initializer
import startup.StartupArgs


fun main(args: Array<String>) {

    val startupArgs = ArgProcessor.process(args)

//    val process = LinuxCncStarter(startupArgs.iniFilePath)
//    Thread.sleep(1000L)

    Initializer(startupArgs)

    startApplication(startupArgs) {
//        process.destroy()
    }
}

fun startApplication(
    startupArgs: StartupArgs,
    onExit: () -> Unit
) {
    application {
        val windowState = rememberWindowState(width = 1024.dp, height = 768.dp)
        AppWindow(
            windowState,
            startupArgs
        ) {
            onExit()
            this.exitApplication()
        }
    }
}