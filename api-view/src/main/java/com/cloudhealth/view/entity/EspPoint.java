package com.cloudhealth.view.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.cloudhealth.view.id.StartToEndPointId;

@Entity @IdClass(StartToEndPointId.class)
public class EspPoint {

	@Id
	private String CHROM; 
	public void setCHROM(String CHROM){this.CHROM = CHROM;}
	public String getCHROM(){return CHROM;}
	
	@Id
	private String START;
	public void setSTART(String START){this.START = START;}
	public String getSTART(){return START;}
	
	private String END; 
	public void setEND(String END){this.END = END;}
	public String getEND(){return END;}
	
	@Id
	private String REF; 
	public void setREF(String REF){this.REF = REF;}
	public String getREF(){return REF;}
	
	@Id
	private String ALT; 
	public void setALT(String ALT){this.ALT = ALT;}
	public String getALT(){return ALT;}
	
	private String AF_ALL; 
	public void setAF_ALL(String AF_ALL){this.AF_ALL = AF_ALL;}
	public String getAF_ALL(){return AF_ALL;}
	
	private String RS; 
	public void setRS(String RS){this.RS = RS;}
	public String getRS(){return RS;}
	
}
