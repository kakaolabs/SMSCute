package com.dungnh8.smscute.database.table;

import java.io.Serializable;

public class News implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String title;
	private String content;
	private String imageLink;
	private boolean isNew;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String toString() {
		return title;
	}
}