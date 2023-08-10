package com.mindovercnc.linuxcnc.settings.local.di

import com.mindovercnc.linuxcnc.settings.SettingsRepository
import com.mindovercnc.linuxcnc.settings.local.SettingsRepositoryLocal
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import java.util.prefs.Preferences

val SettingsLocalModule = DI.Module("settings_local") {
    bindSingleton<SettingsRepository> { SettingsRepositoryLocal(Preferences.userRoot()) }
}