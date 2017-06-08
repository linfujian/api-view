package com.cloudhealth.view.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.cloudhealth.view.id.PosPointId;

@Entity @IdClass(PosPointId.class)
public class ThreeSamplePoint {

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
	
	private String ChildAF; 
	public void setChildAF(String ChildAF){this.ChildAF = ChildAF;}
	public String getChildAF(){return ChildAF;}	
	
	private String FatherALT; 
	public void setFatherALT(String FatherALT){this.FatherALT = FatherALT;}
	public String getFatherALT(){return FatherALT;}
	
	private String FatherAF; 
	public void setFatherAF(String FatherAF){this.FatherAF = FatherAF;}
	public String getFatherAF(){return FatherAF;}
	
	private String MotherALT; 
	public void setMotherALT(String MotherALT){this.MotherALT = MotherALT;}
	public String getMotherALT(){return MotherALT;}	
	
	private String MotherAF; 
	public void setMotherAF(String MotherAF){this.MotherAF = MotherAF;}
	public String getMotherAF(){return MotherAF;}
	
	private String Symbol;
	public void setSymbol(String Symbol) {this.Symbol = Symbol;}
	public String getSymbol() {return Symbol;}
	
	private String RS;
	public void setRS(String RS) {this.RS = RS;}
	public String getRS() {return RS;}
	
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
	
	//2017.5.15 add
	private String CLASS;
	public void setCLASS(String CLASS) {this.CLASS = CLASS;}
	public String getCLASS(){return CLASS;}
	
	//2017.5.4 add
	private String Category;
	public void setCategory(String Category) {this.Category = Category;}
	public String getCategory() {return Category;}
	
	private String Comments;
	public void setComments(String Comments) {this.Comments = Comments;}
	public String getComments() {return Comments;}
	
	private String REPORT;
	public void setREPORT(String REPORT) {this.REPORT = REPORT;}
	public String getREPORT() {return REPORT;}
}
