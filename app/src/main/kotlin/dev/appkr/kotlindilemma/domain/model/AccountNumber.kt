package dev.appkr.kotlindilemma.domain.model

import java.util.UUID

interface AccountNumber {
    val value: UUID
}

data class AccountNumberImpl(override val value: UUID) : AccountNumber
