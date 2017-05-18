package com.cloudhealth.view.model;

import com.cloudhealth.view.entity.VarAnnoPoint;

public class VarAnnoReportPoint {

	private  VarAnnoPoint getVarAnnoPointInstance(){
		if(varAnnoPoint == null)
			return new VarAnnoPoint();
		else {
			return varAnnoPoint;
		}
	}

	private String CHROM;
	
	private String START;
	
	private String END;
	
	private String REF;
	
	private String ALT;
	
	private String RS;	
	private String GeneSymbol;
	private String Func;
	private String ExonicFunc;
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
	
	public String getSTART() {
		return START;
	}
	public void setSTART(String START) {
		this.START = START;
	}
	
	public String getEND() {
		return END;
	}
	public void setEND(String END) {
		this.END = END;
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
	
	public String getFunc() {
		return Func;
	}
	public void setFunc(String Func) {
		this.Func = Func;
	}
	
	public String getExonicFunc() {
		return ExonicFunc;
	}
	public void setExonicFunc(String ExonicFunc) {
		this.ExonicFunc = ExonicFunc;
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
		varAnnoPoint.setSTART(this.START);
		varAnnoPoint.setEND(this.END);
		varAnnoPoint.setREF(this.REF);
		varAnnoPoint.setALT(this.ALT);
		varAnnoPoint.setRS(this.RS);
		varAnnoPoint.setGeneSymbol(this.GeneSymbol);
		varAnnoPoint.setFunc(this.Func);
		varAnnoPoint.setExonicFunc(this.ExonicFunc);
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
