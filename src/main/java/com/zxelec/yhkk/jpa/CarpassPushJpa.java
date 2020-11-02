package com.zxelec.yhkk.jpa;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zxelec.yhkk.po.CarpassPushPO;

public interface CarpassPushJpa extends JpaRepository<CarpassPushPO,Long>{

	List<CarpassPushPO> findAll(Specification<CarpassPushPO> specification);

}
