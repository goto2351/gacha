package com.gachaserver.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 抽選に関連するユーティリティクラス
 * @author gto96
 *
 */
public class Lottery {
	
	/**
	 * ピックアップされたカードの確率を指定されたものにして確率リストを作る
	 * @param num_elements 要素数
	 * @param pickUpList ピックアップするカードのインデックスと確率
	 * @param 確率の合計値
	 * @return 確率リスト
	 */
	public List<Double> makeProbabilityTableWithPickUp(int num_elements, Map<Integer, Double> pickUpList, Double sum_probability) {
		List<Double> res = new ArrayList<Double>();
		// 初期化
		for (int i = 0; i < num_elements; i++) {
			res.add(0.0);
		}
		
		// ピックアップの確率を登録する
		pickUpList.forEach((index, prob) -> res.set(index, prob));
		// ピックアップ分の確率の合計値
		Double sum_probability_pickup = res.stream().mapToDouble(elem -> elem).sum();
		
		// 残りの確率を配分する
		Double probability_residual = (sum_probability - sum_probability_pickup) / (num_elements - pickUpList.size());
		for (int i = 0; i < num_elements; i++) {
			if (!pickUpList.containsKey(i)) {
				res.set(i, probability_residual);
			}
		}
		
		return res;
	}
	
	/**
	 * ピックアップのない場合の確率テーブルを作る
	 * @param num_element 要素数
	 * @param sum_probability そのリストの確率の合計値
	 * @return
	 */
	public List<Double> makeProbabilityTable(int num_element, Double sum_probability) {
		List<Double> res = new ArrayList<Double>();
		
		Double probability = sum_probability / num_element;
		for (int i = 0; i < num_element; i++) {
			res.add(probability);
		}
		
		return res;
	}
	
	/**
	 * 指定された要素ごとの確率で抽選を行う
	 * @param probability_table 各要素の確率を表すリスト
	 * @return リストから選ばれたインデックス
	 */
	public  int lottery(List<Double> probability_table) {
		// 確率の和が1であることを確かめる
		if (Math.abs(probability_table.stream().reduce((accum, value) -> accum + value).get() - 1.0) > 0.01) {
			return -1;
		}
		
		// 確率テーブルの各要素までの確率の和を求める
		List<Double> probability_sum = new ArrayList<>();
		probability_sum.add(probability_table.get(0));
		for (int i = 1; i < probability_table.size(); i++) {
			probability_sum.add(probability_sum.get(i - 1) + probability_table.get(i));
		}
		
		// 0-1の乱数を求め、その数が含まれる区間のインデックスを返す
		Double rnd_num = Math.random();
		for (int i = 0; i < probability_sum.size(); i++) {
			if (rnd_num <= probability_sum.get(i)) {
				return i;
			}
		}
		
		// 不具合のとき
		return -1;
	}
}
