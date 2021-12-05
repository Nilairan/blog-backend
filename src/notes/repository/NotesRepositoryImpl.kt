package ru.nilairan.notes.repository

import ru.nilairan.notes.models.Note
import ru.nilairan.user.models.UserInfo
import java.time.LocalDateTime
import kotlin.random.Random

class NotesRepositoryImpl : NotesRepository {

    private val notesSet: MutableSet<Note> = mutableSetOf()

    init {
        notesSet.add(Note(Random.nextLong(), UserInfo(Random.nextLong(), "Test", "Testof"), "Title", "Message", LocalDateTime.now().toString()))
        notesSet.add(Note(Random.nextLong(), UserInfo(Random.nextLong(), "Test", "Testof"), "Title2", "Message2", LocalDateTime.now().toString()))
        notesSet.add(Note(Random.nextLong(), UserInfo(Random.nextLong(), "Test", "Testof"), "Title3", "Message3", LocalDateTime.now().toString()))
    }

    override fun getAllNotes(): Collection<Note> {
        return notesSet
    }

    override fun getNoteByUserId(userId: Long): Collection<Note> {
        return notesSet.filter { it.userInfo.id == userId }
    }
}