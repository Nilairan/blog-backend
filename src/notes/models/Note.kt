package ru.nilairan.notes.models

import ru.nilairan.user.models.UserInfo

data class Note(
    val id: Long,
    val userInfo: UserInfo,
    val title: String,
    val message: String,
    val createAt: String
)
