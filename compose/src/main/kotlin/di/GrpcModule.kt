package di

import io.grpc.ManagedChannelBuilder
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ro.dragossusi.proto.linuxcnc.LinuxCncGrpc

val GrpcModule =
  DI.Module("grpc") {
    bindSingleton {
      val localhost =
        ManagedChannelBuilder.forAddress(/* name = */ "localhost", /* port = */ 50051)
          .usePlaintext()
          .build()
      LinuxCncGrpc.newBlockingStub(localhost)
    }
  }
