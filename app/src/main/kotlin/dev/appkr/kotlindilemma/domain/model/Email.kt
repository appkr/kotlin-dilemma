package dev.appkr.kotlindilemma.domain.model

data class Email(val value: String) {
    init {
        if (!REGEX_EMAIL.matches(value)) {
            throw IllegalStateException("Email address is not valid")
        }
    }

    companion object {
        val REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

        fun of(value: String) = Email(value)
    }
}
