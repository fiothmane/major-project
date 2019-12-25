package com.emse.spring.faircorp.controller;

import com.emse.spring.faircorp.DAO.LightDao;
import com.emse.spring.faircorp.DAO.RoomDao;
import com.emse.spring.faircorp.DTO.LightDto;
import com.emse.spring.faircorp.model.Light;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lights")
@Transactional
public class LightController {
    @Autowired
    private LightDao lightDao;
    @Autowired
    private RoomDao roomDao;

    public void addHeaders (HttpServletResponse response) {
        response.addHeader("access-control-allow-credentials", "true");
        response.addHeader("access-control-allow-headers", "Origin,Accept,X-Requested-With,Content-Type,X-Auth-Token,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
        response.addHeader("access-control-allow-origin", "*");
        response.addHeader("content-type", "application/json;charset=UTF-8");
    }

    @GetMapping
    public List<LightDto> findAll(HttpServletResponse response) {
        addHeaders(response);
        return lightDao.findAll()
                       .stream()
                       .map(LightDto::new)
                       .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public LightDto findById(@PathVariable Long id, HttpServletResponse response) {
        addHeaders(response);
        Light light = lightDao.findById(id);
        return new LightDto(light);
    }

    @PutMapping(path = "/{id}/switch")
    public LightDto switchLigh(@PathVariable Long id, HttpServletResponse response) {
        addHeaders(response);
        Light light = lightDao.findById(id);
        Status currentStatus = light.getStatus();
        if (currentStatus.equals(Status.ON)) {
            light.setStatus(Status.OFF);
        }
        else {
            light.setStatus(Status.ON);
        }
        return new LightDto(light);
    }

    @PutMapping(path = "/{id}/level")
    public LightDto changeLevel(@PathVariable Long id, @RequestBody LightDto body,  HttpServletResponse response) {
        addHeaders(response);
        Light light = lightDao.findById(id);
        light.setLevel(body.getLevel());
        return new LightDto(light);
    }

    @PostMapping
    public LightDto createLight(@RequestBody LightDto lightDto, HttpServletResponse response) {
        addHeaders(response);
        Room room = null;
        if (lightDto.getRoomId() != null) {
            room = roomDao.findRoomById(lightDto.getRoomId());
        }

        Light light = new Light(lightDto.getId(), lightDto.getLevel(), lightDto.getStatus(), room);
        lightDao.save(light);

        if (room != null) {
            roomDao.updateRoom(room);
        }
        return new LightDto(light);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteLight(@PathVariable Long id, HttpServletResponse response) {
        addHeaders(response);
        lightDao.delete(lightDao.findById(id));
    }
}