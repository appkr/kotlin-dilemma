package dev.appkr.kotlindilemma.domain.model

import java.util.UUID

interface AccountNumber {
    val value: UUID

    companion object {
        // ANTI-PATTERN: parent -> child dependency
        fun newInstance() = AccountNumberImpl(UUID.randomUUID())
    }
}

data class AccountNumberImpl(override val value: UUID) : AccountNumber
