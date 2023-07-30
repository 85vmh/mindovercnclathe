package com.mindovercnc.linuxcnc.local

import com.mindovercnc.repository.SettingsRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import java.util.prefs.Preferences

val LocalRepositoryModule = DI.Module("local_repository"){
    bindSingleton { Preferences.userRoot() }
    bindSingleton<SettingsRepository> { SettingsRepositoryImpl(instance()) }
}