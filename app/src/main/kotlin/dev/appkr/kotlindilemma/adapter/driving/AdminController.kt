package dev.appkr.kotlindilemma.adapter.driving

import dev.appkr.kotlindilemma.adapter.driving.mapper.AccountMapper
import dev.appkr.kotlindilemma.adapter.driving.resource.AccountResource
import dev.appkr.kotlindilemma.port.inbound.AccountUsecase

class AdminController(
    private val usecase: AccountUsecase,
    private val mapper: AccountMapper,
) {
    fun listAccounts(): Collection<AccountResource> {
        return usecase.listAccounts()
            .map { mapper.toResource(it) }
    }
}
