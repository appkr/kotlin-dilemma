package dev.appkr.kotlindilemma

import de.m3y.kformat.Table
import de.m3y.kformat.table
import dev.appkr.kotlindilemma.adapter.driven.AccountInMemoryRepository
import dev.appkr.kotlindilemma.adapter.driving.AccountController
import dev.appkr.kotlindilemma.adapter.driving.AdminController
import dev.appkr.kotlindilemma.adapter.driving.mapper.AccountMapper
import dev.appkr.kotlindilemma.adapter.driving.resource.AccountResource
import dev.appkr.kotlindilemma.port.AccountService
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.Clock
import java.time.ZoneId

fun main() {
    val commands = listOf("register", "verifyEmail", "login", "changePassword", "subscribeMembership", "deregister", "list", "exit")

    // Simulate Singleton Instance
    val usecase = AccountService(AccountInMemoryRepository())
    val mapper = AccountMapper()
    val clock = Clock.system(ZoneId.of("Asia/Seoul"))
    val accountController = AccountController(usecase, mapper, clock)
    val adminController = AdminController(usecase, mapper)

    BufferedReader(InputStreamReader(System.`in`)).use {
        while (true) {
            println("Type one out of: $commands")

            val command = it.readLine()
            if (command.isBlank() || command !in commands) {
                continue
            }

            if (command.startsWith("exit")) {
                break
            }

            try {
                when (command) {
                    "register" -> {
                        val response =
                            accountController.registerAccount(
                                username = ask("username", it),
                                email = ask("email", it),
                                password = ask("password", it),
                            )

                        println(if (response != null) "Success" else "Fail")
                    }

                    "verifyEmail" -> {
                        val response =
                            accountController.verifyEmail(
                                username = ask("username", it),
                                email = ask("email", it),
                            )

                        println(if (response) "Success" else "Fail")
                    }

                    "login" -> {
                        val username = ask("username", it)
                        val password = ask("password", it)
                        val response = accountController.login(username, password)

                        println(if (response) "Success" else "Fail")
                    }

                    "changePassword" -> {
                        val username = ask("username", it)
                        val password = ask("password", it)
                        val response = accountController.changePassword(username, password)

                        println(if (response) "Success" else "Fail")
                    }

                    "subscribeMembership" -> {
                        val username = ask("username", it)
                        val response = accountController.subscribeMembership(username)

                        println(if (response) "Success" else "Fail")
                    }

                    "deregister" -> {
                        val username = ask("username", it)
                        val response = accountController.deregisterAccount(username)

                        println(if (response) "Success" else "Fail")
                    }

                    "list" -> {
                        val response = adminController.listAccounts()

                        println(format(response))
                    }

                    "else" -> throw IllegalStateException("oo)99")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
                continue
            }
        }
    }
}

fun ask(
    question: String,
    reader: BufferedReader,
): String {
    while (true) {
        println("$question?")

        val answer = reader.readLine()
        if (answer.isBlank()) {
            continue
        }

        return answer
    }
}

fun format(userList: Collection<AccountResource>): StringBuilder {
    return table {
        header("id", "username", "email", "password", "state", "membership", "registeredAt")

        userList.forEach {
            row(
                it.accountNumber,
                it.username,
                it.email,
                it.password,
                it.state,
                it.membership,
                it.registeredAt,
            )
        }

        hints {
            alignment("id", Table.Hints.Alignment.LEFT)
            alignment("username", Table.Hints.Alignment.LEFT)
            alignment("email", Table.Hints.Alignment.RIGHT)
            alignment("password", Table.Hints.Alignment.LEFT)
            alignment("state", Table.Hints.Alignment.LEFT)
            alignment("membership", Table.Hints.Alignment.LEFT)
            alignment("registeredAt", Table.Hints.Alignment.LEFT)
            borderStyle = Table.BorderStyle.SINGLE_LINE
        }
    }.render(StringBuilder())
}
