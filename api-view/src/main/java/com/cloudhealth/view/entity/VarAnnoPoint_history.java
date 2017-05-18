package com.cloudhealth.view.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VarAnnoPoint_history {

	@Id
	private String revision;
	private Date datetime;
	private String action;
	private String Func;
	private String ExonicFunc;
	private String Cdot;
	private String Pdot;
	private String Category;
	private String Comments;
	private String OperUser;
	
	public String getRevision() {
		return revision;
	}
	public void setCHROM(String revision) {
		this.revision = revision;
	}
	
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
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
