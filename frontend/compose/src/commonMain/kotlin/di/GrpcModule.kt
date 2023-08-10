package di

import org.kodein.di.DI

internal val PORT = 50051

expect val GrpcModule: DI.Module
