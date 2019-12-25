package com.emse.spring.faircorp.controller;

import com.emse.spring.faircorp.DAO.RingerDao;
import com.emse.spring.faircorp.DAO.RoomDao;
import com.emse.spring.faircorp.DTO.LightDto;
import com.emse.spring.faircorp.DTO.RingerDto;
import com.emse.spring.faircorp.model.Ringer;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ringers")
@Transactional
public class RingerController {
    @Autowired
    private RingerDao ringerDao;
    @Autowired
    private RoomDao roomDao;

    public void addHeaders (HttpServletResponse response) {
        response.addHeader("access-control-allow-credentials", "true");
        response.addHeader("access-control-allow-headers", "Origin,Accept,X-Requested-With,Content-Type,X-Auth-Token,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
        response.addHeader("access-control-allow-origin", "*");
        response.addHeader("content-type", "application/json;charset=UTF-8");
    }

    @GetMapping
    public List<RingerDto> findAll(HttpServletResponse response) {
        addHeaders(response);
        return ringerDao.findAll()
                .stream()
                .map(RingerDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public RingerDto findById(@PathVariable Long id, HttpServletResponse response) {
        addHeaders(response);
        Ringer ringer = ringerDao.findById(id);
        return new RingerDto(ringer);
    }

    @PutMapping(path = "/{id}/switch")
    public RingerDto switchRinger(@PathVariable Long id, HttpServletResponse response) {
        addHeaders(response);
        Ringer ringer = ringerDao.findById(id);
        System.out.println("ICIIIII : " + ringer.getStatus());
        Status currentStatus = ringer.getStatus();
        if (currentStatus.equals(Status.ON)) {
            ringer.setStatus(Status.OFF);
        }
        else {
            ringer.setStatus(Status.ON);
        }
        return new RingerDto(ringer);
    }

    @PutMapping(path = "/{id}/level")
    public RingerDto changeLevel(@PathVariable Long id, @RequestBody RingerDto body, HttpServletResponse response) {
        addHeaders(response);
        Ringer ringer = ringerDao.findById(id);
        ringer.setLevel(body.getLevel());
        return new RingerDto(ringer);
    }

    @PostMapping
    public RingerDto createRinger(@RequestBody RingerDto ringerDto, HttpServletResponse response) {
        addHeaders(response);
        Room room = null;
        if (ringerDto.getRoomId() != null) {
            room = roomDao.findRoomById(ringerDto.getRoomId());
        }

        Ringer ringer = new Ringer(ringerDto.getId(), ringerDto.getLevel(), ringerDto.getStatus(), room);
        if (room != null) {
            roomDao.updateRoom(room);
            ringerDao.removeRingerFromPreviousRoom(ringerDao.findByRoomId(room.getId()));
        }

        ringerDao.save(ringer);
        return new RingerDto(ringer);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteRinger(@PathVariable Long id, HttpServletResponse response) {
        addHeaders(response);
        ringerDao.delete(ringerDao.findById(id));
    }
}