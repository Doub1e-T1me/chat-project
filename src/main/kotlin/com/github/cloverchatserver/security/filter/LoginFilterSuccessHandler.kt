package com.github.cloverchatserver.security.filter

import com.github.cloverchatserver.domain.user.controller.domain.ResponseUser
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class LoginFilterSuccessHandler : AuthenticationSuccessHandler {

    private val mapper = jacksonObjectMapper()

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        if (response == null || request == null || authentication == null) {
            throw RuntimeException("object is null!")
        }

        val responseUser = authentication.details as ResponseUser

        response.status = HttpStatus.OK.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        mapper.writeValue(response.writer, responseUser)
    }
}