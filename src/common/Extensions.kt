package ru.nilairan.common

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.util.pipeline.*
import ru.nilairan.USERNAME_PRINCIPAL
import java.security.MessageDigest

fun String.getMd5Digest(): ByteArray = MessageDigest.getInstance("MD5").digest(this.toByteArray())

fun PipelineContext<*, ApplicationCall>.getUserIdByPrincipal(): Long {
    val principal = call.principal<JWTPrincipal>()
    return principal!!.payload.getClaim(USERNAME_PRINCIPAL).asLong()
}