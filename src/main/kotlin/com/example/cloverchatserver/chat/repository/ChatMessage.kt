package com.example.cloverchatserver.chat.repository

import com.example.cloverchatserver.board.repository.ChatRoom
import com.example.cloverchatserver.chat.controller.ResponseChatMessage
import com.example.cloverchatserver.user.repository.User
import java.lang.RuntimeException
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "chat_message")
class ChatMessage(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    val id: Long?,

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    val chatRoom: ChatRoom,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val createUser: User,

    @Column(length = 200, nullable = false, updatable = false)
    val content: String,

    @Column(nullable = false, updatable = false)
    val createAt: LocalDateTime
) {

    fun toResponseChatMessage(): ResponseChatMessage {
        if (id == null) {
            throw RuntimeException("id is null")
        }

        return ResponseChatMessage(
            id,
            chatRoom.toResponseChatRoom(),
            createUser.toResponseUser(),
            content,
            createAt
        )
    }
}