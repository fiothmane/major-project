package emse.anass.faircorp.models;

import java.util.List;

public class Room {

    private Long id;

    private String name;

    private int floor;

    private List<Light> lights;

    private Ringer ringer;

    private AutoLight autoLightControl;

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