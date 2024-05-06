package dev.appkr.kotlindilemma.adapter.driving

import dev.appkr.kotlindilemma.adapter.driving.mapper.AccountMapper
import dev.appkr.kotlindilemma.adapter.driving.resource.AccountResource
import dev.appkr.kotlindilemma.port.inbound.AccountUsecase
import java.time.Clock

class AccountController(
    private val usecase: AccountUsecase,
    private val clock: Clock,
) {
    fun registerAccount(
        username: String,
        email: String,
        password: String,
    ): AccountResource =
        usecase.registerAccount(username, email, password, clock.instant())
            .let { AccountMapper.toResource(it) }

    fun verifyEmail(
        username: String,
        email: String,
    ): Boolean =
        usecase.verifyEmail(username, email, clock.instant()) != null

    fun login(
        username: String,
        password: String,
    ): Boolean =
        usecase.login(username, password, clock.instant()) != null

    fun changePassword(
        username: String,
        password: String,
    ): Boolean =
        usecase.changePassword(username, password, clock.instant()) != null

    fun subscribeMembership(username: String): Boolean =
        usecase.subscribeMembership(username, clock.instant()) != null

    fun deregisterAccount(username: String): Boolean =
        usecase.deregisterAccount(username)
}
