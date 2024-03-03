package dev.appkr.kotlindilemma.port.outbound

interface BaseRepository<T, ID> {
    fun save(model: T): T

    fun findAll(): Collection<T>

    fun findBy(id: ID): T?

    fun deleteBy(id: ID): Boolean
}
