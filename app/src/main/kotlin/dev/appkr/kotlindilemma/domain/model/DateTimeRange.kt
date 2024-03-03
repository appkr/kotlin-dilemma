package dev.appkr.kotlindilemma.domain.model

import java.time.Instant

data class DateTimeRange(
    val from: Instant,
    val to: Instant,
) {
    init {
        if (from >= to) {
            throw IllegalStateException("to value must be greater than the from value")
        }
    }

    fun isExpired(requestedAt: Instant) = requestedAt !in from..to
}
