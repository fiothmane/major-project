package com.emse.spring.faircorp.controller;

import com.emse.spring.faircorp.DAO.*;
import com.emse.spring.faircorp.DTO.LightDto;
import com.emse.spring.faircorp.DTO.RoomDto;
import com.emse.spring.faircorp.model.*;
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
    private RingerDao ringerDao;
    @Autowired
    private ThermostatDao thermostatDao;
    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private AutoLightDao autoLightDao;

    public void addHeaders (HttpServletResponse response) {
        response.addHeader("access-control-allow-credentials", "true");
        response.addHeader("access-control-allow-headers", "Origin,Accept,X-Requested-With,Content-Type,X-Auth-Token,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
        response.addHeader("access-control-allow-origin", "*");
        response.addHeader("content-type", "application/json;charset=UTF-8");
    }

    @GetMapping
    public List<RoomDto> findAll(HttpServletResponse response) {
        addHeaders(response);
        return roomDao.findAll()
                .stream()
                .map(RoomDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
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
        if (roomDto.getLightsIds() != null) {
            for (int i = 0; i < roomDto.getLightsIds().size(); i++) {
                roomLights.add(lightDao.findById(roomDto.getLightsIds().get(i)));
            }
        }
        Ringer ringer = null;
        if (roomDto.getRingerId() != null) {
            ringer = ringerDao.findById(roomDto.getRingerId());
        }
        Thermostat thermostat = null;
        if (roomDto.getThermostatId() != null) {
            thermostat = thermostatDao.findById(roomDto.getThermostatId());
        }
        Building building = null;
        if (roomDto.getBuildingId() != null) {
            building = buildingDao.findBuildingById(roomDto.getBuildingId());
        }
        int floor = -999;
        if (roomDto.getFloor() != -999) {
            floor = roomDto.getFloor();
        }
        AutoLight autoLight = null;
        if (roomDto.getAutoLightControlId() != null) {
            autoLight = autoLightDao.findAutoLightById(roomDto.getAutoLightControlId());
        }

        Room room = new Room(roomDto.getId(), roomDto.getName(), floor, autoLight, roomLights, ringer, thermostat, building);
        roomDao.save(room);
        if (ringer != null) {
            ringer.setRoom(room);
            ringerDao.updateRinger(ringer);
        }
        if (roomLights != null) {
            for (int i = 0; i < roomLights.size(); i++) {
                roomLights.get(i).setRoom(room);
                lightDao.updateLight(roomLights.get(i));
            }
        }
        return new RoomDto(room);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteRoom(@PathVariable Long id, HttpServletResponse response) {
        addHeaders(response);
        Room room = roomDao.findRoomById(id);
        /* Delete the room's lights */
        if (room.getLights() != null) {
            for (int i = 0; i < room.getLights().size(); i++) {
                lightDao.delete(room.getLights().get(i));
            }
        }
        /* Delete the room's ringer */
        if (room.getRinger() != null) {
            ringerDao.delete(room.getRinger());
        }
        /* Delete the room's thermostat */
        if (room.getThermostat() != null) {
            thermostatDao.delete(room.getThermostat());
        }
        /* Delete the room */
        roomDao.delete(room);
    }
}