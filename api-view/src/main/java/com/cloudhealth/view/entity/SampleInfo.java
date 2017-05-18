package com.cloudhealth.view.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.cloudhealth.view.id.PosPointId;

@Entity @IdClass(PosPointId.class)
public class SampleInfo {

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
	
	
	
}
