package com.emse.spring.faircorp.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue
    private Long id;

//    @Column(nullable = false)
    @Column
    private String name;

    @Column(nullable = false)
    private int floor;

    @OneToMany(mappedBy = "room")
    private List<Light> lights;

    @OneToOne(mappedBy = "room")
    private Ringer ringer;

    @OneToOne(mappedBy = "room")
    private AutoLight autoLightControl;

    @ManyToOne
    private Building building;

    public Room() {

    }

    public Room(Long id, String name, int floor, AutoLight autoLightControl, List<Light> lights, Ringer ringer, Building building) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.autoLightControl = autoLightControl;
        this.lights = lights;
        this.ringer = ringer;
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

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public List<Light> getLights() {
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }

    public Ringer getRinger() {
        return ringer;
    }

    public void setRinger(Ringer ringer) {
        this.ringer = ringer;
    }

    public AutoLight getAutoLightControl() {
        return autoLightControl;
    }

    public void setAutoLightControl(AutoLight autoLightControl) {
        this.autoLightControl = autoLightControl;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
