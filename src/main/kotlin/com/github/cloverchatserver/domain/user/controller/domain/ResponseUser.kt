package com.github.cloverchatserver.domain.user.controller.domain

data class ResponseUser(
    val id: Long,
    val email: String,
    val nickname: String
)