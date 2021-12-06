package ru.nilairan.notes.repository

import org.jetbrains.exposed.sql.transactions.transaction
import ru.nilairan.notes.entity.NoteDAO
import ru.nilairan.notes.entity.NoteEntity
import ru.nilairan.notes.models.Note
import ru.nilairan.user.repository.UserRepository

class NotesRepositoryImpl(
    private val userRepository: UserRepository
) : NotesRepository {

    override fun getAllNotes(): Collection<Note> {
        return transaction {
            NoteEntity.all().toList().map { it.mapToNote(userRepository.getUserById(it.userId)?.mapToInfo()) }
        }
    }

    override fun getNoteByUserId(userId: Long): Collection<Note> {
        return transaction {
            NoteEntity.find { NoteDAO.userId eq userId }.toList().map {
                it.mapToNote(userRepository.getUserById(it.userId)?.mapToInfo())
            }
        }
    }

    override fun createNote(
        userId: Long,
        title: String,
        message: String,
        createAt: String
    ) {
        return transaction {
            NoteEntity.new {
                this.userId = userId
                this.title = title
                this.message = message
                this.createAt = createAt
            }
        }
    }
}