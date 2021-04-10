package com.example.user.model;

/**
 * 驗證碼亂數用
 * @author user1
 *
 */
public class Random {

	private Integer random;

	private Float randomF;
	
	private Integer randomP;

	public Integer getRandom() {
		int r = (int) (Math.random() * 10);
		return r;
	}

	public Float getRandomF() {

		float r = (float) (Math.random() * 80);
		return r;

	}
	
	public Integer getRandomP() {
		int r = (int) (Math.random() * 18000+2000);
		return r;
	}

}
