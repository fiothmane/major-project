package com.emse.spring.faircorp.DAO;

import com.emse.spring.faircorp.model.AutoLight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoLightDao extends JpaRepository<AutoLight, String>, AutoLightDaoCustom {
}
