package com.gideon.realtimetextcall.Controller;

import com.gideon.realtimetextcall.Domain.Document;
import com.gideon.realtimetextcall.Domain.Room;
import com.gideon.realtimetextcall.Repository.RoomRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Optional;

@Controller
public class DocumentController {

    private final RoomRepository roomRepository;

    public DocumentController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @MessageMapping("/document/{roomId}")
    @SendTo("/topic/public/{roomId}")
    public Document register(@Payload Map<String, String> payload, @DestinationVariable String roomId,SimpMessageHeaderAccessor headerAccessor) {
        Document document = new Document(payload.get("sender"), payload.get("content"), Long.parseLong(payload.get("time")));
        headerAccessor.getSessionAttributes().put("username", document.getSender());
        if (payload.containsKey("roomId")){
            Room room = new Room(Long.parseLong(payload.get("roomId")), payload.get("roomName"), payload.get("content"));
            roomRepository.save(room);
        }
        return document;
    }
}
