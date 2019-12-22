package com.emse.spring.faircorp.controller;

import com.emse.spring.faircorp.DAO.LightDao;
import com.emse.spring.faircorp.DAO.RoomDao;
import com.emse.spring.faircorp.DTO.LightDto;
import com.emse.spring.faircorp.model.Light;
import com.emse.spring.faircorp.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<LightDto> findAll() {
        return lightDao.findAll()
                       .stream()
                       .map(LightDto::new)
                       .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public LightDto findById(@PathVariable Long id) {
        Light light = lightDao.findById(id);
        return new LightDto(light);
    }

    @PutMapping(path = "/{id}/switch")
    public LightDto switchLigh(@PathVariable Long id) {
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

    @PostMapping
    public LightDto createLight(@RequestBody LightDto lightDto) {
        Light light = new Light(lightDto.getId(), lightDto.getLevel(), lightDto.getStatus(), roomDao.findRoomById(lightDto.getRoomId()));
        lightDao.save(light);
        return new LightDto(light);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteLight(@PathVariable Long id) {
        lightDao.delete(lightDao.findById(id));
    }
}