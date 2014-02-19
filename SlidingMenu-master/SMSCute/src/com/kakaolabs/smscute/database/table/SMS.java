package com.kakaolabs.smscute.database.table;

import java.io.Serializable;

public class SMS implements Serializable {
	private static final long serialVersionUID = -7089411254136547927L;
	private long id;
	private long smsId;
	private String content;
	private String searchedContent;
	private int votes;
	private int index;
	private boolean isFavorited;
	private boolean isUsed;
	private int catalogueID;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSmsId() {
		return smsId;
	}

	public void setSmsId(long smsId) {
		this.smsId = smsId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSearchedContent() {
		return searchedContent;
	}

	public void setSearchedContent(String searchedContent) {
		this.searchedContent = searchedContent;
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

	public int getCatalogueID() {
		return catalogueID;
	}

	public void setCatalogueID(int catalogueID) {
		this.catalogueID = catalogueID;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String toString() {
		return id + " : " + content + " : " + votes + " : " + index + " : "
				+ isFavorited;
	}
}