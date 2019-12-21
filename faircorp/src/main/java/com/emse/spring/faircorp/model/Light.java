package com.emse.spring.faircorp.model;

import com.emse.spring.faircorp.DTO.LightDto;

import javax.persistence.*;

@Entity
public class Light {
    @Id
    private Long id;

    @Column(nullable = false)
    private Integer level;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private Room room;

    public Light() {

    }

    public Light(Long id, Integer level, Status status, Room room) {
        this.id = id;
        this.level = level;
        this.status = status;
        this.room = room;
//        room.getLights().add(this);
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}