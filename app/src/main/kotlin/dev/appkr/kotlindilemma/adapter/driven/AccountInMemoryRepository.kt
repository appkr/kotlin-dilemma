package dev.appkr.kotlindilemma.adapter.driven

import dev.appkr.kotlindilemma.domain.model.Account
import dev.appkr.kotlindilemma.domain.model.AccountState
import dev.appkr.kotlindilemma.port.outbound.BaseRepository
import java.util.concurrent.ConcurrentHashMap

class AccountInMemoryRepository : BaseRepository<Account, String> {
    private val store: ConcurrentHashMap<String, Account> = ConcurrentHashMap()

    override fun save(model: Account): Account {
        if (!store.contains(model.username.value)) {
            store[model.username.value] = model
        }

        return model
    }

    override fun findAll(): Collection<Account> =
        store.values.filter { it.state != AccountState.DELETED }

    override fun findBy(id: String): Account? =
        store[id]?.takeIf { it.state != AccountState.DELETED }

    override fun deleteBy(id: String): Boolean =
        if (store.contains(id)) {
            store.remove(id) != null
        } else {
            false
        }
}
