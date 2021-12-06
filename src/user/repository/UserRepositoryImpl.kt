package ru.nilairan.user.repository

import org.jetbrains.exposed.sql.transactions.transaction
import ru.nilairan.user.entity.UserEntity
import ru.nilairan.user.entity.UsersDAO
import ru.nilairan.user.models.User

class UserRepositoryImpl : UserRepository {

    override fun registerUser(
        email: String,
        password: String,
        firstname: String,
        lastname: String
    ) {
        transaction {
            UserEntity.new {
                this.email = email
                this.password = password
                this.firstname = firstname
                this.lastname = lastname
            }
        }
    }

    override fun getUserById(id: Long): User? {
        return transaction {
            UserEntity.findById(id)?.toUser()
        }
    }

    override fun getUserByEmail(email: String): User? {
        return transaction {
            UserEntity.find { UsersDAO.email eq email }.firstOrNull()?.toUser()
        }
    }

    override fun getAllUsers(): Collection<User> {
        return transaction { UserEntity.all().toList().map { it.toUser() } }
    }
}