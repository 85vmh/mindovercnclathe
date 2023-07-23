package com.mindovercnc.linuxcnc.di

import com.mindovercnc.linuxcnc.parsing.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val ParseFactoryModule =
    DI.Module("parseFactory") {
        bindSingleton { PositionFactory(instance()) }
        bindSingleton { TaskStatusFactory(instance(), instance()) }
        bindSingleton { TrajectoryStatusFactory(instance(), instance()) }
        bindSingleton { JointStatusFactory(instance()) }
        bindSingleton { SpindleStatusFactory(instance()) }
        bindSingleton {
            MotionStatusFactory(instance(), instance(), instance(), instance(), instance())
        }
        bindSingleton { IoStatusFactory(instance()) }
        bindSingleton { CncStatusFactory(instance(), instance(), instance(), instance()) }
    }

val KtlCncModule = DI.Module("ktlcnc") {

}