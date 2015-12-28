package com.example.myselfview.sections.gridview;

import java.io.Serializable;

/**
 * 自己定义的bean的demo
 * 
 * @author 马彦君
 * 
 */
public class MCPicModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String link;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
