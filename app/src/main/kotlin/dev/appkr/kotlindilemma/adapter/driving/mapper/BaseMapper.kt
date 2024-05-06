package dev.appkr.kotlindilemma.adapter.driving.mapper

abstract class BaseMapper<DomainModel, ApiResource> {
    abstract fun toResource(model: DomainModel): ApiResource

    fun toResource(modelList: Collection<DomainModel>) =
        modelList.map { toResource(it) }
}
