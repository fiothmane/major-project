package com.emse.spring.faircorp.DTO;

import com.emse.spring.faircorp.model.AutoLight;
import com.emse.spring.faircorp.model.Status;

public class AutoLightDto {
    private Long id;

    private String sunriseTime;

    private String sunsetTime;

    private String latitude;

    private String longitude;

    private Status autoLightControlState;

    private Status autoThermostatControlState;

    private Long roomId;

    public AutoLightDto() {

    }

    public AutoLightDto(AutoLight autoLight) {
        this.id = autoLight.getId();
        if (autoLight.getSunriseTime() != null) {
            this.sunriseTime = autoLight.getSunriseTime();
        }
        if (autoLight.getSunsetTime() != null) {
            this.sunsetTime = autoLight.getSunsetTime();
        }
        if (autoLight.getLatitude() != null) {
            this.latitude = autoLight.getLatitude();
        }
        if (autoLight.getLongitude() != null) {
            this.longitude = autoLight.getLongitude();
        }
        this.autoLightControlState = autoLight.getAutoLightControlState();
        this.autoThermostatControlState = autoLight.getAutoThermostatControlState();
        if (autoLight.getRoom() != null) {
            this.roomId = autoLight.getRoom().getId();
        }
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

    public Status getAutoThermostatControlState() {
        return autoThermostatControlState;
    }

    public void setAutoThermostatControlState(Status autoThermostatControlState) {
        this.autoThermostatControlState = autoThermostatControlState;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
