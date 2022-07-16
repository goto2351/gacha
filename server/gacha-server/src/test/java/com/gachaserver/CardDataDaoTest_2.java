package com.gachaserver;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gachaserver.dao.CardDataDao;
import com.gachaserver.dto.CardDto;

@SpringBootTest
class CardDataDaoTest_2 {
	@Autowired
	CardDataDao dao;
	
	@Test
	@DisplayName("正常系: SSRのリストが正しく取得できること")
	void testFindCardsByRarity1() {
		List<CardDto> res = dao.findCardsByRarity("gachaBox_noir_fes_202207", "SSR");
		System.out.print("SSRtest->");
		System.out.print("取得件数:" + res.size());
		System.out.println(res.get(0));
		assertEquals(306, res.size());
	}
	
	@Test
	@DisplayName("正常系: SRのリストが正しく取得できること")
	void testFindCardsByRarity2() {
		List<CardDto> res = dao.findCardsByRarity("gachaBox_noir_fes_202207", "SR");
		System.out.print("SRtest->");
		System.out.print("取得件数:" + res.size());
		System.out.println(res.get(0));
		assertEquals(293, res.size());
	}
	
	@Test
	@DisplayName("正常系: Rのリストが正しく取得できること")
	void testFindCardsByRarity3() {
		List<CardDto> res = dao.findCardsByRarity("gachaBox_noir_fes_202207", "R");
		System.out.print("Rtest->");
		System.out.print("取得件数:" + res.size());
		System.out.println(res.get(0));
		assertEquals(103, res.size());
	}
	
	@Test
	@DisplayName("準正常系: 対象に存在しないレアリティのカードを指定したとき0件となって返ってくること")
	void testFindCardsByRarity4() {
		List<CardDto> res = dao.findCardsByRarity("gachaBox_noir_fes_202207", "N");
		assertEquals(0, res.size());
	}
}
