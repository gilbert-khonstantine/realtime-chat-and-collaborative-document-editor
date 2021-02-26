package com.gideon.realtimetextcall.Controller;

import com.gideon.realtimetextcall.Domain.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Map;
@Controller
public class ChatController {

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessage register(@Payload Map<String, String> payload, @DestinationVariable String roomId, SimpMessageHeaderAccessor headerAccessor) {
        ChatMessage chatMessage = new ChatMessage(payload.get("sender"), payload.get("content"));
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        System.out.println("chat is in");
        System.out.println(payload);
        return chatMessage;
    }

}
