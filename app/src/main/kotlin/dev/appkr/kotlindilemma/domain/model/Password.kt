package dev.appkr.kotlindilemma.domain.model

interface Password {
    val value: String

    fun matches(passwordToChallenge: String): Boolean {
        return value == passwordToChallenge
    }

    fun validate() {
        val isLongEnough = value.length >= 8
        val hasNumber = value.any { it.isDigit() }
        val hasUpperCase = value.any { it.isUpperCase() }

        val strength = listOf(isLongEnough, hasNumber, hasUpperCase).count { it }
        if (strength < 3) {
            throw IllegalStateException("Stronger password required: 8+ letters, number, and capital letter")
        }
    }
}

data class PasswordImpl(override val value: String) : Password {
    init {
        validate()
    }
}
