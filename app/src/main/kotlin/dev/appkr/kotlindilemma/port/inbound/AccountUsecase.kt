package dev.appkr.kotlindilemma.port.inbound

import dev.appkr.kotlindilemma.domain.model.Account
import java.time.Instant

interface AccountUsecase {
    fun registerAccount(
        username: String,
        email: String,
        password: String,
        requestAt: Instant,
    ): Account

    fun verifyEmail(
        username: String,
        email: String,
        requestAt: Instant,
    ): Account

    fun login(
        username: String,
        password: String,
        requestAt: Instant,
    ): Account

    fun changePassword(
        username: String,
        password: String,
        requestAt: Instant,
    ): Account

    fun subscribeMembership(
        username: String,
        requestAt: Instant,
    ): Account

    fun deregisterAccount(username: String): Boolean

    fun listAccounts(): Collection<Account>
}
