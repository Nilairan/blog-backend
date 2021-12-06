package ru.nilairan.notes.service

import ru.nilairan.notes.dto.CreateNoteDTO
import ru.nilairan.notes.models.Note
import ru.nilairan.notes.repository.NotesRepository
import java.time.LocalDateTime

class NotesServiceImpl(
    private val notesRepository: NotesRepository
) : NotesService {
    override fun getAllNotes(): Collection<Note> {
        return notesRepository.getAllNotes()
    }

    override fun getNotesByUserId(id: Long): Collection<Note> {
        return notesRepository.getNoteByUserId(id)
    }

    override fun createNote(createNoteDTO: CreateNoteDTO, userId: Long) {
        notesRepository.createNote(
            userId,
            createNoteDTO.title,
            createNoteDTO.message,
            LocalDateTime.now().toString()
        )
    }
}