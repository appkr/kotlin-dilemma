package dev.appkr.kotlindilemma.domain.model

enum class AccountState(
    private val description: String,
) {
    NEW("신규"),
    ACTIVATED("활성화됨"),
    DORMANT("휴면"),
    DELETED("탈퇴"),
}
