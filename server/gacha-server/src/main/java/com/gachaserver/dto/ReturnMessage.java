package com.gachaserver.dto;

import java.util.List;

/**
 * APIの返答を表すデータクラス
 * @author gto96
 *
 */
@lombok.Data
public class ReturnMessage {
	/**
	 * メッセージ
	 */
	private String message;
	
	/**
	 * ガチャ結果のカードリスト
	 */
	private List<ReturnCardDto> returnCardList;

}
