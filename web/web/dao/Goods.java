package web.dao;

import java.io.Serializable;

public class Goods implements Serializable {
	
	private static final long serialVersionUID = 2594174257073442291L;
	
	private int id;
	private String name;
	private float price;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
}
