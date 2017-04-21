package com.cloudhealth.view.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity @IdClass(OnekgId.class)
public class OnekgPoint {

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
	private String OBS; 
	public void setOBS(String OBS){this.OBS = OBS;}
	public String getOBS(){return OBS;}
	
	private String AF_EAS; 
	public void setAF_EAS(String AF_EAS){this.AF_EAS = AF_EAS;}
	public String getAF_EAS(){return AF_EAS;}
	
	private String RS; 
	public void setRS(String RS){this.RS = RS;}
	public String getRS(){return RS;}
}
