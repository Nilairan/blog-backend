package ru.nilairan.notes.entity

import org.jetbrains.exposed.dao.id.LongIdTable

object NoteDAO : LongIdTable(name = "note") {
    val userId = long("user_id")
    val title = varchar("title", 100)
    val message = varchar("message", 2000)
    val createAt = varchar("create_at", 100)
}