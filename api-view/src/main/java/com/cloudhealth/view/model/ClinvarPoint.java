package com.cloudhealth.view.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity @IdClass(StartToEndPointId.class)
@Table(name="clinvar")
public class ClinvarPoint {
	
	@Id
	private String CHROM; 
	public void setCHROM(String CHROM){this.CHROM = CHROM;}
	public String getCHROM(){return CHROM;}
	
	@Id
	@Column(name ="START_POINT")
	private String START;
	public void setSTART(String START){this.START = START;}
	public String getSTART(){return START;}
	
	@Column(name ="END_POINT")
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
	
	private String CLNSIG; 
	public void setCLNSIG(String CLNSIG){this.CLNSIG = CLNSIG;}
	public String getCLNSIG(){return CLNSIG;}
	
	private String CLNDBN; 
	public void setCLNDBN(String CLNDBN){this.CLNDBN = CLNDBN;}
	public String getCLNDBN(){return CLNDBN;}
	
	private String CLNACC; 
	public void setCLNACC(String CLNACC){this.CLNACC = CLNACC;}
	public String getCLNACC(){return CLNACC;}
	
	private String CLNDSDB; 
	public void setCLNDSDB(String CLNDSDB){this.CLNDSDB = CLNDSDB;}
	public String getCLNDSDB(){return CLNDSDB;}
	
	private String CLNDSDBID; 
	public void setCLNDSDBID(String CLNDSDBID){this.CLNDSDBID = CLNDSDBID;}
	public String getCLNDSDBID(){return CLNDSDBID;}

}
