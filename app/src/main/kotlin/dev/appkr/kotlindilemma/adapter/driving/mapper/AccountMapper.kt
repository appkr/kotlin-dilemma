package dev.appkr.kotlindilemma.adapter.driving.mapper

import dev.appkr.kotlindilemma.adapter.driving.resource.AccountResource
import dev.appkr.kotlindilemma.domain.model.Account
import dev.appkr.kotlindilemma.domain.model.DateTimeRange
import java.time.ZoneOffset

object AccountMapper : BaseMapper<Account, AccountResource>() {
    private val ZONE_OFFSET = ZoneOffset.of("+09:00")

    override fun toResource(model: Account) =
        with(model) {
            AccountResource(
                accountNumber = accountNumber.value,
                username = username.value,
                email = email.value,
                password = mask(password.value),
                state = state.name,
                membership = "${membership.grade.name}(${format(membership.validThrough)})",
                registeredAt = registeredAt.atOffset(ZONE_OFFSET),
            )
        }

    private fun mask(source: String): String {
        if (source.isBlank()) return source
        val halfIndex = source.length / 2
        return source.substring(0, halfIndex) + "*".repeat(source.length - halfIndex)
    }

    private fun format(dateTimeRange: DateTimeRange): String =
        "${dateTimeRange.from.atOffset(ZONE_OFFSET)}~${dateTimeRange.to.atOffset(ZONE_OFFSET)}"
}
