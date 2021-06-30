package com.miranda.ot.validacao

import com.miranda.ot.ValorFreteResponse
import io.grpc.Status
import io.grpc.stub.StreamObserver

class ValidaCep() {

    fun validaCep(cep:String ,responseObserver: StreamObserver<ValorFreteResponse>?):Boolean  {
    if (cep!!.isNullOrEmpty() || cep.isNullOrBlank() || !cep.matches("[0-9]{5}-[0-9]{3}".toRegex())) {
        val error = Status.INVALID_ARGUMENT
            .withDescription("CEP não pode ser vazio OR  Formato deve ser CEP 99999-999")
            .asRuntimeException()
        responseObserver!!.onError(error)
        return false
    }  else return true

    }
}