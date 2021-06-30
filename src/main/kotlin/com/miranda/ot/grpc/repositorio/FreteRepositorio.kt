package com.miranda.ot.grpc.repositorio

import com.miranda.ot.grpc.modelo.Frete
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface FreteRepositorio:CrudRepository<Frete,Long> {
}