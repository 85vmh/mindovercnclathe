// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the
// Apache 2.0 license that can be found in the LICENSE file.
@file:Suppress("FunctionName")

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ApplicationScope
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

    val componentContext = createComponentContext()
    application {
        val (appConfig, setAppConfig) = remember {
            mutableStateOf(createAppConfig(startupArgs))
        }

        if (appConfig == null) {
            CommunicationPickerWindow(
                startupArgs,
                modifier = Modifier.fillMaxSize(),
                onCommunication = { communication -> setAppConfig(startupArgs.toAppConfig(communication)) },
            )
        } else {
            MainApplication(
                appConfig,
                componentContext
            )
        }
    }
}

@Composable
private fun ApplicationScope.MainApplication(
    appConfig: AppConfig,
    componentContext: ComponentContext
) {
    withAppDi(appConfig) {
        val (initialised, setInitialised) = rememberSaveable { mutableStateOf(false) }
        if (initialised) {
            AppWindow(appConfig, componentContext)
        } else {
            StartupWindow(appConfig) { setInitialised(true) }
        }
    }
}

private fun createAppConfig(startupArgs: StartupArgs): AppConfig? {
    if (startupArgs.legacyCommunication) {
        return startupArgs.toAppConfig(Communication.Local)
    }
    return null
}

private fun StartupArgs.toAppConfig(communication: Communication) =
    AppConfig(
        iniFilePath,
        communication,
        screenSize,
        topBarEnabled.enabled,
        darkMode,
        density
    )

private fun createComponentContext(): ComponentContext {
    val lifecycle = LifecycleRegistry()
    val backDispatcher = BackDispatcher()
    return DefaultComponentContext(lifecycle = lifecycle, backHandler = backDispatcher)
}
