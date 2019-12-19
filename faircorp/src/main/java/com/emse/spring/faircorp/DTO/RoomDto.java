package com.emse.spring.faircorp.DTO;

import com.emse.spring.faircorp.model.Building;

import java.util.List;

public class RoomDto {
    private Long id;

    private String name;

    private int floor;

    private List<LightDto> lights;

    private Building building;

    public RoomDto() {

    }

    public RoomDto(String name, int floor, List<LightDto> lights, Building building) {
        this.name = name;
        this.floor = floor;
        this.lights = lights;
        this.building = building;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = name;
    }

    public List<LightDto> getLights() {
        return lights;
    }

    public void setLights(List<LightDto> lights) {
        this.lights = lights;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
