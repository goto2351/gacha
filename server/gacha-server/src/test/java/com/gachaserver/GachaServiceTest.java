package com.gachaserver;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gachaserver.dto.ReturnCardDto;
import com.gachaserver.service.GachaService;

@SpringBootTest
class GachaServiceTest {

	@Autowired
	private  GachaService service;
	
	@Test
	@DisplayName("正常系: 通常のガシャを9回引ける")
	void testExecuteNormalGacha() {
		List<ReturnCardDto> res = service.executeNormalGacha("gachaBox_noir_fes_202207", 9);
		System.out.println("通常ガシャ:");
		System.out.println(res);
		assertEquals(9, res.size());
		//fail("まだ実装されていません");
	}

	@Test
	@DisplayName("正常系: SSレア以上確定ガシャを1回引ける")
	void testExecuteLastGacha() {
		ReturnCardDto res = service.executeLastGacha("gachaBox_noir_fes_202207");
		System.out.println("Sレア以上確定ガシャ:");
		System.out.println(res);
		assertTrue(res != null);
	}
	
	@Test
	@DisplayName("正常系: 10連ガシャを行える")
	void test10Gacha() {
		List<ReturnCardDto> res_normal = service.executeNormalGacha("gachaBox_noir_fes_202207", 9);
		ReturnCardDto res_SRorSSR = service.executeLastGacha("gachaBox_noir_fes_202207");
		List<ReturnCardDto> res = new ArrayList<ReturnCardDto>();
		res.addAll(res_normal);
		res.add(res_SRorSSR);
		System.out.println("10連ガシャ:");
		for (int i = 0; i < res.size(); i++) {
			System.out.println(res.get(i));
		}
		assertEquals(10, res.size());
	}

}
