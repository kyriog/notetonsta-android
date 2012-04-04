package com.supinfo.notetonsta.android.entity;

import java.io.Serializable;

public class ComplexIntervention implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 723816540262005088L;
	private Long id;
	private String name;
	private String description;
	private String dateStart;
	private String dateEnd;
	private int status;
	private Integer evaluationNumber;
	private Double speakerNote;
	private Double slideNote;
	private Double globalNote;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public Integer getEvaluationNumber() {
		return evaluationNumber;
	}
	public void setEvaluationNumber(Integer evaluationNumber) {
		this.evaluationNumber = evaluationNumber;
	}
	public Double getSpeakerNote() {
		return speakerNote;
	}
	public void setSpeakerNote(Double speakerNote) {
		this.speakerNote = speakerNote;
	}
	public Double getSlideNote() {
		return slideNote;
	}
	public void setSlideNote(Double slideNote) {
		this.slideNote = slideNote;
	}
	public Double getGlobalNote() {
		return globalNote;
	}
	public void setGlobalNote(Double globalNote) {
		this.globalNote = globalNote;
	}
}
