package ru.nilairan.user.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.nilairan.user.models.User

class UserEntity(
    id: EntityID<Long>
) : LongEntity(id) {
    var email: String by UsersDAO.email
    var password: String by UsersDAO.password
    var firstname: String by UsersDAO.firstname
    var lastname: String by UsersDAO.lastname

    fun toUser() = User(id.value, email, password, firstname, lastname)

    companion object : LongEntityClass<UserEntity>(UsersDAO)
}