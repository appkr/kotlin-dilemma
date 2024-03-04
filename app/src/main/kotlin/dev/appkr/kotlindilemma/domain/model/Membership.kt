package dev.appkr.kotlindilemma.domain.model

import java.time.Instant
import java.time.temporal.ChronoUnit

data class Membership(
    val grade: MembershipGrade,
    val validThrough: DateTimeRange,
) {
    fun isExpired(requestedAt: Instant) = validThrough.isExpired(requestedAt)

    companion object {
        private const val FREE_TRIAL_DAYS = 30L

        fun of(from: Instant): Membership =
            Membership(
                grade = MembershipGrade.REGULAR,
                validThrough =
                    DateTimeRange(
                        from = from,
                        to = from.plus(FREE_TRIAL_DAYS, ChronoUnit.DAYS),
                    ),
            )
    }
}
