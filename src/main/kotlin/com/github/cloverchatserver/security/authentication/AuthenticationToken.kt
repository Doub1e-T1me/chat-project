package com.github.cloverchatserver.security.authentication

import com.github.cloverchatserver.domain.user.controller.domain.ResponseUser
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class AuthenticationToken(
    private val principal: String,
    private val credentials: String?,
    authorities: Collection<GrantedAuthority>?,
    val responseUser: ResponseUser? = null,
) : AbstractAuthenticationToken(authorities) {

    companion object {

        fun requestToken(
            principal: String,
            credentials: String?,
            authorities: Collection<GrantedAuthority>? = null,
        ): AuthenticationToken {
            val requestToken = AuthenticationToken(principal, credentials, authorities)
            requestToken.setRequest()
            return requestToken
        }

        fun successToken(
            principal: String,
            authorities: Collection<GrantedAuthority>?,
            responseUser: ResponseUser,
        ): AuthenticationToken {
            val successToken = AuthenticationToken(principal, null, authorities, responseUser)
            successToken.setSuccess()
            successToken.details = responseUser
            return successToken
        }
    }

    private fun setRequest() {
        super.setAuthenticated(false)
    }

    private fun setSuccess() {
        super.setAuthenticated(true)
    }

    override fun getPrincipal(): String = principal
    override fun getCredentials(): String? = credentials
}