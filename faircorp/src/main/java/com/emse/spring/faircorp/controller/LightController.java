package com.emse.spring.faircorp.controller;

import com.emse.spring.faircorp.DAO.LightDao;
import com.emse.spring.faircorp.DTO.LightDto;
import com.emse.spring.faircorp.model.Light;
import com.emse.spring.faircorp.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lights")
@Transactional
public class LightController {
    @Autowired
    private LightDao lightDao;

    @GetMapping
    public List<Light> findAll() {
        return lightDao.findAll();
    }

    @GetMapping(path = "/{id}")
    public Light findById(@PathVariable Long id) {
        return lightDao.findById(id);
    }

    @PutMapping(path = "/{id}/switch")
    public void switchLigh(@PathVariable Long id) {
        Light light = lightDao.findById(id);
        Status currentStatus = light.getStatus();
        if (currentStatus.equals(Status.ON)) {
            light.setStatus(Status.OFF);
        }
        else {
            light.setStatus(Status.ON);
        }
    }

    @PostMapping
    public void createLight(@RequestBody Light light) {
        lightDao.save(light);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteLight(@PathVariable Long id) {
        Light light = lightDao.findById(id);
        lightDao.delete(light);
    }
}