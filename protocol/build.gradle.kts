import com.google.protobuf.gradle.*

plugins {
    java
    `java-library`

    alias(libs.plugins.protobuf)
}

dependencies {
    implementation(libs.bundles.grpc)
    if (JavaVersion.current().isJava9Compatible) {
        compileOnly(libs.annotationsApi)
    }
}

protobuf {
    // Using the libs here because the versions are locked
    // however, libs can be argued to be wrong, since this is configuration
    // I think it depends on how tight the coupling is, here I judge it to be tight
    protoc {
        artifact = libs.grpc.protoc.binary.get().toString()
    }

    plugins {
        id("grpc") {
            artifact = libs.grpc.protoc.generator.get().toString()
        }
    }

    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc") {}
            }
        }
    }
}