package ru.nilairan.user

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import ru.nilairan.API_SIGNATURE
import ru.nilairan.JWT_LIVE_TIME
import ru.nilairan.USERNAME_PRINCIPAL
import ru.nilairan.common.BaseResponse
import ru.nilairan.user.dto.LoginUserDTO
import ru.nilairan.user.dto.RegisterUserDTO
import ru.nilairan.user.dto.TokenDTO
import ru.nilairan.user.repository.UserRepository
import ru.nilairan.user.repository.UserRepositoryImpl
import ru.nilairan.user.service.register.RegisterService
import ru.nilairan.user.service.register.RegisterServiceImpl
import ru.nilairan.user.service.user.UserService
import ru.nilairan.user.service.user.UserServiceImpl
import java.util.*

fun Application.initUserModule(audience: String, issuer: String, secret: String) {
    routing {
        initUserController(audience, issuer, secret)
    }
}

fun Routing.initUserController(audience: String, issuer: String, secret: String) {
    val userService: UserService by inject()
    val registerService: RegisterService by inject()

    route("$API_SIGNATURE/$USER_ROUTE") {
        get {
            call.respond(HttpStatusCode.OK, BaseResponse(userService.getAllUser()))
        }
        get("{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            id?.let {
                userService.getUserById(it)?.let { user ->
                    call.respond(HttpStatusCode.OK, BaseResponse(user))
                } ?: run {
                    call.respond(HttpStatusCode.NotFound)
                }
            } ?: run {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
        post("registration") {
            val registerUserDTO = call.receive<RegisterUserDTO>()
            when (registerService.validateRegisterData(registerUserDTO)) {
                true -> {
                    try {
                        registerService.registerUser(registerUserDTO)
                        call.respond(HttpStatusCode.OK)
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                }
                false -> call.respond(HttpStatusCode.BadRequest)
            }
        }
        post("auth") {
            val user = call.receive<LoginUserDTO>()
            if (user.email.isNullOrEmpty().not() && user.password.isNullOrEmpty().not()) {
                if (userService.validateLoginUserData(user.email!!, user.password!!)) {
                    userService.getUserByEmail(user.email)?.let { userInfo ->
                        val token = JWT.create()
                            .withAudience(audience)
                            .withIssuer(issuer)
                            .withClaim(USERNAME_PRINCIPAL, userInfo.id)
                            .withExpiresAt(Date(System.currentTimeMillis() + JWT_LIVE_TIME))
                            .sign(Algorithm.HMAC256(secret))
                        call.respond(HttpStatusCode.OK, BaseResponse(TokenDTO(token)))
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

val userAppModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    single<RegisterService> { RegisterServiceImpl(get()) }
    single<UserService> { UserServiceImpl(get()) }
}

const val USER_ROUTE = "user"