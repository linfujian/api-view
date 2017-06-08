package com.cloudhealth.view.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.cloudhealth.view.id.PosPointId;

@Entity @IdClass(PosPointId.class)
public class HgmdVarAnnoPoint {

	@Id
	private String CHROM;
	@Id
	private String POS;
	@Id
	private String REF;
	@Id
	private String ALT;
	
	private String CLASS;
	
	private String Category;
	
	public String getCHROM() {return CHROM;}
	public void setCHROM(String CHROM) {this.CHROM = CHROM;}
	
	public void setPOS(String POS){this.POS = POS;}
	public String getPOS(){return POS;}
	
	public void setREF(String REF){this.REF = REF;}
	public String getREF(){return REF;}
	
	public void setALT(String ALT){this.ALT = ALT;}
	public String getALT(){return ALT;}	
	
	public void setCLASS(String CLASS) {this.CLASS = CLASS;}
	public String getCLASS(){return CLASS;}
	
	public void setCategory(String Category) {this.Category = Category;}
	public String getCategory() {return Category;}
	
}
