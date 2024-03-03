package dev.appkr.kotlindilemma.adapter.driving

import dev.appkr.kotlindilemma.adapter.driving.mapper.AccountMapper
import dev.appkr.kotlindilemma.adapter.driving.resource.AccountResource
import dev.appkr.kotlindilemma.port.inbound.AccountUsecase
import java.time.Clock

class AccountController(
    private val usecase: AccountUsecase,
    private val mapper: AccountMapper,
    private val clock: Clock,
) {
    fun registerAccount(
        username: String,
        email: String,
        password: String,
    ): AccountResource {
        return usecase.registerAccount(username, email, password, clock.instant())
            .let { mapper.toResource(it) }
    }

    fun verifyEmail(
        username: String,
        email: String,
    ) = usecase.verifyEmail(username, email, clock.instant()) != null

    fun login(
        username: String,
        password: String,
    ) = usecase.login(username, password, clock.instant()) != null

    fun changePassword(
        username: String,
        password: String,
    ) = usecase.changePassword(username, password, clock.instant()) != null

    fun subscribeMembership(username: String) = usecase.subscribeMembership(username, clock.instant()) != null

    fun deregisterAccount(username: String) = usecase.deregisterAccount(username)
}
