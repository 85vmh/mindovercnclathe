// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the
// Apache 2.0 license that can be found in the LICENSE file.
@file:Suppress("FunctionName")

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.window.application
import app.AppWindow
import mu.KotlinLogging
import okio.FileSystem
import startup.ArgProcessor
import startup.StartupArgs
import startup.StartupWindow

fun main(args: Array<String>) {
    val logger = KotlinLogging.logger("Main")

    val startupArgs = ArgProcessor(FileSystem.SYSTEM).process(args)

    logger.info("Starting app with args $startupArgs")
    startApplication(
        startupArgs,
        onExit = {
            // no-op
        }
    )
}

fun startApplication(startupArgs: StartupArgs, onExit: () -> Unit) {
    application {
        val (initialised, setInitialised) = rememberSaveable {
            mutableStateOf(false)
        }
        if (initialised) {
            AppWindow(startupArgs) {
                onExit()
                this.exitApplication()
            }
        } else {
            StartupWindow(startupArgs, onInitialise = { setInitialised(true) })
        }
    }
}
