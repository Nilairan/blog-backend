package ru.nilairan.user.repository

import ru.nilairan.user.models.User

interface UserRepository {
    fun registerUser(
        email: String,
        password: String,
        firstname: String,
        lastname: String
    )

    fun getAllUsers(): Collection<User>
    fun getUserById(id: Long): User?
    fun getUserByEmail(email: String): User?
}