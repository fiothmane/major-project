package com.emse.spring.faircorp.controller;

import com.emse.spring.faircorp.DAO.AutoLightDao;
import com.emse.spring.faircorp.DAO.RingerDao;
import com.emse.spring.faircorp.DAO.RoomDao;
import com.emse.spring.faircorp.DTO.AutoLightDto;
import com.emse.spring.faircorp.DTO.RingerDto;
import com.emse.spring.faircorp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/autoLightControllers")
@Transactional
public class AutoLightController {
    @Autowired
    private AutoLightDao autoLightDao;
    @Autowired
    private RoomDao roomDao;

    public void addHeaders (HttpServletResponse response) {
        response.addHeader("access-control-allow-credentials", "true");
        response.addHeader("access-control-allow-headers", "Origin,Accept,X-Requested-With,Content-Type,X-Auth-Token,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
        response.addHeader("access-control-allow-origin", "*");
        response.addHeader("content-type", "application/json;charset=UTF-8");
    }

    @GetMapping
    public List<AutoLightDto> findAll(HttpServletResponse response) {
        addHeaders(response);
        return autoLightDao.findAll()
                .stream()
                .map(AutoLightDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public AutoLightDto findById(@PathVariable Long id, HttpServletResponse response) {
        addHeaders(response);
        AutoLight autoLight = autoLightDao.findAutoLightById(id);
        return new AutoLightDto(autoLight);
    }

    @PutMapping(path = "/{id}/switch")
    public AutoLightDto switchAutoLightController(@PathVariable Long id, HttpServletResponse response) {
        addHeaders(response);
        AutoLight autoLight = autoLightDao.findAutoLightById(id);
        Status currentStatus = autoLight.getAutoLightControlState();
        if (currentStatus.equals(Status.ON)) {
            autoLight.setAutoLightControlState(Status.OFF);
        }
        else {
            autoLight.setAutoLightControlState(Status.ON);
        }
        return new AutoLightDto(autoLight);
    }

    @PutMapping(path = "/{id}/sunset-sunrise")
    public AutoLightDto changeSunsetSunrise(@PathVariable Long id, @RequestBody AutoLightDto body, HttpServletResponse response) {
        addHeaders(response);
        AutoLight autoLight = autoLightDao.findAutoLightById(id);
        autoLight.setSunriseTime(body.getSunriseTime());
        autoLight.setSunsetTime(body.getSunsetTime());
        if (body.getLatitude() != null) {
            autoLight.setLatitude(body.getLatitude());
        }
        if (body.getLongitude() != null) {
            autoLight.setLongitude(body.getLongitude());
        }
        return new AutoLightDto(autoLight);
    }

    @PostMapping
    public AutoLightDto createAutoLightController(@RequestBody AutoLightDto autoLightDto, HttpServletResponse response) {
        addHeaders(response);
        Room room = null;
        if (autoLightDto.getRoomId() != null) {
            room = roomDao.findRoomById(autoLightDto.getRoomId());
        }
        AutoLight autoLight = new AutoLight(autoLightDto.getId(), autoLightDto.getSunriseTime(), autoLightDto.getSunsetTime(), autoLightDto.getLatitude(), autoLightDto.getLongitude(), autoLightDto.getAutoLightControlState(), room);

        if (room != null) {
            roomDao.updateRoom(room);
        }

        autoLightDao.save(autoLight);
        return new AutoLightDto(autoLight);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteAutoLightController(@PathVariable Long id, HttpServletResponse response) {
        addHeaders(response);
        autoLightDao.delete(autoLightDao.findAutoLightById(id));
    }
}