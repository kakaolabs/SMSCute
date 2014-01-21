package com.dungnh8.smscute.database.table;

import java.io.Serializable;

public class SMS implements Serializable {
	private static final long serialVersionUID = -7089411254136547927L;
	private long id;
	private String content;
	private String searched_content;
	private boolean isFavorited;
	private boolean isUsed;
	private int catelogueID;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSearched_content() {
		return searched_content;
	}

	public void setSearched_content(String searched_content) {
		this.searched_content = searched_content;
	}

	public boolean isFavorited() {
		return isFavorited;
	}

	public void setFavorited(boolean isFavorited) {
		this.isFavorited = isFavorited;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public int getCatelogueID() {
		return catelogueID;
	}

	public void setCatelogueID(int catelogueID) {
		this.catelogueID = catelogueID;
	}

	public String toString() {
		return content;
	}
}