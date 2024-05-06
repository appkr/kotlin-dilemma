package dev.appkr.kotlindilemma.domain.model

import java.time.Instant

interface Membership {
    val grade: MembershipGrade
    val validThrough: DateTimeRange

    fun isExpired(requestedAt: Instant) = validThrough.isExpired(requestedAt)

    companion object {
        internal const val FREE_TRIAL_DAYS = 30L
    }
}

data class MembershipImpl(
    override val grade: MembershipGrade,
    override val validThrough: DateTimeRange,
) : Membership
