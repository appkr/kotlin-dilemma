package dev.appkr.kotlindilemma.domain.model

data class Password(val value: String) {
    init {
        val isLongEnough = value.length >= 8
        val hasNumber = value.any { it.isDigit() }
        val hasUpperCase = value.any { it.isUpperCase() }

        val strength = listOf(isLongEnough, hasNumber, hasUpperCase).count { it }
        if (strength < 3) {
            throw IllegalStateException("Stronger password required: 8+ letters, number, and capital letter")
        }
    }

    fun matches(passwordToChallenge: String): Boolean {
        return value == passwordToChallenge
    }

    companion object {
        fun of(value: String) = Password(value)
    }
}
