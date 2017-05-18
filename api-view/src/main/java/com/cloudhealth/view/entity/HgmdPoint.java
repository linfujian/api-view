package com.cloudhealth.view.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.cloudhealth.view.id.PosPointId;

@Entity @IdClass(PosPointId.class)
@Table(name="hg38_hgmd")
public class HgmdPoint {
	
	@Id
	private String CHROM;
	
	@Id
	private String POS;
	
	@Id
	private String REF;
	
	@Id
	private String ALT;
	
	private String CLASS;
	private String GENE;
	private String DNA;
	private String PROT;
	private String PHEN;
	
	public String getCHROM() {
		return CHROM;
	}
	public void setCHROM(String CHROM) {
		this.CHROM = CHROM;
	}
	
	public String getPOS() {
		return POS;
	}
	public void setPOS(String POS) {
		this.POS = POS;
	}
	
	public String getREF() {
		return REF;
	}
	public void setREF(String REF) {
		this.REF = REF;
	}
	
	public String getALT() {
		return ALT;
	}
	public void setALT(String ALT) {
		this.ALT = ALT;
	}
	
	public String getCLASS() {
		return CLASS;
	}
	public void setCLASS(String CLASS) {
		this.CLASS = CLASS;
	}
	
	public String getGENE() {
		return GENE;
	}
	public void setGENE(String GENE) {
		this.GENE = GENE;
	}
	
	public String getDNA() {
		return DNA;
	}
	public void setDNA(String DNA) {
		this.DNA = DNA;
	}
	
	public String getPROT() {
		return PROT;
	}
	public void setPROT(String PROT) {
		this.PROT = PROT;
	}
	
	public String getPHEN() {
		return PHEN;
	}
	public void setPHEN(String PHEN) {
		this.PHEN = PHEN;
	}
}
