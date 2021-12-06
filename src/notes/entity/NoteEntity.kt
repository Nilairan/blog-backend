package ru.nilairan.notes.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.nilairan.notes.models.Note
import ru.nilairan.user.models.UserInfo

class NoteEntity(
    id: EntityID<Long>
) : LongEntity(id) {

    var userId: Long by NoteDAO.userId
    var title: String by NoteDAO.title
    var message: String by NoteDAO.message
    var createAt: String by NoteDAO.createAt

    fun mapToNote(userInfo: UserInfo?) = Note(
        id.value,
        userInfo,
        title,
        message,
        createAt
    )

    companion object : LongEntityClass<NoteEntity>(NoteDAO)
}