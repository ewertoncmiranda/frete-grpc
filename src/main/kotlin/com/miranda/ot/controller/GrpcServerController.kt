package com.miranda.ot.controller

import com.miranda.ot.grpc.client.FretesGrpcServer
import io.micronaut.grpc.server.GrpcEmbeddedServer
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import javax.inject.Inject

@Controller
class GrpcServerController(@Inject val grpcServer: GrpcEmbeddedServer) {

    @Get("/server/stop")
    fun stop():HttpResponse<String>{
        grpcServer.stop()
        return  HttpResponse.ok("A Aplicação esta rodando? ${grpcServer.isRunning}")
    }

    @Get("/server/start")
    fun start():HttpResponse<String>{
        grpcServer.start()
        return  HttpResponse.ok("A Aplicação esta rodando? ${grpcServer.isRunning}")
    }
}