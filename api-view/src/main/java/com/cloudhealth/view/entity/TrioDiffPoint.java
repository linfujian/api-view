package com.cloudhealth.view.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.cloudhealth.view.id.TrioDiffId;

@Entity @IdClass(TrioDiffId.class)
@Table(name="trioDiff")
public class TrioDiffPoint {

	@Id
	private String CHROM; 
	public void setCHROM(String CHROM){this.CHROM = CHROM;}
	public String getCHROM(){return CHROM;}
	
	@Id
	private String POS;
	public void setPOS(String POS){this.POS = POS;}
	public String getPOS(){return POS;}
	
	@Id
	private String REF; 
	public void setREF(String REF){this.REF = REF;}
	public String getREF(){return REF;}
	
	@Id
	private String ALT; 
	public void setALT(String ALT){this.ALT = ALT;}
	public String getALT(){return ALT;}	
	
	private String ChildAF; 
	public void setChildAF(String ChildAF){this.ChildAF = ChildAF;}
	public String getChildAF(){return ChildAF;}	
	
	private String FatherALT; 
	public void setFatherALT(String FatherALT){this.FatherALT = FatherALT;}
	public String getFatherALT(){return FatherALT;}
	
	private String FatherAF; 
	public void setFatherAF(String FatherAF){this.FatherAF = FatherAF;}
	public String getFatherAF(){return FatherAF;}
	
	private String MotherALT; 
	public void setMotherALT(String MotherALT){this.MotherALT = MotherALT;}
	public String getMotherALT(){return MotherALT;}	
	
	private String MotherAF; 
	public void setMotherAF(String MotherAF){this.MotherAF = MotherAF;}
	public String getMotherAF(){return MotherAF;}
	
	@Id
	private String SampleId;
	public void setSampleId(String SampleId) {this.SampleId = SampleId;}
	public String getSampleId() {return SampleId;}
	
	private String FatherId;
	public void setFatherId(String FatherId) {this.FatherId = FatherId;}
	public String getFatherId() {return FatherId;}
	
	private String MotherId;
	public void setMotherId(String MotherId) {this.MotherId = MotherId;}
	public String getMotherId() {return MotherId;}
}
