package dev.appkr.kotlindilemma.domain.model

data class Username(val value: String) {
    init {
        if (value.length < 5) {
            throw IllegalStateException("Username too short")
        }
    }

    companion object {
        fun of(value: String) = Username(value)
    }
}
