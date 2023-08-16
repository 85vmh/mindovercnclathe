package com.mindovercnc.linuxcnc.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.kodein.di.DI
import org.kodein.di.compose.localDI
import org.kodein.di.direct
import org.kodein.di.provider
import org.kodein.di.subDI


@Composable
inline fun <reified T : ScreenModel> Screen.rememberScreenModel(
    di: DI = localDI(),
    tag: Any? = null
): T = rememberScreenModel(tag = tag?.toString()) { di.direct.provider<T>(tag)() }

@Composable
inline fun <reified T : ScreenModel> Screen.rememberScreenModel(
    di: DI = localDI(),
    tag: Any? = null,
    crossinline init: DI.MainBuilder.() -> Unit
): T {
    val subDi = subDI(di, init = init)
    return rememberScreenModel(tag = tag?.toString()) { subDi.direct.provider<T>(tag)() }
}

