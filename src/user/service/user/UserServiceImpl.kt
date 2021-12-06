package ru.nilairan.user.service.user

import io.ktor.util.*
import ru.nilairan.common.getMd5Digest
import ru.nilairan.user.models.UserInfo
import ru.nilairan.user.repository.UserRepository

class UserServiceImpl(
    private val repository: UserRepository
) : UserService {
    override fun getAllUser(): Collection<UserInfo> {
        return repository.getAllUsers().map { it.mapToInfo() }
    }

    override fun getUserById(id: Long): UserInfo? {
        return repository.getUserById(id)?.mapToInfo()
    }

    @OptIn(InternalAPI::class)
    override fun validateLoginUserData(email: String, password: String): Boolean {
        return repository.getUserByEmail(email)?.let {
            it.email == email && it.password.decodeBase64Bytes().contentEquals(password.getMd5Digest())
        } ?: false
    }

    override fun getUserByEmail(email: String): UserInfo? {
        return repository.getUserByEmail(email)?.mapToInfo()
    }
}