package com.emse.rommsproject.controllers;


import com.emse.rommsproject.models.Room;
import com.emse.rommsproject.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/rooms")
    public List getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/rooms/{roomId}")
    public Room getRoom(@PathVariable String roomId){
        return roomService.getRoom(roomId);
    }

    @PostMapping("/rooms")
    public void addRoom(@RequestBody Room room) {
        roomService.addRoom(room);
    }

    @PutMapping("/rooms/{roomId}")
    public void updateRoom(@PathVariable String roomId, @RequestBody Room room) {
        roomService.updateRoom(roomId, room);
    }

    @DeleteMapping("/rooms/{roomId}")
    public void deleteRoom(@PathVariable String roomId) {
        roomService.deleteRoom(roomId);
    }
}
