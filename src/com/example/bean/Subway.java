package com.example.bean;

/**
 * Create by 张瀛煜 On 2020年10月1日 下午5:33:50
 */

public class Subway {
	private String name;
	private int diatance;

	public Subway(String name, int diatance) {
		super();
		this.name = name;
		this.diatance = diatance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDiatance() {
		return diatance;
	}

	public void setDiatance(int diatance) {
		this.diatance = diatance;
	}

}
