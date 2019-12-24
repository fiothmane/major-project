package com.emse.spring.faircorp.controller;

import com.emse.spring.faircorp.DAO.BuildingDao;
import com.emse.spring.faircorp.DAO.RoomDao;
import com.emse.spring.faircorp.DTO.BuildingDto;
import com.emse.spring.faircorp.model.Building;
import com.emse.spring.faircorp.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/buildings")
@Transactional
public class BuildingController {
    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private RoomDao roomDao;

    public void addHeaders (HttpServletResponse response) {
        response.addHeader("access-control-allow-credentials", "true");
        response.addHeader("access-control-allow-headers", "Origin,Accept,X-Requested-With,Content-Type,X-Auth-Token,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
        response.addHeader("access-control-allow-origin", "*");
        response.addHeader("content-type", "application/json;charset=UTF-8");
    }

    @GetMapping
    public List<BuildingDto> findAll(HttpServletResponse response) {
        addHeaders(response);
        return buildingDao.findAll()
                .stream()
                .map(BuildingDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public BuildingDto findById(@PathVariable Long id, HttpServletResponse response) {
        addHeaders(response);
        Building building = buildingDao.findBuildingById(id);
        return new BuildingDto(building);
    }

    @PostMapping
    public BuildingDto createBuilding(@RequestBody BuildingDto buildingDto, HttpServletResponse response) {
        addHeaders(response);
        List<Room> buildingRooms = new ArrayList<Room>();
        if (buildingDto.getRoomsIds() != null) {
            for (int i = 0; i < buildingDto.getRoomsIds().size(); i++) {
                buildingRooms.add(roomDao.findRoomById(buildingDto.getRoomsIds().get(i)));
            }
        }

        Building building = new Building(buildingDto.getId(), buildingDto.getName(), buildingDto.getNbOfFloors(), buildingRooms);
        buildingDao.save(building);

        if (buildingRooms != null) {
            for (int i = 0; i < buildingRooms.size(); i++) {
                buildingRooms.get(i).setBuilding(building);
                roomDao.updateRoom(buildingRooms.get(i));
            }
        }
        return new BuildingDto(building);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteBuilding(@PathVariable Long id, HttpServletResponse response) {
        addHeaders(response);
        buildingDao.delete(buildingDao.findBuildingById(id));
    }
}
