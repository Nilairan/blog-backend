package ru.nilairan.user.entity

import org.jetbrains.exposed.dao.id.LongIdTable

object UsersDAO : LongIdTable(name = "user") {
    val email = varchar("email", 50)
    val password = varchar("password", 500)
    val firstname = varchar("firstname", 100)
    val lastname = varchar("lastname", 100)
}