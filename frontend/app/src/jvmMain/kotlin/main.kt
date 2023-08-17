// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the
// Apache 2.0 license that can be found in the LICENSE file.
@file:Suppress("FunctionName")

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.window.application
import di.withAppDi
import mu.KotlinLogging
import okio.FileSystem
import startup.StartupWindow
import startup.args.ArgProcessor
import startup.args.StartupArgs

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
        withAppDi(startupArgs) {
            val (initialised, setInitialised) = rememberSaveable { mutableStateOf(false) }
            if (initialised) {
                AppWindow(startupArgs) {
                    onExit()
                    this.exitApplication()
                }
            } else {
                StartupWindow { setInitialised(true) }
            }
        }
    }
}
