package com.cloudhealth.view.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.cloudhealth.view.id.StartToEndPointId;

@Entity @IdClass(StartToEndPointId.class)
@Table(name="vcfanno")
public class VarAnnoPoint {

	@Id
	private String CHROM;
	
	@Id
	private String START;
	
	private String END;
	
	@Id
	private String REF;
	
	@Id
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
}
