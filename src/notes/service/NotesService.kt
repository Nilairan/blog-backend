package ru.nilairan.notes.service

import ru.nilairan.notes.models.Note

interface NotesService {
    fun getAllNotes(): Collection<Note>
    fun getNotesByEmail(email: String): Collection<Note>
}