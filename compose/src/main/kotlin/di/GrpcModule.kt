package di

import com.squareup.wire.GrpcClient
import okhttp3.OkHttpClient
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ro.dragossusi.proto.linuxcnc.LinuxCncClient

private val PORT = 50051

val GrpcModule =
    DI.Module("grpc") {
        bindSingleton {
            val grpcClient = GrpcClient.Builder()
                .client(OkHttpClient.Builder().build())
                .baseUrl("localhost:$PORT")
                .build()
            grpcClient.create<LinuxCncClient>()
        }
    }
