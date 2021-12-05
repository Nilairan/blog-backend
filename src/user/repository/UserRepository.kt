package ru.nilairan.user.repository

import ru.nilairan.user.models.User
import ru.nilairan.user.models.UserInfo

interface UserRepository {
    fun registerUser(user: User)
    fun getAllUsers(): Collection<User>
    fun getUserById(id: Long): User?
    fun getUserByEmail(email: String): User?
}