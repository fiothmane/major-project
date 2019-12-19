package com.emse.spring.faircorp.DAO;

import com.emse.spring.faircorp.model.Light;

import java.util.List;

public interface BuildingDaoCustom {
    public List<Light> findBuildingLights(Long id);
}
