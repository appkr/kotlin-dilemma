package dev.appkr.kotlindilemma.domain.model

interface Username {
    val value: String

    fun validate() {
        if (value.length < 5) {
            throw IllegalStateException("Username too short")
        }
    }

    companion object {
        // ANTI-PATTERN: parent -> child dependency
        fun of(value: String): Username = UsernameImpl(value)
    }
}

data class UsernameImpl(override val value: String) : Username {
    init {
        validate()
    }
}
