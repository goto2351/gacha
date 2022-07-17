package com.gachaserver.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gachaserver.dto.ReturnCardDto;
import com.gachaserver.dto.ReturnMessage;
import com.gachaserver.service.GachaService;

@RestController
@RequestMapping("gacha-api/")
public class GachaController {
	@Autowired
	private GachaService service;
	
	private final String CURRENT_GACHA_BOX = "gachaBox_noir_fes_202207";
	/**
	 * 単発ガシャを行う
	 * @return 選ばれたカード情報を含むjsonレスポンス
	 */
	@GetMapping("/1gacha")
	public ReturnMessage normalGacha() {
		System.out.println("Request: 単発ガシャ");
		// 返答用のオブジェクト
		ReturnMessage response = new ReturnMessage();
		
		List<ReturnCardDto> selectedCard = service.executeNormalGacha(CURRENT_GACHA_BOX, 1);
		
		// レスポンスを作る
		response.setMessage("OK");
		response.setReturnCardList(selectedCard);
		
		return response;
	}
	
	/**
	 * 10連ガシャを行う
	 * @return 選ばれたカード情報を含むjsonレスポンス
	 */
	@GetMapping("/10gacha")
	public ReturnMessage tenTimesGacha() {
		System.out.println("Request: 10連ガシャ");
		// 返答用のオブジェクト
		ReturnMessage response = new ReturnMessage();
		
		// ガシャを行う
		List<ReturnCardDto> selectedCards = new ArrayList<ReturnCardDto>();
		// 通常ガシャ
		List<ReturnCardDto> res_normalGacha = service.executeNormalGacha(CURRENT_GACHA_BOX, 9);
		// SR以上確定ガシャ
		ReturnCardDto res_lastGacha = service.executeLastGacha(CURRENT_GACHA_BOX);
		// リストを結合する
		selectedCards.addAll(res_normalGacha);
		selectedCards.add(res_lastGacha);
		
		// レスポンスを作成する
		response.setMessage("OK");
		response.setReturnCardList(selectedCards);
		
		return response;
	}
	
	@ExceptionHandler(Exception.class) 
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ReturnMessage errorHandler() {
		// 返答用のオブジェクト
		ReturnMessage response = new ReturnMessage();
		response.setMessage("internal server error");
		response.setReturnCardList(null);
		
		return response;
	}
}
