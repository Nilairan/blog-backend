package ru.nilairan.user.models

data class User(
    val id: Long,
    val email: String,
    val password: String,
    val firstname: String,
    val lastname: String
) {
    fun mapToInfo(): UserInfo {
        return UserInfo(id, firstname, lastname)
    }
}
