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
                instance(),
                instance(),
                instance(),
                instance(),
                instance(),
                instance(),
                instance(),
                instance(),
                instance()
            )
        }

        bindProvider { TurningSettingsScreenModel(instance()) }

        bindProvider { TaperSettingsScreenModel(instance()) }

        bindProvider { VirtualLimitsScreenModel(instance(), instance()) }

        bindProvider { SimpleCyclesScreenModel(di, componentContext = instance()) }

        bindProvider { ManualRootScreenModel(di, instance()) }
    }
