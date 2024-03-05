package dev.appkr.kotlindilemma.port

import dev.appkr.kotlindilemma.domain.AccountFactory
import dev.appkr.kotlindilemma.domain.model.Account
import dev.appkr.kotlindilemma.domain.model.AccountImpl
import dev.appkr.kotlindilemma.domain.model.AccountState
import dev.appkr.kotlindilemma.domain.model.DateTimeRange
import dev.appkr.kotlindilemma.domain.model.MembershipImpl
import dev.appkr.kotlindilemma.domain.model.PasswordImpl
import dev.appkr.kotlindilemma.port.inbound.AccountUsecase
import dev.appkr.kotlindilemma.port.outbound.BaseRepository
import java.time.Instant
import java.time.temporal.ChronoUnit

class AccountService(
    private val repository: BaseRepository<Account, String>,
) : AccountUsecase {
    override fun registerAccount(
        username: String,
        email: String,
        password: String,
        requestAt: Instant,
    ): Account {
        repository.findBy(username)
            ?.let { throw IllegalArgumentException("Username already taken!!") }

        val account =
            AccountFactory.createFrom(
                username = username,
                email = email,
                password = password,
                requestAt = requestAt,
            )

        return repository.save(account)
    }

    override fun verifyEmail(
        username: String,
        email: String,
        requestAt: Instant,
    ): Account {
        val account = repository.findBy(username) ?: throw NoSuchElementException("Account not exists")

        // ANTI-PATTERN: Violates Tell Don't Ask
        // TODO: encapsulate as Account#activate()
        if (email != account.email.value) {
            throw NoSuchElementException("Account not exists")
        }
        if (account.isMembershipExpired(requestAt)) {
            throw IllegalAccessException("Membership expired")
        }

        // Boilerplate
        val updated = AccountImpl(
            accountNumber = account.accountNumber,
            username = account.username,
            email = account.email,
            password = account.password,
            state = AccountState.ACTIVATED,
            membership = account.membership,
            registeredAt = account.registeredAt,
        )

        return repository.save(updated)
    }

    override fun login(
        username: String,
        password: String,
        requestAt: Instant,
    ): Account {
        val account = repository.findBy(username) ?: throw NoSuchElementException("Incorrect username or password")

        account.attemptsLogin(username, password, requestAt)

        return account
    }

    override fun changePassword(
        username: String,
        password: String,
        requestAt: Instant,
    ): Account {
        val account = repository.findBy(username) ?: throw NoSuchElementException("Account not exists")

        // Boilerplate
        val updated = AccountImpl(
            accountNumber = account.accountNumber,
            username = account.username,
            email = account.email,
            password = PasswordImpl(password),
            state = account.state,
            membership = account.membership,
            registeredAt = account.registeredAt,
        )

        return repository.save(updated)
    }

    override fun subscribeMembership(
        username: String,
        requestAt: Instant,
    ): Account {
        val account = repository.findBy(username) ?: throw NoSuchElementException("Account not exists")

        // ANTI-PATTERN: Violates Tell Don't Ask
        // TODO: encapsulate as Account#subscribe()
        if (account.state in listOf(AccountState.DORMANT, AccountState.DELETED)) {
            throw IllegalAccessException("Account is dormant or deleted")
        }

        // ANTI-PATTERN: Sub Casting; Violates Law of Demeter
        val updatedMembership =
            (account.membership as MembershipImpl).copy(
                validThrough =
                    DateTimeRange(
                        from = account.membership.validThrough.from,
                        to = account.membership.validThrough.to.plus(30L, ChronoUnit.DAYS),
                    ),
            )
        // Boilerplate
        val updatedAccount = AccountImpl(
            accountNumber = account.accountNumber,
            username = account.username,
            email = account.email,
            password = account.password,
            state = AccountState.ACTIVATED,
            membership = updatedMembership,
            registeredAt = account.registeredAt,
        )

        return repository.save(updatedAccount)
    }

    override fun deregisterAccount(username: String): Boolean {
        val account = repository.findBy(username) ?: throw NoSuchElementException("Account not exists")

        // ANTI-PATTERN: Violates Tell Don't Ask
        // TODO: encapsulate as Account#deregister()
        if (account.state == AccountState.DELETED) {
            throw NoSuchElementException("Account not exists")
        }

        // Boilerplate
        val updated = AccountImpl(
            accountNumber = account.accountNumber,
            username = account.username,
            email = account.email,
            password = account.password,
            state = AccountState.DELETED,
            membership = account.membership,
            registeredAt = account.registeredAt,
        )

        return repository.save(updated) != null
    }

    override fun listAccounts(): Collection<Account> {
        return repository.findAll()
    }
}
