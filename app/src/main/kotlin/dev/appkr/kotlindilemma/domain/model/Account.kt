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
    private var _accountNumber: AccountNumber,
    private var _username: Username,
    private var _email: Email,
    private var _password: Password,
    private var _state: AccountState,
    private var _membership: Membership,
    private var _registeredAt: Instant,
) : Account {
    override val accountNumber: AccountNumber
        get() = _accountNumber
    override val username: Username
        get() = _username
    override val email: Email
        get() = _email
    override val password: Password
        get() = _password
    override val state: AccountState
        get() = _state
    override val membership: Membership
        get() = _membership
    override val registeredAt: Instant
        get() = _registeredAt

    override fun changePassword(password: String) {
        this._password = Password(password)
    }

    override fun verifyEmail(email: String) {
        if (this.email.value != email) {
            throw NoSuchElementException("Account not exists")
        }

        this._state = AccountState.ACTIVATED
    }

    override fun subscribe(requestedAt: Instant) {
        this._state = AccountState.ACTIVATED
        this._membership =
            this.membership
                .copy(
                    validThrough =
                        this.membership.validThrough.copy(
                            to = requestedAt.plus(30L, ChronoUnit.DAYS),
                        ),
                )
    }

    override fun deregister() {
        this._state = AccountState.DELETED
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Account) return false
        return this.accountNumber == other.accountNumber
    }
}
