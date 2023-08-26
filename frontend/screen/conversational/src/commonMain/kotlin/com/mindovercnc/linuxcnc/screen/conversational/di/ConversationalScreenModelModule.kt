package com.mindovercnc.linuxcnc.screen.conversational.di

import com.mindovercnc.linuxcnc.screen.conversational.ConversationalScreenModel
import org.kodein.di.DI
import org.kodein.di.bindProvider

val ConversationalScreenModelModule =
    DI.Module("conversational_screen_model") { bindProvider { ConversationalScreenModel() } }
