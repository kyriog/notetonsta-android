package com.supinfo.notetonsta.android.entity;

import java.io.Serializable;

public class SimpleIntervention implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9002232726758302716L;
	private Long id;
	private String name;
	private String dateStart;
	private String dateEnd;
	private int status;

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
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
