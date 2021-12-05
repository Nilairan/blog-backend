package ru.nilairan.notes.repository

import ru.nilairan.notes.models.Note

interface NotesRepository {
    fun getAllNotes(): Collection<Note>
    fun getNoteByUserId(userId: Long): Collection<Note>
}