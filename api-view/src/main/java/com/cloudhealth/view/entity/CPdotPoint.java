package com.cloudhealth.view.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.cloudhealth.view.id.StartToEndPointId;

@Entity @IdClass(StartToEndPointId.class)
@Table(name="hg38_0base_FuncCPdot")
public class CPdotPoint {

	@Id
	private String CHROM;
	
	@Id
	private String START;
	
	private String END;
	
	@Id
	private String REF;
	
	@Id
	private String ALT;
	
	private String Func;
	
	private String ExonicFunc;
	
	private String Cdot;
	
	private String Pdot;
	
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
	
}
