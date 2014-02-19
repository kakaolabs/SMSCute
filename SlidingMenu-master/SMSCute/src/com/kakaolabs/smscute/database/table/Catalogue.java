package com.kakaolabs.smscute.database.table;

import java.io.Serializable;

public class Catalogue implements Serializable {

	private static final long serialVersionUID = 2843989281309019618L;

	private long id;
	private int catelogueID; // id in server
	private String name;
	private String searched_name;
	private long parentCatalogueID;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCatelogueID() {
		return catelogueID;
	}

	public void setCatelogueID(int catelogueID) {
		this.catelogueID = catelogueID;
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

	public long getParentCatalogueID() {
		return parentCatalogueID;
	}

	public void setParentCatalogueID(long parentCatalogueID) {
		this.parentCatalogueID = parentCatalogueID;
	}

	public String toString() {
		return id + " : " + catelogueID + " : " + name + " : " + searched_name
				+ " : " + parentCatalogueID;
	}
}