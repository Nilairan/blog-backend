package ru.nilairan.user.repository

import ru.nilairan.user.models.User
import ru.nilairan.user.models.UserInfo

class UserRepositoryImpl : UserRepository {
    private val usersSet: MutableSet<User> = mutableSetOf()

    override fun registerUser(user: User) {
        usersSet.add(user)
    }

    override fun getUserById(id: Long): User? {
        return usersSet.find { it.id == id }
    }

    override fun getUserByEmail(email: String): User? {
        return usersSet.find { it.email == email }
    }

    override fun getAllUsers(): Collection<User> {
        return usersSet
    }
}