package ru.nilairan.notes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

import org.koin.dsl.module
import org.koin.ktor.ext.inject
import ru.nilairan.API_SIGNATURE
import ru.nilairan.JWT_NAME
import ru.nilairan.common.BaseResponse
import ru.nilairan.common.getUserIdByPrincipal
import ru.nilairan.notes.dto.CreateNoteDTO
import ru.nilairan.notes.repository.NotesRepository
import ru.nilairan.notes.repository.NotesRepositoryImpl
import ru.nilairan.notes.service.NotesService
import ru.nilairan.notes.service.NotesServiceImpl

fun Application.initNotesBlogModule() {
    routing {
        initNotesController()
    }
}

fun Routing.initNotesController() {

    val service: NotesService by inject()

    route("$API_SIGNATURE/$NOTES_ROUTE") {
        get {
            val allNotes = service.getAllNotes()
            call.respond(HttpStatusCode.OK, BaseResponse(allNotes))
        }
        authenticate(JWT_NAME) {
            get("my") {
                try {
                    call.respond(HttpStatusCode.OK, BaseResponse(service.getNotesByUserId(getUserIdByPrincipal())))
                } catch (e: Exception) {
                    e.printStackTrace()
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
            post("create") {
                try {
                    val createNoteDTO = call.receive<CreateNoteDTO>()
                    service.createNote(createNoteDTO, getUserIdByPrincipal())
                    call.respond(HttpStatusCode.OK)
                } catch (e: Exception) {
                    e.printStackTrace()
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
        }
    }
}

val notesAppModule = module {
    single<NotesService> { NotesServiceImpl(get()) }
    single<NotesRepository> { NotesRepositoryImpl(get()) }
}

const val NOTES_ROUTE = "notes"