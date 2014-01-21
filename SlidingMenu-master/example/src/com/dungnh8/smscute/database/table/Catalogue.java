package com.dungnh8.smscute.database.table;

import java.io.Serializable;

public class Catalogue implements Serializable {

	private static final long serialVersionUID = 2843989281309019618L;

	private long id;
	private String name;
	private String searched_name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSearched_name() {
		return searched_name;
	}

	public void setSearched_name(String searched_name) {
		this.searched_name = searched_name;
	}

	public String toString() {
		return name;
	}
}