package dev.appkr.kotlindilemma.domain.model

enum class MembershipGrade(
    private val description: String,
) {
    REGULAR("일반"),
    SILVER("실버"),
    GOLD("골드"),
    PREMIUM("프리미엄"),
}
