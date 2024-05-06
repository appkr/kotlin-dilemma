package dev.appkr.kotlindilemma.port

import dev.appkr.kotlindilemma.domain.AccountFactory
import dev.appkr.kotlindilemma.domain.model.Account
import dev.appkr.kotlindilemma.port.inbound.AccountUsecase
import dev.appkr.kotlindilemma.port.outbound.BaseRepository
import java.time.Instant

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
        val account = repository.findBy(username)
            ?: throw NoSuchElementException("Account not exists")

        val updated = account.verifyEmail(email, requestAt)

        return repository.save(updated)
    }

    override fun login(
        username: String,
        password: String,
        requestAt: Instant,
    ): Account {
        val account = repository.findBy(username)
            ?: throw NoSuchElementException("Incorrect username or password")

        account.attemptsLogin(username, password, requestAt)

        return account
    }

    override fun changePassword(
        username: String,
        password: String,
        requestAt: Instant,
    ): Account {
        val account = repository.findBy(username)
            ?: throw NoSuchElementException("Account not exists")

        val updated = account.changePassword(password, requestAt)

        return repository.save(updated)
    }

    override fun subscribeMembership(
        username: String,
        requestAt: Instant,
    ): Account {
        val account = repository.findBy(username)
            ?: throw NoSuchElementException("Account not exists")

        val updated = account.subscribeMembership(requestAt)

        return repository.save(updated)
    }

    override fun deregisterAccount(username: String): Boolean {
        val account = repository.findBy(username)
            ?: throw NoSuchElementException("Account not exists")

        val updated = account.deregisterAccount()

        return repository.save(updated) != null
    }

    override fun listAccounts(): Collection<Account> {
        return repository.findAll()
    }
}
