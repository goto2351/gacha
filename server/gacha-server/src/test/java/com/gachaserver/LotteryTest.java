package com.gachaserver;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.gachaserver.util.Lottery;

class LotteryTest {

	@Test
	void testMakeProbabilityTableWithPickUp() {
		Lottery lottery = new Lottery();
		
		var pickUpProb = new HashMap<Integer, Double>();
		for (int i = 0; i < 19; i++) {
			if (i > 0) {
				pickUpProb.put(i, 0.00097);
			} else {
				pickUpProb.put(i, 0.0075);
			}
		}
		
		var table = lottery.makeProbabilityTableWithPickUp(306, pickUpProb, 0.06);
		for (int i = 0; i < table.size(); i++) {
			System.out.println(table.get(i));
		}
	}

	// TODO: lotteryの方はこれからテストを作る

}
