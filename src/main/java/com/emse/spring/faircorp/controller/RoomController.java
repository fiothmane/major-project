package com.emse.spring.faircorp.controller;

import com.emse.spring.faircorp.DAO.BuildingDao;
import com.emse.spring.faircorp.DAO.LightDao;
import com.emse.spring.faircorp.DAO.RoomDao;
import com.emse.spring.faircorp.DTO.RoomDto;
import com.emse.spring.faircorp.model.Light;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
@Transactional
public class RoomController {
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private LightDao lightDao;
    @Autowired
    private BuildingDao buildingDao;

    @GetMapping
    public List<RoomDto> findAll() {
        return roomDao.findAll()
                .stream()
                .map(RoomDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public RoomDto findById(@PathVariable Long id) {
        Room room = roomDao.findRoomById(id);
        return new RoomDto(room);
    }

    @PutMapping(path = "/{id}/switchLight")
    public RoomDto switchRoomLights(@PathVariable Long id) {
        Room room = roomDao.findRoomById(id);
        List<Light> roomLights = room.getLights();
        for (int i = 0; i < roomLights.size(); i++) {
            Status currentStatus = roomLights.get(i).getStatus();
            if (currentStatus.equals(Status.ON)) {
                roomLights.get(i).setStatus(Status.OFF);
            } else {
                roomLights.get(i).setStatus(Status.ON);
            }
        }
        return new RoomDto(room);
    }

    @PostMapping
    public RoomDto createRoom(@RequestBody RoomDto roomDto) {
        List<Light> roomLights = new ArrayList<Light>();
        for (int i = 0; i < roomDto.getLightsIds().size(); i++) {
            roomLights.add(lightDao.findById(roomDto.getLightsIds().get(i)));
        }

        Room room = new Room(roomDto.getId(), roomDto.getName(), roomDto.getFloor(), roomLights, buildingDao.findBuildingById(roomDto.getBuildingId()));
        roomDao.save(room);
        return new RoomDto(room);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteRoom(@PathVariable Long id) {
        Room room = roomDao.findRoomById(id);
        roomDao.delete(room);
    }
}