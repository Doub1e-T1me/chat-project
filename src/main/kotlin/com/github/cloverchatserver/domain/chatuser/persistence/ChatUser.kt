package com.github.cloverchatserver.domain.chatuser.persistence

import com.github.cloverchatserver.domain.chatuser.api.domain.ResponseChatUser
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import com.github.cloverchatserver.domain.account.persistence.Account
import jakarta.persistence.*

@Entity
@Table(name = "chat_user")
class ChatUser(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_user_id")
    val id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    val chatRoom: ChatRoom,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    val account: Account,

    @Column(unique = true, nullable = false, updatable = false, length = 100)
    val sessionId: String
) {

    fun toResponseChatUser(): ResponseChatUser {
        if (id == null) {
            throw RuntimeException("id is null")
        }

        return ResponseChatUser(id!!, chatRoom.toResponseChatRoom(), account.toResponseUser())
    }
}