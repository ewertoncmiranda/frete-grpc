package com.miranda.ot
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions

import org.junit.jupiter.api.Test
import javax.inject.Inject
import javax.inject.Singleton


@MicronautTest
internal class  FretesTest (val  grpc: FretesServiceGrpc.FretesServiceBlockingStub){

    @Test
    fun testItWorks() {
        val response = grpc.calculaFrete( CalculaFreteRequest
                                                .newBuilder()
                                                .setCep("13603-187").build())
    }

@Factory
class Clients{

    @Singleton
    fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME ) channel:
                         ManagedChannel):FretesServiceGrpc.FretesServiceBlockingStub? {
    return  FretesServiceGrpc.newBlockingStub(channel);
    }
}

}
