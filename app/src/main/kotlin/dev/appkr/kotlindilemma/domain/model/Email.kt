package dev.appkr.kotlindilemma.domain.model

interface Email {
    val value: String

    fun validate() {
        if (!REGEX_EMAIL.matches(value)) {
            throw IllegalStateException("Email address is not valid")
        }
    }

    companion object {
        val REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    }
}

data class EmailImpl(override val value: String) : Email {
    init {
        validate()
    }
}
