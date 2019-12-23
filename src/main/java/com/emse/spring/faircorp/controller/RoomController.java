package com.emse.spring.faircorp.controller;

import com.emse.spring.faircorp.DAO.BuildingDao;
import com.emse.spring.faircorp.DAO.LightDao;
import com.emse.spring.faircorp.DAO.RoomDao;
import com.emse.spring.faircorp.DTO.RoomDto;
import com.emse.spring.faircorp.model.Light;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    public void addHeaders (HttpServletResponse response) {
        response.addHeader("access-control-allow-credentials", "true");
        response.addHeader("access-control-allow-headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
        response.addHeader("access-control-allow-origin", "*");
        response.addHeader("content-type", "application/json;charset=UTF-8");
    }

    @GetMapping
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<RoomDto> findAll(HttpServletResponse response) {
        addHeaders(response);
        return roomDao.findAll()
                .stream()
                .map(RoomDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public RoomDto findById(@PathVariable Long id, HttpServletResponse response) {
        addHeaders(response);
        Room room = roomDao.findRoomById(id);
        return new RoomDto(room);
    }

    @PutMapping(path = "/{id}/switchLight")
    public RoomDto switchRoomLights(@PathVariable Long id, HttpServletResponse response) {
        addHeaders(response);
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
    public RoomDto createRoom(@RequestBody RoomDto roomDto, HttpServletResponse response) {
        addHeaders(response);
        List<Light> roomLights = new ArrayList<Light>();
        for (int i = 0; i < roomDto.getLightsIds().size(); i++) {
            roomLights.add(lightDao.findById(roomDto.getLightsIds().get(i)));
        }

        Room room = new Room(roomDto.getId(), roomDto.getName(), roomDto.getFloor(), roomLights, buildingDao.findBuildingById(roomDto.getBuildingId()));
        roomDao.save(room);
        return new RoomDto(room);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteRoom(@PathVariable Long id, HttpServletResponse response) {
        addHeaders(response);
        Room room = roomDao.findRoomById(id);
        roomDao.delete(room);
    }
}