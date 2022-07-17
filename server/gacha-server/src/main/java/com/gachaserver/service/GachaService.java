package com.gachaserver.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gachaserver.dao.CardDataDao;
import com.gachaserver.dto.CardDto;
import com.gachaserver.dto.ReturnCardDto;
import com.gachaserver.util.Lottery;

/**
 * ガシャのサービスクラス
 * @author gto96
 *
 */
@Service
public class GachaService {
	//DAO
	@Autowired
	CardDataDao dao;
	
	// カードリスト
	private List<CardDto> rareList;
	private List<CardDto> srareList;
	private List<CardDto> ssrareList;
	//private List<CardDto> allRarityList;
	
	// SSRのピックアップリスト
	Map<Integer, Double>pickUpList;
	
	// レアリティごとの確率
	private final Double PROBABILITY_RARE = 0.82;
	private final Double PROBABILITY_SRARE_NORMAL = 0.12;
	private final Double PROBABILITY_SRARE_LAST = 0.94;
	//private final Double PROBABILITY_SSRARE_NORMAL = 0.03;
	private final Double PROBABILITY_SSRARE_FES = 0.06;
	
	/**
	 * カードリストに対象のカードを登録しておく(先に実行する)
	 * @param 対象のガシャのビュー名
	 */
	public void getCardList(String gachaBox) {
		rareList = dao.findCardsByRarity(gachaBox, "R");
		srareList = dao.findCardsByRarity(gachaBox, "SR");
		ssrareList = dao.findCardsByRarity(gachaBox, "SSR");
	}
	
	/**
	 * ピックアップのリストを作成する
	 */
	public void makepickUpList() {
		pickUpList = new HashMap<Integer, Double>();
		for (int i = 0; i < ssrareList.size(); i++) {
			if (ssrareList.get(i).getIsLimited() == 3) {
				// ノワールフェス限定の時
				if (ssrareList.get(i).getName() == "塩見周子") {
					// 今回のピックアップ用
					pickUpList.put(i, 0.0075);
				} else {
					pickUpList.put(i, 0.00097);
				}
			}
		}
		
	}
	
	/**
	 * R以上を対象とした通常のガシャを行う
	 * @param gachaBox 対象のガシャのビュー名
	 * @param num_lottery 回数
	 * @return 抽選で選ばれたカードのリスト
	 */
	public List<ReturnCardDto> executeNormalGacha(String gachaBox, int num_lottery) {
		// カードリストにデータが入っていなければ読み込む
		if (rareList == null || srareList == null || ssrareList == null) {
			getCardList(gachaBox);
		}
		
		// 確率テーブルを作る(ガシャによって異なる)
		if (pickUpList == null) {
			makepickUpList();
		}
		// SSR
		Lottery lottery = new Lottery();
		List<Double> probList_ssrare = lottery.makeProbabilityTableWithPickUp(ssrareList.size(), pickUpList, PROBABILITY_SSRARE_FES);
		
		//SR(ピックアップなし)
		List<Double> probList_srare = lottery.makeProbabilityTable(srareList.size(), PROBABILITY_SRARE_NORMAL);
		
		// R(ピックアップなし)
		List<Double> probList_rare = lottery.makeProbabilityTable(rareList.size(), PROBABILITY_RARE);
		
		// リストの結合
		List<CardDto> allRarityList = new ArrayList<CardDto>();
		allRarityList.addAll(rareList);
		allRarityList.addAll(srareList);
		allRarityList.addAll(ssrareList);
		
		List<Double> probList = new ArrayList<Double>();
		probList.addAll(probList_rare);
		probList.addAll(probList_srare);
		probList.addAll(probList_ssrare);
		
		// 抽選を行う
		var returnList = new ArrayList<ReturnCardDto>();
		for (int i = 0; i < num_lottery; i++) {
			int selectedIndex = lottery.lottery(probList);
			var selectedCard = allRarityList.get(selectedIndex);
			ReturnCardDto dto = new ReturnCardDto(selectedCard.getRarity(), selectedCard.getName(), selectedCard.getEpisode());
			returnList.add(dto);
		}
		
		return returnList;
	}
	
	/**
	 * SR以上確定ガシャを行う
	 * @param gachaBox 対象のガシャのビュー名
	 * @return 抽選結果のカードデータ
	 */
	public ReturnCardDto executeLastGacha(String gachaBox) {
		// カードリストにデータが入っていなければ読み込む
		if (rareList == null || srareList == null || ssrareList == null) {
			getCardList(gachaBox);
		}

		// 確率テーブルを作る(ガシャによって異なる)
		if (pickUpList == null) {
			makepickUpList();
		}
		
		// 確率テーブルを作る
		Lottery lottery = new Lottery();
		// SSR
		List<Double> probList_ssrare = lottery.makeProbabilityTableWithPickUp(ssrareList.size(), pickUpList, PROBABILITY_SSRARE_FES);
		// SR
		List<Double> probList_srare = lottery.makeProbabilityTable(srareList.size(), PROBABILITY_SRARE_LAST);
		
		// リストの結合
		List<CardDto> allRarityList = new ArrayList<CardDto>();
		allRarityList.addAll(srareList);
		allRarityList.addAll(ssrareList);
		
		List<Double> probList = new ArrayList<Double>();
		probList.addAll(probList_srare);
		probList.addAll(probList_ssrare);
		
		// 抽選を行う
		int selectedIndex = lottery.lottery(probList);
		var selectedCard = allRarityList.get(selectedIndex);
		
		return new ReturnCardDto(selectedCard.getRarity(), selectedCard.getName(), selectedCard.getEpisode());
	}
	
	
}
