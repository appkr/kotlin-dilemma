package dev.appkr.kotlindilemma.adapter.driving.resource

import java.time.OffsetDateTime
import java.util.UUID

data class AccountResource(
    val accountNumber: UUID,
    val username: String,
    val email: String,
    val password: String,
    val state: String,
    val membership: String,
    val registeredAt: OffsetDateTime,
)
