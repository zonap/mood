package com.dsir.tools;

import java.util.Random;

public class StringTools {
	public static String getRandomString(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	public String getUUIDByRules(String rules) {
		int rpoint = 0;
		StringBuffer generateRandStr = new StringBuffer();
		Random rand = new Random();
		int length = 19;
		for (int i = 0; i < length; i++) {
			if (rules != null) {
				rpoint = rules.length();
				int randNum = rand.nextInt(rpoint);
				generateRandStr.append(rules.substring(randNum, randNum + 1));
			}
		}
		return generateRandStr + "" + System.currentTimeMillis();
	}
}