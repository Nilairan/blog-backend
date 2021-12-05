package ru.nilairan.user.dto

data class RegisterUserDTO(
    val email: String?,
    val password: String?,
    val firstname: String?,
    val lastname: String?
)