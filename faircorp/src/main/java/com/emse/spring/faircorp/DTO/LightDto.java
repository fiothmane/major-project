package com.emse.spring.faircorp.DTO;

import com.emse.spring.faircorp.model.Light;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Status;

public class LightDto {
    private Long id;

    private Integer level;

    private Status status;

    private Long roomId;

    public LightDto() {

    }

    public LightDto(Light light) {
        this.id = light.getId();
        this.level = light.getLevel();
        this.status = light.getStatus();
        this.roomId = light.getRoom().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}