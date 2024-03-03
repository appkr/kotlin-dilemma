package dev.appkr.kotlindilemma.domain.model

import java.time.Instant
import java.time.temporal.ChronoUnit

interface Membership {
    val grade: MembershipGrade
    val validThrough: DateTimeRange

    fun isExpired(requestedAt: Instant) = validThrough.isExpired(requestedAt)

    companion object {
        private const val FREE_TRIAL_DAYS = 30L

        // ANTI-PATTERN: parent -> child dependency
        fun of(from: Instant): Membership =
            MembershipImpl(
                grade = MembershipGrade.REGULAR,
                validThrough =
                    DateTimeRange(
                        from = from,
                        to = from.plus(FREE_TRIAL_DAYS, ChronoUnit.DAYS),
                    ),
            )
    }
}

data class MembershipImpl(
    override val grade: MembershipGrade,
    override val validThrough: DateTimeRange,
) : Membership
