package dev.appkr.kotlindilemma.adapter.driven

import dev.appkr.kotlindilemma.domain.model.Account
import dev.appkr.kotlindilemma.domain.model.AccountState
import dev.appkr.kotlindilemma.port.outbound.BaseRepository
import java.util.concurrent.ConcurrentHashMap

class AccountInMemoryRepository : BaseRepository<Account, String> {
    private val store: ConcurrentHashMap<String, Account> = ConcurrentHashMap()

    override fun save(model: Account): Account {
        if (!store.contains(model.username.value)) {
            store.put(model.username.value, model)
        }

        return model
    }

    override fun findAll(): Collection<Account> {
        return store.values
    }

    override fun findBy(username: String): Account? {
        return store.get(username)
            ?.takeIf { it.state != AccountState.DELETED }
    }

    override fun deleteBy(username: String): Boolean {
        return if (store.contains(username)) {
            store.remove(username) != null
        } else {
            false
        }
    }
}
