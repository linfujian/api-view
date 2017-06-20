package com.cloudhealth.view.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TrioDiffGroup {

	@Id
	private String SampleId;
	private String FatherId;
	private String MotherId;
	
	public void setSampleId(String sample) {
		this.SampleId = sample;
	}
	public String getSampleId() {
		return SampleId;
	}
	
	public void setFatherId(String father) {
		this.FatherId = father;
	}
	public String getFatherId() {
		return FatherId;
	}
	
	public void setMotherId(String mother) {
		this.MotherId = mother;
	}
	public String getMotherId() {
		return MotherId;
	}
}
