package ru.nilairan.user.service.user

import ru.nilairan.user.dto.LoginUserDTO
import ru.nilairan.user.models.UserInfo

interface UserService {
    fun getAllUser(): Collection<UserInfo>
    fun getUserById(id: Long): UserInfo?
    fun validateLoginUserData(email: String, password: String): Boolean
}