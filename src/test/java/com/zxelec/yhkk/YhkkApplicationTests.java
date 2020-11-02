package com.zxelec.yhkk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zxelec.yhkk.jpa.CarpassPushJpa;
import com.zxelec.yhkk.po.CarpassPushPO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YhkkApplicationTests {
	
	@Autowired
	private CarpassPushJpa carpassPushJpa;
	@Test
	public void contextLoads() {
		CarpassPushPO po = new CarpassPushPO();
		po.setDeviceID("123");
		carpassPushJpa.save(po);
	}

}
