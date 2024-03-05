package dev.appkr.kotlindilemma.domain.model

import java.time.Instant

interface Account {
    val accountNumber: AccountNumber
    val username: Username
    val email: Email
    val password: Password
    val state: AccountState
    val membership: Membership
    val registeredAt: Instant

    fun isMembershipExpired(requestedAt: Instant) = membership.isExpired(requestedAt)

    fun attemptsLogin(
        username: String,
        password: String,
        requestedAt: Instant,
    ): Boolean {
        if (state == AccountState.DELETED) {
            throw NoSuchElementException("Account not exists")
        }
        if (state != AccountState.ACTIVATED) {
            throw IllegalAccessException("Account not active")
        }
        if (isMembershipExpired(requestedAt)) {
            throw IllegalAccessException("Membership expired")
        }
        if (!this.password.matches(password)) {
            throw NoSuchElementException("Incorrect username or password")
        }

        return true
    }
}

class AccountImpl(
    override val accountNumber: AccountNumber,
    override val username: Username,
    override val email: Email,
    override val password: Password,
    override val state: AccountState,
    override val membership: Membership,
    override val registeredAt: Instant,
) : Account
