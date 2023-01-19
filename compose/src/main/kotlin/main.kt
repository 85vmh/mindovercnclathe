// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the
// Apache 2.0 license that can be found in the LICENSE file.
@file:Suppress("FunctionName")

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import app.AppWindow
import mu.KotlinLogging
import okio.FileSystem
import ro.dragossusi.proto.linuxcnc.status.IoStatus
import startup.ArgProcessor
import startup.Initializer
import startup.StartupArgs

private val LOG = KotlinLogging.logger("Main")

fun main(args: Array<String>) {
  val ioStatus = IoStatus.newBuilder().setFault(3).build()

  LOG.info("Created IoStatus")
  LOG.info(ioStatus.toString())

  val startupArgs = ArgProcessor(FileSystem.SYSTEM).process(args)

  Initializer(startupArgs)

  startApplication(
    startupArgs,
    onExit = {
      // no-op
    }
  )
}

fun startApplication(startupArgs: StartupArgs, onExit: () -> Unit) {
  LOG.info("Starting app with args $startupArgs")
  application {
    val windowState = rememberWindowState(width = 1024.dp, height = 768.dp)
    AppWindow(windowState, startupArgs) {
      onExit()
      this.exitApplication()
    }
  }
}
