package com.zxelec.yhkk.jpa;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.zxelec.yhkk.po.CarpassPushPO;
import com.zxelec.yhkk.po.TImgErrorMsgPO;

public interface TImgErrorMsgJpa extends JpaRepository<TImgErrorMsgPO,String>{

	List<TImgErrorMsgPO> findAll(Specification<TImgErrorMsgPO> specification);
	@Modifying
	@Transactional
	@Query(nativeQuery = true,value = "delete from t_img_error_msg where motor_vehicle_id =: ")
	void delTimgErrorMsg(@Param("motorVehicleId") String motorVehicleId);
}
