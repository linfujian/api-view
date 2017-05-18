package com.cloudhealth.view.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.cloudhealth.view.id.StartToEndPointId;

@Entity @IdClass(StartToEndPointId.class)
public class ExacPoint {

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
	
	private String EXAC_ALL; 
	public void setEXAC_ALL(String EXAC_ALL){this.EXAC_ALL = EXAC_ALL;}
	public String getEXAC_ALL(){return EXAC_ALL;}
	
	private String EXAC_AFR; 
	public void setEXAC_AFR(String EXAC_AFR){this.EXAC_AFR = EXAC_AFR;}
	public String getEXAC_AFR(){return EXAC_AFR;}
	
	private String EXAC_AMR; 
	public void setEXAC_AMR(String EXAC_AMR){this.EXAC_AMR = EXAC_AMR;}
	public String getEXAC_AMR(){return EXAC_AMR;}
	
	private String EXAC_EAS; 
	public void setEXAC_EAS(String EXAC_EAS){this.EXAC_EAS = EXAC_EAS;}
	public String getEXAC_EAS(){return EXAC_EAS;}
	
	private String EXAC_FIN; 
	public void setEXAC_FIN(String EXAC_FIN){this.EXAC_FIN = EXAC_FIN;}
	public String getEXAC_FIN(){return EXAC_FIN;}
	
	private String EXAC_NFE; 
	public void setEXAC_NFE(String EXAC_NFE){this.EXAC_NFE = EXAC_NFE;}
	public String getEXAC_NFE(){return EXAC_NFE;}
	
	private String EXAC_OTH; 
	public void setEXAC_OTH(String EXAC_OTH){this.EXAC_OTH = EXAC_OTH;}
	public String getEXAC_OTH(){return EXAC_OTH;}
	
	private String EXAC_SAS; 
	public void setEXAC_SAS(String EXAC_SAS){this.EXAC_SAS = EXAC_SAS;}
	public String getEXAC_SAS(){return EXAC_SAS;}
	
}
