package com.emse.spring.faircorp.DTO;

import java.util.List;

public class BuildingDto {
    private Long id;

    private String name;

    private int nbOfFloors;

    private List<RoomDto> rooms;

    public BuildingDto() {

    }

    public BuildingDto(Long id, List<RoomDto> rooms) {
        this.id = id;
        this.rooms = rooms;
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

    public int getNbOfFloors() {
        return nbOfFloors;
    }

    public void setNbOfFloors(int nbOfFloors) {
        this.nbOfFloors = nbOfFloors;
    }

    public List<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }
}
