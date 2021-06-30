package com.miranda.ot.grpc.modelo

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Frete (val preco:Double , val frete:String){

    @Id @GeneratedValue
    var id : Long? = null
}