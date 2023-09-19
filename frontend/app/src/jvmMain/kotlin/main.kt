// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the
// Apache 2.0 license that can be found in the LICENSE file.
@file:Suppress("FunctionName")

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
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
    val componentContext = createComponentContext()
    application {
        withAppDi(startupArgs) {
            val (initialised, setInitialised) = rememberSaveable { mutableStateOf(false) }
            if (initialised) {
                AppWindow(startupArgs, componentContext) {
                    onExit()
                    this.exitApplication()
                }
            } else {
                StartupWindow(startupArgs) { setInitialised(true) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TestWindow() {
    Window(onCloseRequest = {}) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Badged") },
                    actions = {
                        BadgedBox(
                            badge = {
                                Badge(modifier = Modifier.align(Alignment.BottomStart)) { Text("box", modifier = Modifier.background(Color.Black)) }
                            }
                        ) {
                            IconButton(onClick = {}, modifier = Modifier.background(Color.Gray)) {
                                Icon(Icons.Default.Check, contentDescription = null)
                            }
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Close, contentDescription = null)
                        }
                    }
                )
            }
        ) {
            Text("Badged Box", modifier = Modifier.padding(it))
        }
    }
}

fun createComponentContext(): ComponentContext {
    val lifecycle = LifecycleRegistry()
    val backDispatcher = BackDispatcher()
    return DefaultComponentContext(lifecycle = lifecycle, backHandler = backDispatcher)
}
