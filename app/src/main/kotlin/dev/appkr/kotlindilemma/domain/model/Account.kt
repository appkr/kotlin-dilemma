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

    fun verifyEmail(emailToChallenge: String, requestedAt: Instant): Account

    fun changePassword(newPassword: String, requestedAt: Instant): Account

    fun subscribeMembership(requestedAt: Instant): Account

    fun deregisterAccount(): Account

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
        if (membership.isExpired(requestedAt)) {
            throw IllegalAccessException("Membership expired")
        }
        if (!this.password.matches(password)) {
            throw NoSuchElementException("Incorrect username or password")
        }

        return true
    }
}

data class AccountImpl(
    override val accountNumber: AccountNumber,
    override val username: Username,
    override val email: Email,
    override val password: Password,
    override val state: AccountState,
    override val membership: Membership,
    override val registeredAt: Instant,
) : Account {
    override fun verifyEmail(emailToChallenge: String, requestedAt: Instant): Account {
        if (emailToChallenge != email.value) {
            throw NoSuchElementException("Account not exists")
        }
        if (membership.isExpired(requestedAt)) {
            throw IllegalAccessException("Membership expired")
        }

        return copy(
            state = AccountState.ACTIVATED,
        )
    }

    override fun changePassword(newPassword: String, requestedAt: Instant): Account =
        copy(
            password = PasswordImpl(newPassword),
        )

    override fun subscribeMembership(requestedAt: Instant): Account {
        if (state in listOf(AccountState.DORMANT, AccountState.DELETED)) {
            throw IllegalAccessException("Account is dormant or deleted")
        }

        return copy(
            membership = with(membership) {
                MembershipImpl(
                    grade = grade,
                    validThrough = DateTimeRange(
                        from = validThrough.from,
                        // 30일 멤버쉽 가입을 가정한다
                        to = validThrough.to.plus(30L, ChronoUnit.DAYS),
                    ),
                )
            }
        )
    }

    override fun deregisterAccount(): Account {
        if (state == AccountState.DELETED) {
            throw NoSuchElementException("Account not exists")
        }

        return copy(
            state = AccountState.DELETED,
        )
    }
}
