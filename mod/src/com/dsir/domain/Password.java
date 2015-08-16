package com.dsir.domain;

public class Password {
	private String tips;
	private int tipsId;

	public Password(String tips, int tipsId) {
		this.tips = tips;
		this.tipsId = tipsId;
	}

	public String getTips() {
		return this.tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public int getTipsId() {
		return this.tipsId;
	}

	public void setTipsId(int tipsId) {
		this.tipsId = tipsId;
	}
}