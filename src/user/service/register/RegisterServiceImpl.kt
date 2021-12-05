package ru.nilairan.user.service.register

import io.ktor.util.*
import ru.nilairan.common.getMd5Digest
import ru.nilairan.user.dto.RegisterUserDTO
import ru.nilairan.user.models.User
import ru.nilairan.user.repository.UserRepository
import java.security.MessageDigest
import kotlin.random.Random

class RegisterServiceImpl(
    private val repository: UserRepository
) : RegisterService {

    @OptIn(InternalAPI::class)
    override fun registerUser(registerUserDTO: RegisterUserDTO) {
        if (validateRegisterData(registerUserDTO)) {
            repository.getUserByEmail(registerUserDTO.email!!)?.let {
                throw IllegalArgumentException()
            } ?: run {
                repository.registerUser(
                    User(
                        id = Random.nextLong(),
                        email = registerUserDTO.email,
                        password = registerUserDTO.password!!.getMd5Digest().encodeBase64(),
                        firstname = registerUserDTO.firstname!!,
                        lastname = registerUserDTO.lastname!!
                    )
                )
            }
        } else {
            throw IllegalArgumentException()
        }
    }

    override fun validateRegisterData(registerUserDTO: RegisterUserDTO): Boolean {
        registerUserDTO.email ?: return false
        registerUserDTO.firstname ?: return false
        registerUserDTO.lastname ?: return false
        registerUserDTO.password ?: return false
        return true
    }
}