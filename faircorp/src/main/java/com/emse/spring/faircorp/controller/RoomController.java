package com.emse.spring.faircorp.controller;

import com.emse.spring.faircorp.DAO.RoomDao;
import com.emse.spring.faircorp.model.Light;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@Transactional
public class RoomController {
    @Autowired
    private RoomDao roomDao;

    @GetMapping
    public List<Room> findAll() {
        return roomDao.findAll();
    }

    @GetMapping(path = "/{id}")
    public Room findById(@PathVariable Long id) {
        return roomDao.findRoomById(id);
    }

    @PutMapping(path = "/{id}/switchLight")
    public void switchRoomLights(@PathVariable Long id) {
        Room room = roomDao.findRoomById(id);
        List<Light> roomLights = room.getLights();

        for (int i = 0; i < roomLights.size(); i++) {
            Status currentStatus = roomLights.get(i).getStatus();
            if (currentStatus.equals(Status.ON)) {
                roomLights.get(i).setStatus(Status.OFF);
            }
            else {
                roomLights.get(i).setStatus(Status.ON);
            }
        }
    }

    @PostMapping
    public void createRoom(@RequestBody Room room) {
        roomDao.save(room);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteRoom(@PathVariable Long id) {
        Room room = roomDao.findRoomById(id);
        roomDao.delete(room);
    }
}