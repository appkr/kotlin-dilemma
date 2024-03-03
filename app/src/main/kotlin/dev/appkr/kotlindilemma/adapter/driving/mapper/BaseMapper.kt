package dev.appkr.kotlindilemma.adapter.driving.mapper

abstract class BaseMapper<M, R> {
    abstract fun toResource(model: M): R

    fun toResource(modelList: Collection<M>) = modelList.map { toResource(it) }
}
