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
        accountNumber = AccountNumber.newInstance(),
        username = Username.of(username),
        email = Email.of(email),
        password = Password.of(password),
        state = AccountState.NEW,
        membership = Membership.of(requestAt),
        registeredAt = requestAt,
    )
}
