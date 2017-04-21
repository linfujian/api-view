package com.cloudhealth.view.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity @IdClass(PosPointId.class)
public class GnoGenomePoint {

	@Id
	private String CHROM; 
	public void setCHROM(String CHROM){this.CHROM = CHROM;}
	public String getCHROM(){return CHROM;}
	
	@Id
	private String POS;
	public void setPOS(String POS){this.POS = POS;}
	public String getPOS(){return POS;}
	
	private String ID; 
	public void setID(String ID){this.ID = ID;}
	public String getID(){return ID;}
	
	@Id
	private String REF; 
	public void setREF(String REF){this.REF = REF;}
	public String getREF(){return REF;}
	
	@Id
	private String ALT; 
	public void setALT(String ALT){this.ALT = ALT;}
	public String getALT(){return ALT;}
	
	private String AF_GNOMAD;
	public void setAF_GNOMAD(String AF_GNOMAD){this.AF_GNOMAD = AF_GNOMAD;}
	public String getAF_GNOMAD(){return AF_GNOMAD;}
	
	private String AF_AFR;
	public void setAF_AFR(String AF_AFR){this.AF_AFR = AF_AFR;}
	public String getAF_AFR(){return AF_AFR;}
	
	private String AF_AMR;
	public void setAF_AMR(String AF_AMR){this.AF_AMR = AF_AMR;}
	public String getAF_AMR(){return AF_AMR;}
	
	private String AF_ASJ;
	public void setAF_ASJ(String AF_ASJ){this.AF_ASJ = AF_ASJ;}
	public String getAF_ASJ(){return AF_ASJ;}
	
	private String AF_EAS;
	public void setAF_EAS(String AF_EAS){this.AF_EAS = AF_EAS;}
	public String getAF_EAS(){return AF_EAS;}
	
	private String AF_FIN;
	public void setAF_FIN(String AF_FIN){this.AF_FIN = AF_FIN;}
	public String getAF_FIN(){return AF_FIN;}
	
	private String AF_NFE;
	public void setAF_NFE(String AF_NFE){this.AF_NFE = AF_NFE;}
	public String getAF_NFE(){return AF_NFE;}
	
	private String AF_OTH;
	public void setAF_OTH(String AF_OTH){this.AF_OTH = AF_OTH;}
	public String getAF_OTH(){return AF_OTH;}
	
	private String AF_Male;
	public void setAF_Male(String AF_Male){this.AF_Male = AF_Male;}
	public String getAF_Male(){return AF_Male;}
	
	private String AF_Female;
	public void setAF_Female(String AF_Female){this.AF_Female = AF_Female;}
	public String getAF_Female(){return AF_Female;}
	
	
	
}
