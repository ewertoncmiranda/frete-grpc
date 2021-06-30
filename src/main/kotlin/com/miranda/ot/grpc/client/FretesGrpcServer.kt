package com.miranda.ot.grpc.client


import com.google.protobuf.Any
import com.google.rpc.Code
import com.miranda.ot.CalculaFreteRequest
import com.miranda.ot.ErroDeMensagem
import com.miranda.ot.FretesServiceGrpc
import com.miranda.ot.ValorFreteResponse
import com.miranda.ot.grpc.modelo.Frete
import com.miranda.ot.grpc.repositorio.FreteRepositorio

import com.miranda.ot.validacao.ValidaCep
import io.grpc.Status
import io.grpc.protobuf.StatusProto
import io.grpc.stub.StreamObserver
import io.micronaut.http.exceptions.HttpException
import org.slf4j.LoggerFactory
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class FretesGrpcServer(@Inject val freteRepositorio: FreteRepositorio) : FretesServiceGrpc.FretesServiceImplBase(){

    private val logger = LoggerFactory.getLogger(FretesGrpcServer::class.java)

    override fun calculaFrete(request: CalculaFreteRequest?, responseObserver: StreamObserver<ValorFreteResponse>?) {

        if (request == null) {
          return Status.DATA_LOSS.withDescription("Requisição com valor nulo")
                                 .asRuntimeException().let {
                                      responseObserver!!.onError(it)
                                  }
        }

        try {
            if(ValidaCep().validaCep(request.cep,responseObserver)){
                logger.info("CEP VALIDADO")
            }

               if(request.cep.endsWith("333")){
                  com.google.rpc.Status.newBuilder()
                                   .setCode(Code.PERMISSION_DENIED.number)
                                   .setMessage("Sem Permissão para acessar recurso!")
                                   .addDetails(Any.pack(ErroDeMensagem.newBuilder()
                                                        .setMensagem2("Erro Besta")
                                                        .setMensagem("Certifique-Me").build() ))
                                   .build()
                                   .let { StatusProto.toStatusRuntimeException(it)}
                                   .let{responseObserver!!.onError(it)}.let { return }

                  }else if(request.cep.endsWith("222")){
                      throw IllegalStateException("Erro lançado pelo 222")
                  }

        }catch (e:IllegalArgumentException){
           Status.INTERNAL.withCause(e)
               .withDescription(e.message)
               .asRuntimeException()
               .let { responseObserver!!.onError(it) }
               .let { return }
        }

        val response = ValorFreteResponse
                            .newBuilder()
                            .setCep(request.cep)
                            .setValor(Random.nextDouble(from = 0.0 ,until = 140.00))
                            .build()

        val frete:Frete =  Frete(response.valor,response.cep)
        freteRepositorio.save(frete)

        responseObserver!!.onNext(response)
        responseObserver.onCompleted()

    }
}

