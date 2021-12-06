package ru.nilairan.user.service.register

import io.ktor.util.*
import ru.nilairan.common.getMd5Digest
import ru.nilairan.user.dto.RegisterUserDTO
import ru.nilairan.user.repository.UserRepository
import java.util.regex.Pattern

class RegisterServiceImpl(
    private val repository: UserRepository
) : RegisterService {

    private val emailMatcher =
        Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")

    @OptIn(InternalAPI::class)
    override fun registerUser(registerUserDTO: RegisterUserDTO) {
        if (validateRegisterData(registerUserDTO)) {
            repository.getUserByEmail(registerUserDTO.email!!)?.let {
                throw IllegalArgumentException()
            } ?: run {
                repository.registerUser(
                    registerUserDTO.email,
                    password = registerUserDTO.password!!.getMd5Digest().encodeBase64(),
                    firstname = registerUserDTO.firstname!!,
                    lastname = registerUserDTO.lastname!!
                )
            }
        } else {
            throw IllegalArgumentException()
        }
    }

    override fun validateRegisterData(registerUserDTO: RegisterUserDTO): Boolean {
        if (emailMatcher.matcher(registerUserDTO.email).matches().not()) {
            return false
        } else if (registerUserDTO.firstname.isNullOrEmpty()
            || registerUserDTO.lastname.isNullOrEmpty() || registerUserDTO.password.isNullOrEmpty()
        ) {
            return false
        } else if (registerUserDTO.password.length < 8) {
            return false
        }
        return true
    }
}