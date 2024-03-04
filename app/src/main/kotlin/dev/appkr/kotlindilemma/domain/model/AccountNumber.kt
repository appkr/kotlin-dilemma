package dev.appkr.kotlindilemma.domain.model

import java.util.UUID

data class AccountNumber(val value: UUID) {
    companion object {
        fun newInstance() = AccountNumber(UUID.randomUUID())
    }
}
