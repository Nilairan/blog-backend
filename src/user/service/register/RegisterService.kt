package ru.nilairan.user.service.register

import ru.nilairan.user.dto.RegisterUserDTO

interface RegisterService {

    fun registerUser(registerUserDTO: RegisterUserDTO)

    fun validateRegisterData(registerUserDTO: RegisterUserDTO): Boolean
}