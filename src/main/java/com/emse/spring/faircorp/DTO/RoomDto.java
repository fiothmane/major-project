package com.emse.spring.faircorp.DTO;

import com.emse.spring.faircorp.model.Ringer;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Status;

import java.util.ArrayList;
import java.util.List;

public class RoomDto {
    private Long id;

    private String name;

    private int floor;

    private List<Long> lightsIds;

    private Long ringerId;

    private Long autoLightControlId;

    private Long buildingId;

    public RoomDto() {

    }

    public RoomDto(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.floor = room.getFloor();
        if (room.getAutoLightControl() != null) {
            this.autoLightControlId = room.getAutoLightControl().getId();
        }
        if (room.getRinger() != null) {
            this.ringerId = room.getRinger().getId();
        }
        this.lightsIds = new ArrayList<Long>();
        if (room.getLights() != null) {
            for (int i = 0; i < room.getLights().size(); i++) {
                this.lightsIds.add(room.getLights().get(i).getId());
            }
        }
        if (room.getBuilding() != null) {
            this.buildingId = room.getBuilding().getId();
        }
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

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public List<Long> getLightsIds() {
        return lightsIds;
    }

    public void setLightsIds(List<Long> lightsIds) {
        this.lightsIds = lightsIds;
    }

    public Long getRingerId() {
        return ringerId;
    }

    public void setRingerId(Long ringerId) {
        this.ringerId = ringerId;
    }

    public Long getAutoLightControlId() {
        return autoLightControlId;
    }

    public void setAutoLightControlId(Long autoLightControlId) {
        this.autoLightControlId = autoLightControlId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }
}
