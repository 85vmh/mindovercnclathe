import com.google.protobuf.gradle.*

plugins {
  kotlin("jvm")
  id("com.google.protobuf")
}

dependencies {
  protobuf(project(":protos"))

  api("io.grpc:grpc-kotlin-stub:${Versions.Grpc.grpc_kotlin}")
  api("io.grpc:grpc-protobuf:${Versions.Grpc.grpc}")
  api("com.google.protobuf:protobuf-kotlin:${Versions.Grpc.protobuf_kotlin}")
}

protobuf {
  protoc { artifact = "com.google.protobuf:protoc:${Versions.Grpc.protobuf_kotlin}" }

  plugins {
    id("grpc") { artifact = "io.grpc:protoc-gen-grpc-java:${Versions.Grpc.grpc}" }
    id("grpckt") {
      artifact = "io.grpc:protoc-gen-grpc-kotlin:${Versions.Grpc.grpc_kotlin}:jdk8@jar"
    }
  }

  generateProtoTasks {
    all().forEach {
      it.plugins {
        id("grpc")
        id("grpckt")
      }
      it.builtins { id("kotlin") }
    }
  }
}
