package com.cloudhealth.view.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity @IdClass(PosPointId.class)
public class AFPoint {

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
	
	private String AF_gno_genome; 
	public void setAF_gno_genome(String AF_gno_genome){this.AF_gno_genome = AF_gno_genome;}
	public String getAF_gno_genome(){return AF_gno_genome;}
	
	private String AF_gno_exome; 
	public void setAF_gno_exome(String AF_gno_exome){this.AF_gno_exome = AF_gno_exome;}
	public String getAF_gno_exome(){return AF_gno_exome;}
	
	private String AF_EAS_1kg; 
	public void setAF_EAS_1kg(String AF_EAS_1kg){this.AF_EAS_1kg = AF_EAS_1kg;}
	public String getAF_EAS_1kg(){return AF_EAS_1kg;}
	
	private String AF_ALL_esp; 
	public void setAF_ALL_esp(String AF_ALL_esp){this.AF_ALL_esp = AF_ALL_esp;}
	public String getAF_ALL_esp(){return AF_ALL_esp;}
	
	private String AF_ALL_exac; 
	public void setAF_ALL_exac(String AF_ALL_exac){this.AF_ALL_exac = AF_ALL_exac;}
	public String getAF_ALL_exac(){return AF_ALL_exac;}
	
	private String SIFT_score; 
	public void setSIFT_score(String SIFT_score){this.SIFT_score = SIFT_score;}
	public String getSIFT_score(){return SIFT_score;}
	
	private String Polyphen2_HDIV_score; 
	public void setPolyphen2_HDIV_score(String Polyphen2_HDIV_score){this.Polyphen2_HDIV_score = Polyphen2_HDIV_score;}
	public String getPolyphen2_HDIV_score(){return Polyphen2_HDIV_score;}
	
	private String CLNSIG_clinvar; 
	public void setCLNSIG_clinvar(String CLNSIG_clinvar){this.CLNSIG_clinvar = CLNSIG_clinvar;}
	public String getCLNSIG_clinvar(){return CLNSIG_clinvar;}
	
}
