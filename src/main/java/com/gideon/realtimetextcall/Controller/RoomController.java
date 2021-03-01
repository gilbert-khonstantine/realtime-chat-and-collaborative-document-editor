package com.gideon.realtimetextcall.Controller;
import javax.validation.Valid;

import com.gideon.realtimetextcall.Domain.Document;
import com.gideon.realtimetextcall.Domain.Room;
import com.gideon.realtimetextcall.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class RoomController {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping(value = {"/index","/"})
    public String showRoomList(Model model) {
        model.addAttribute("rooms", roomRepository.findAll());
        model.addAttribute("roomsLength",((Collection<?>) roomRepository.findAll()).size());
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(Room room) {
        return "add-room";
    }

    @PostMapping("/addroom")
    public String addRoom(@Valid Room room, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-room";
        }

        roomRepository.save(room);
        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid room Id:" + id));
        model.addAttribute("room", room);

        return "update-room";
    }

    @PostMapping("/update/{id}")
    public String updateRoom(@PathVariable("id") long id, @Valid Room room, BindingResult result, Model model) {
        if (result.hasErrors()) {
            room.setId(id);
            return "update-room";
        }

        roomRepository.save(room);

        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable("id") long id, Model model) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid room Id:" + id));
        roomRepository.delete(room);

        return "redirect:/index";
    }

    @GetMapping("/room/{id}")
    public String findRoomId(@PathVariable("id") long id, Model model) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid room Id:" + id));
        model.addAttribute("room",room);
        return "room";
    }

    @MessageMapping("/room/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Map<String, String> register(@Payload Map<String, String> payload, @DestinationVariable String roomId, SimpMessageHeaderAccessor headerAccessor) {
        return payload;
    }
}
