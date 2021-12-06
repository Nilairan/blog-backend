package ru.nilairan

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.jackson.*
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.ext.Koin
import ru.nilairan.notes.initNotesBlogModule
import ru.nilairan.notes.notesAppModule
import ru.nilairan.user.initUserModule
import ru.nilairan.user.userAppModule
import java.text.DateFormat

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()
    initDB()
    install(DefaultHeaders)
    install(CallLogging)
    install(Authentication) {
        jwt(JWT_NAME) {
            realm = myRealm
            verifier(JWT
                .require(Algorithm.HMAC256(secret))
                .withAudience(audience)
                .withIssuer(issuer)
                .build()
            )
            validate { credential ->
                if (credential.payload.getClaim(USERNAME_PRINCIPAL).asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
    install(Koin) {
        modules(userAppModule)
        modules(notesAppModule)
    }
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            dateFormat = DateFormat.getDateInstance()
        }
    }
    initNotesBlogModule()
    initUserModule(audience, issuer, secret)
}

fun initDB() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/blog",
        driver = "org.postgresql.Driver",
        user = "ivankholodov",
        password = "dqqb8n9y",
    )
}

const val API_VERSION = 1
const val API_SIGNATURE = "api/v$API_VERSION"
const val JWT_NAME = "auth-jwt"
const val JWT_LIVE_TIME = 3600000
const val USERNAME_PRINCIPAL = "ID"
