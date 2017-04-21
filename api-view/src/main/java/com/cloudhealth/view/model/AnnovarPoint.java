package com.cloudhealth.view.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity @IdClass(StartToEndPointId.class)
public class AnnovarPoint {

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
	
	private String SIFT_score; 
	public void setSIFT_score(String SIFT_score){this.SIFT_score = SIFT_score;}
	public String getSIFT_score(){return SIFT_score;}
	
	private String SIFT_pred; 
	public void setSIFT_pred(String SIFT_pred){this.SIFT_pred = SIFT_pred;}
	public String getSIFT_pred(){return SIFT_pred;}
	
	private String Polyphen2_HDIV_score; 
	public void setPolyphen2_HDIV_score(String Polyphen2_HDIV_score){this.Polyphen2_HDIV_score = Polyphen2_HDIV_score;}
	public String getPolyphen2_HDIV_score(){return Polyphen2_HDIV_score;}
	
	private String Polyphen2_HDIV_pred; 
	public void setPolyphen2_HDIV_pred(String Polyphen2_HDIV_pred){this.Polyphen2_HDIV_pred = Polyphen2_HDIV_pred;}
	public String getPolyphen2_HDIV_pred(){return Polyphen2_HDIV_pred;}
	
	private String Polyphen2_HVAR_score; 
	public void setPolyphen2_HVAR_score(String Polyphen2_HVAR_score){this.Polyphen2_HVAR_score = Polyphen2_HVAR_score;}
	public String getPolyphen2_HVAR_score(){return Polyphen2_HVAR_score;}
	
	private String Polyphen2_HVAR_pred; 
	public void setPolyphen2_HVAR_pred(String Polyphen2_HVAR_pred){this.Polyphen2_HVAR_pred = Polyphen2_HVAR_pred;}
	public String getPolyphen2_HVAR_pred(){return Polyphen2_HVAR_pred;}
}
