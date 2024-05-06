package dev.appkr.kotlindilemma.domain

import dev.appkr.kotlindilemma.domain.model.AccountImpl
import dev.appkr.kotlindilemma.domain.model.AccountNumberImpl
import dev.appkr.kotlindilemma.domain.model.AccountState
import dev.appkr.kotlindilemma.domain.model.DateTimeRange
import dev.appkr.kotlindilemma.domain.model.EmailImpl
import dev.appkr.kotlindilemma.domain.model.Membership.Companion.FREE_TRIAL_DAYS
import dev.appkr.kotlindilemma.domain.model.MembershipGrade
import dev.appkr.kotlindilemma.domain.model.MembershipImpl
import dev.appkr.kotlindilemma.domain.model.PasswordImpl
import dev.appkr.kotlindilemma.domain.model.UsernameImpl
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

object AccountFactory {
    fun createFrom(
        username: String,
        email: String,
        password: String,
        requestAt: Instant,
    ) =
        AccountImpl(
            accountNumber = AccountNumberImpl(UUID.randomUUID()),
            username = UsernameImpl(username),
            email = EmailImpl(email),
            password = PasswordImpl(password),
            state = AccountState.NEW,
            membership = MembershipImpl(
                grade = MembershipGrade.REGULAR,
                validThrough =
                DateTimeRange(
                    from = requestAt,
                    to = requestAt.plus(FREE_TRIAL_DAYS, ChronoUnit.DAYS),
                ),
            ),
            registeredAt = requestAt,
        )
}
