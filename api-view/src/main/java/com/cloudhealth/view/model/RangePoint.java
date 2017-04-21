package com.cloudhealth.view.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RangePoint {

	@Id
	private String acc;
	
	private String chr_name;
	private String tx_start;
	private String tx_end;
	
	
	public String getAcc() {
		return acc;
	}
	public void setAcc(String acc) {
		this.acc = acc;
	}
	
	public String getChr_name() {
		return chr_name;
	}
	public void setChr_name(String chr) {
		this.chr_name = chr;
	}
	
	public String getTx_start() {
		return tx_start;
	}
	public void setTx_start(String start) {
		this.tx_start = start;
	}
	
	public String getTx_end() {
		return tx_end;
	}
	public void setTx_end(String end) {
		this.tx_end = end;
	}
}
