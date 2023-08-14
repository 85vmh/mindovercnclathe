package com.mindovercnc.linuxcnc.di

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ro.dragossusi.proto.linuxcnc.LinuxCncClient


actual val GrpcModule =
    DI.Module("grpc") {
        bindSingleton<LinuxCncClient> {
            TODO("Grpc client not supported on js")
        }
    }