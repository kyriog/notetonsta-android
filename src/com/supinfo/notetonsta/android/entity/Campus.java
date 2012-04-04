package com.supinfo.notetonsta.android.entity;

import java.io.Serializable;

public class Campus implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1064737664879477141L;
	private Long id;
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}
