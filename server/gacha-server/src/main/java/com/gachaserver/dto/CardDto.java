package com.gachaserver.dto;

/***
 * データベースに対応したカード情報を表すクラス
 * @author gto96
 *
 */
@lombok.Data
public class CardDto {
	/**
	 * ID
	 */
	// private int id;
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
	/**
	 * 期間限定かどうか
	 */
	private int isLimited;
}
