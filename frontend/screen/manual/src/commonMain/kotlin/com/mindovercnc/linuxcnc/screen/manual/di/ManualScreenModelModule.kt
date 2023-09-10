package com.mindovercnc.linuxcnc.screen.manual.di

import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootScreenModel
import com.mindovercnc.linuxcnc.screen.manual.simplecycles.SimpleCyclesScreenModel
import com.mindovercnc.linuxcnc.screen.manual.tapersettings.TaperSettingsScreenModel
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningScreenModel
import com.mindovercnc.linuxcnc.screen.manual.turningsettings.TurningSettingsScreenModel
import com.mindovercnc.linuxcnc.screen.manual.virtuallimits.VirtualLimitsScreenModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

@Deprecated("Use constructor instead")
val ManualScreenModelModule =
    DI.Module("manual_screen_model") {
        bindProvider {
            ManualTurningScreenModel(
                di,
                componentContext = instance(),
            )
        }

        bindProvider { TurningSettingsScreenModel(di, componentContext = instance()) }

        bindProvider { TaperSettingsScreenModel(di, componentContext = instance()) }

        bindProvider { VirtualLimitsScreenModel(di, componentContext = instance()) }

        bindProvider { SimpleCyclesScreenModel(di, componentContext = instance()) }

        bindProvider { ManualRootScreenModel(di, componentContext = instance()) }
    }
