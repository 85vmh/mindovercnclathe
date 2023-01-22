// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the
// Apache 2.0 license that can be found in the LICENSE file.
@file:Suppress("FunctionName")

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import app.AppWindow
import mu.KotlinLogging
import okio.FileSystem
import startup.ArgProcessor
import startup.Initializer
import startup.StartupArgs

fun main(args: Array<String>) {
  val logger = KotlinLogging.logger("Main")

  val startupArgs = ArgProcessor(FileSystem.SYSTEM).process(args)

  Initializer(startupArgs)

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
    val windowState = rememberWindowState(width = 1024.dp, height = 768.dp)
    AppWindow(windowState, startupArgs) {
      onExit()
      this.exitApplication()
    }
  }
}
