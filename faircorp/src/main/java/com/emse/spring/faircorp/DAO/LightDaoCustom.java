package com.emse.spring.faircorp.DAO;

import com.emse.spring.faircorp.model.Light;

import java.util.List;

public interface LightDaoCustom {
    List<Light> findOnLights();
    Light findById(Long id);
}
