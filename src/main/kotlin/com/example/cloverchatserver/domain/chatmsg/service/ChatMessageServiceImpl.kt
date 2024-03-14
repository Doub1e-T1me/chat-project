package com.example.cloverchatserver.domain.chatmsg.service

import com.example.cloverchatserver.domain.chatroom.service.ChatRoomNotFoundException
import com.example.cloverchatserver.domain.chatroom.service.ChatRoomService
import com.example.cloverchatserver.domain.chatmsg.controller.domain.RequestChatMessagesReadForm
import com.example.cloverchatserver.domain.chatmsg.controller.domain.RequestStompChatMessage
import com.example.cloverchatserver.domain.chatmsg.repository.ChatMessage
import com.example.cloverchatserver.domain.chatmsg.repository.ChatMessageRepository
import com.example.cloverchatserver.domain.user.controller.domain.ResponseUser
import com.example.cloverchatserver.domain.user.service.UserNotFoundException
import com.example.cloverchatserver.domain.user.service.UserService
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatMessageServiceImpl(

    val chatMessageRepository: ChatMessageRepository,
    val chatRoomService: ChatRoomService,
    val userService: UserService

) : ChatMessageService {

    @Transactional
    override fun getChatMessagesBy(form: RequestChatMessagesReadForm): List<ChatMessage> {
        val chatRoom = chatRoomService.getChatRoomById(form.chatRoomId)
            ?: throw ChatRoomNotFoundException()

        if (form.password != chatRoom.password) {
            throw BadCredentialsException("password is wrong")
        }

        return chatMessageRepository.findByChatRoom(chatRoom)
    }

    @Transactional
    override fun createChatMessage(requestStompChatMessage: RequestStompChatMessage, responseUser: ResponseUser): ChatMessage {
        val chatRoom = chatRoomService.getChatRoomById(requestStompChatMessage.chatRoomId)
            ?: throw ChatRoomNotFoundException()

        val createUser = userService.getUserBy(responseUser.id)
            ?: throw UserNotFoundException()

        val chatMessage = requestStompChatMessage.toChatMessage(chatRoom, createUser)
        chatRoom.chatMessages.add(chatMessage)

        return chatMessageRepository.save(chatMessage)
    }
}