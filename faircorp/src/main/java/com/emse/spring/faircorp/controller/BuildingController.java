package com.emse.spring.faircorp.controller;

import com.emse.spring.faircorp.DAO.BuildingDao;
import com.emse.spring.faircorp.DAO.LightDao;
import com.emse.spring.faircorp.DAO.RoomDao;
import com.emse.spring.faircorp.DTO.BuildingDto;
import com.emse.spring.faircorp.DTO.LightDto;
import com.emse.spring.faircorp.model.Building;
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
@RequestMapping("/api/buildings")
@Transactional
public class BuildingController {
    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private RoomDao roomDao;

    @GetMapping
    public List<BuildingDto> findAll() {
        return buildingDao.findAll()
                .stream()
                .map(BuildingDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public BuildingDto findById(@PathVariable Long id) {
        Building building = buildingDao.findBuildingById(id);
        return new BuildingDto(building);
    }

    @PostMapping
    public BuildingDto createBuilding(@RequestBody BuildingDto buildingDto) {
        List<Room> buildingRooms = new ArrayList<Room>();
        for (int i = 0; i < buildingDto.getRoomsIds().size(); i++) {
            buildingRooms.add(roomDao.findRoomById(buildingDto.getRoomsIds().get(i)));
        }

        Building building = new Building(buildingDto.getId(), buildingDto.getName(), buildingDto.getNbOfFloors(), buildingRooms);
        buildingDao.save(building);
        return new BuildingDto(building);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteBuilding(@PathVariable Long id) {
        buildingDao.delete(buildingDao.findBuildingById(id));
    }
}
