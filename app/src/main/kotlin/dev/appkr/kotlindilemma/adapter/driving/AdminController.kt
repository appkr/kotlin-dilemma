package dev.appkr.kotlindilemma.adapter.driving

import dev.appkr.kotlindilemma.adapter.driving.mapper.AccountMapper
import dev.appkr.kotlindilemma.adapter.driving.resource.AccountResource
import dev.appkr.kotlindilemma.port.inbound.AccountUsecase

class AdminController(
    private val usecase: AccountUsecase,
) {
    fun listAccounts(): Collection<AccountResource> =
        usecase.listAccounts().map { AccountMapper.toResource(it) }
}
