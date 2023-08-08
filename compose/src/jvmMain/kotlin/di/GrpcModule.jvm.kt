package di

import com.squareup.wire.GrpcClient
import okhttp3.OkHttpClient
import okhttp3.Protocol
import org.kodein.di.DI
import org.kodein.di.bindProvider
import ro.dragossusi.proto.linuxcnc.LinuxCncClient


actual val GrpcModule =
    DI.Module("grpc") {
        bindProvider<LinuxCncClient> {
            val client = OkHttpClient.Builder()
                .protocols(listOf(Protocol.H2_PRIOR_KNOWLEDGE))
                .build()
            val grpcClient = GrpcClient.Builder()
                .client(client)
                .baseUrl("http://0.0.0.0:$PORT")
                .build()
            grpcClient.create<LinuxCncClient>()
        }
    }
