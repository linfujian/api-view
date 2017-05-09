package com.cloudhealth.view.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity @IdClass(PosPointId.class)
public class VarAnnoReportPoint {

	private  VarAnnoPoint getVarAnnoPointInstance(){
		if(varAnnoPoint == null)
			return new VarAnnoPoint();
		else {
			return varAnnoPoint;
		}
	}
	@Id
	private String CHROM;
	
	@Id
	private String POS;
	
	@Id
	private String REF;
	
	@Id
	private String ALT;
	
	private String RS;	
	private String GeneSymbol;
	private String Cdot;
	private String Pdot;
	private String Category;
	private String OperUser;
	private String Comments;
	
	private static VarAnnoPoint varAnnoPoint;
	private String report;
	
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
	
	public String getRS() {
		return RS;
	}
	public void setRS(String RS) {
		this.RS = RS;
	}
	
	public String getGeneSymbol() {
		return GeneSymbol;
	}
	public void setGeneSymbol(String GeneSymbol) {
		this.GeneSymbol = GeneSymbol;
	}
	
	public String getCdot() {
		return Cdot;
	}
	public void setCdot(String Cdot) {
		this.Cdot = Cdot;
	}
	
	public String getPdot() {
		return Pdot;
	}
	public void setPdot(String Pdot) {
		this.Pdot = Pdot;
	}
	
	public String getCategory() {
		return Category;
	}
	public void setCategory(String Category) {
		this.Category = Category;
	}
	
	public String getOperUser() {
		return OperUser;
	}
	public void setOperUser(String OperUser) {
		this.OperUser = OperUser;
	}
	
	public String getComments() {
		return Comments;
	}
	public void setComments(String Comments) {
		this.Comments = Comments;
	}
	
	
	public VarAnnoPoint getVarAnnoPoint(){
		varAnnoPoint = getVarAnnoPointInstance();
		varAnnoPoint.setCHROM(this.CHROM);
		varAnnoPoint.setPOS(this.POS);
		varAnnoPoint.setREF(this.REF);
		varAnnoPoint.setALT(this.ALT);
		varAnnoPoint.setRS(this.RS);
		varAnnoPoint.setGeneSymbol(this.GeneSymbol);
		varAnnoPoint.setCdot(this.Cdot);
		varAnnoPoint.setPdot(this.Pdot);
		varAnnoPoint.setCategory(this.Category);
		varAnnoPoint.setOperUser(this.OperUser);
		varAnnoPoint.setComments(this.Comments);
		return varAnnoPoint;
		
	}
	
	//if report?
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	
}
