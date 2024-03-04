package dev.appkr.kotlindilemma.domain.model

import java.time.Instant
import java.time.temporal.ChronoUnit

interface Account {
    val accountNumber: AccountNumber
    val username: Username
    val email: Email
    val password: Password
    val state: AccountState
    val membership: Membership
    val registeredAt: Instant

    fun attemptsLogin(
        username: String,
        password: String,
        requestedAt: Instant,
    ): Boolean {
        if (state != AccountState.ACTIVATED) {
            throw IllegalAccessException("Account not active")
        }
        if (membership.isExpired(requestedAt)) {
            throw IllegalAccessException("Membership expired")
        }
        if (!this.password.matches(password)) {
            throw NoSuchElementException("Incorrect username or password")
        }

        return true
    }

    fun changePassword(password: String)

    fun verifyEmail(email: String)

    fun subscribe(requestedAt: Instant)

    fun deregister()
}

class AccountImpl(
    override var accountNumber: AccountNumber,
    override var username: Username,
    override var email: Email,
    override var password: Password,
    override var state: AccountState,
    override var membership: Membership,
    override var registeredAt: Instant,
) : Account {
    override fun changePassword(password: String) {
        this.password = Password(password)
    }

    override fun verifyEmail(email: String) {
        if (this.email.value != email) {
            throw NoSuchElementException("Account not exists")
        }

        this.state = AccountState.ACTIVATED
    }

    override fun subscribe(requestedAt: Instant) {
        this.state = AccountState.ACTIVATED
        this.membership =
            this.membership
                .copy(
                    validThrough =
                        this.membership.validThrough.copy(
                            to = requestedAt.plus(30L, ChronoUnit.DAYS),
                        ),
                )
    }

    override fun deregister() {
        this.state = AccountState.DELETED
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Account) return false
        return this.accountNumber == other.accountNumber
    }
}
