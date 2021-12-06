package ru.nilairan.notes.service

import ru.nilairan.notes.dto.CreateNoteDTO
import ru.nilairan.notes.models.Note

interface NotesService {
    fun getAllNotes(): Collection<Note>
    fun getNotesByUserId(id: Long): Collection<Note>
    fun createNote(createNoteDTO: CreateNoteDTO, userId: Long)
}