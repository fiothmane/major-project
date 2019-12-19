package com.emse.spring.faircorp.DAO;

import com.emse.spring.faircorp.model.Light;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingDao extends JpaRepository<Light, String>, BuildingDaoCustom {
}
