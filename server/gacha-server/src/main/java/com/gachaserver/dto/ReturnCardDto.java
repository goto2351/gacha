package com.gachaserver.dto;

import lombok.AllArgsConstructor;

/**
 * 返り値に使うカード情報のクラス
 * @author gto96
 *
 */
@lombok.Data
@AllArgsConstructor
public class ReturnCardDto {
	/**
	 * レアリティ
	 */
	private String rarity;
	/**
	 * キャラクター名
	 */
	private String name;
	/**
	 * エピソード名
	 */
	private String episode;
}
