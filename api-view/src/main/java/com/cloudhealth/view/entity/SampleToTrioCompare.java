package com.cloudhealth.view.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.cloudhealth.view.id.PosPointId;

@Entity @IdClass(PosPointId.class)
public class SampleToTrioCompare {
	
	public SampleToTrioCompare() {
		
	}
	
	public SampleToTrioCompare(String CHROM,String POS,
			String REF,String ALT, String AF,String ChildALT,String ChildAF) {
		this.CHROM = CHROM;
		this.POS = POS;
		this.REF = REF;
		this.ALT = ALT;
		this.AF = AF;
		this.ChildALT = ChildALT;
		this.ChildAF = ChildAF;
	}
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
	
	public String AF;
	public void setAF(String AF){this.AF = AF;}
	public String getAF(){return AF;}
	
	@Column(nullable=true)
	public String ChildALT;
	public void setChildALT(String ChildALT){this.ChildALT = ChildALT;}
	public String getChildALT(){return ChildALT;}
	
	@Column(nullable=true)
	public String ChildAF;
	public void setChildAF(String ChildAF){this.ChildAF = ChildAF;}
	public String getChildAF(){return ChildAF;}
}
