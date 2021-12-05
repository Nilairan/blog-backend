package ru.nilairan.notes.service

import ru.nilairan.notes.models.Note
import ru.nilairan.notes.repository.NotesRepository
import ru.nilairan.user.repository.UserRepository

class NotesServiceImpl(
    private val notesRepository: NotesRepository,
    private val userRepository: UserRepository
) : NotesService {
    override fun getAllNotes(): Collection<Note> {
        return notesRepository.getAllNotes()
    }

    override fun getNotesByEmail(email: String): Collection<Note> {
        return userRepository.getUserByEmail(email)?.id?.let {
            notesRepository.getNoteByUserId(it)
        }?: run {
            throw IllegalArgumentException()
        }
    }
}