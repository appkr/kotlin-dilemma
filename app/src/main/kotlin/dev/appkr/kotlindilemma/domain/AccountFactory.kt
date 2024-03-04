package dev.appkr.kotlindilemma.domain

import dev.appkr.kotlindilemma.domain.model.AccountImpl
import dev.appkr.kotlindilemma.domain.model.AccountNumber
import dev.appkr.kotlindilemma.domain.model.AccountState
import dev.appkr.kotlindilemma.domain.model.Email
import dev.appkr.kotlindilemma.domain.model.Membership
import dev.appkr.kotlindilemma.domain.model.Password
import dev.appkr.kotlindilemma.domain.model.Username
import java.time.Instant

object AccountFactory {
    fun createFrom(
        username: String,
        email: String,
        password: String,
        requestAt: Instant,
    ) = AccountImpl(
        _accountNumber = AccountNumber.newInstance(),
        _username = Username.of(username),
        _email = Email.of(email),
        _password = Password.of(password),
        _state = AccountState.NEW,
        _membership = Membership.of(requestAt),
        _registeredAt = requestAt,
    )
}
