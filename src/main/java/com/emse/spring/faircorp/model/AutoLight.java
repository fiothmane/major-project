package com.emse.spring.faircorp.model;

import javax.persistence.*;

@Entity
public class AutoLight {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String sunriseTime;

    @Column
    private String sunsetTime;

    @Column
    private String latitude;

    @Column
    private String longitude;

    @Enumerated(EnumType.STRING)
    private Status autoLightControlState;

    @OneToOne
    private Room room;

    public AutoLight() {

    }

    public AutoLight(Long id, String sunriseTime, String sunsetTime, String latitude, String longitude, Status autoLightControlState, Room room) {
        this.id = id;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.autoLightControlState = autoLightControlState;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(String sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public String getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(String sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Status getAutoLightControlState() {
        return autoLightControlState;
    }

    public void setAutoLightControlState(Status autoLightControlState) {
        this.autoLightControlState = autoLightControlState;
    }

    public com.emse.spring.faircorp.model.Room getRoom() {
        return room;
    }

    public void setRoom(com.emse.spring.faircorp.model.Room room) {
        room = room;
    }
}
