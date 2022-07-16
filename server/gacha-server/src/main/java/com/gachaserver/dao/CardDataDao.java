package com.gachaserver.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gachaserver.dto.CardDto;

/**
 * カード情報をデータベースから入手するクラス
 * @author gto96
 *
 */
@Repository
public class CardDataDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 指定されたレアリティのカードのリストを取得する(使わないかも)
	 * @param gachaBox ガシャに対応するビューの名前
	 * @param rarity レアリティ(R, SR, SSR)
	 * @return 現在開催中のガシャで対象となる指定したレアリティのカードのリスト
	 */
	public List<CardDto> findCardsByRarity(String gachaBox , String rarity) {
		//String sql = "SELECT * FROM CardList WHERE rarity = '?'";
		String sql = String.format("SELECT rarity, name, episode, isLimited FROM %s WHERE rarity = ?", gachaBox);
		List<CardDto> res = jdbcTemplate.query(sql, new BeanPropertyRowMapper<CardDto>(CardDto.class), rarity);
		
		return res;
	}
}
