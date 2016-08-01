package com.wpits.mwalletsamba;

import java.io.Serializable;

public class Transferobject implements Serializable {
	String frommobile;
	String tomobile;
	String amount;
	String remark;
	String transfertime;
	String transfertype;
	String transferaccount;



	public Transferobject(String frommobile, String tomobile, String amount,
			String remark, String transfertime, String transfertype,
			String transferaccount) {
		super();
		this.frommobile = frommobile;
		this.tomobile = tomobile;
		this.amount = amount;
		this.remark = remark;
		this.transfertime = transfertime;
		this.transfertype = transfertype;
		this.transferaccount = transferaccount;
	}
	public String getFrommobile() {
		return frommobile;
	}
	public void setFrommobile(String frommobile) {
		this.frommobile = frommobile;
	}
	public String getTomobile() {
		return tomobile;
	}
	public void setTomobile(String tomobile) {
		this.tomobile = tomobile;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTransfertime() {
		return transfertime;
	}
	public void setTransfertime(String transfertime) {
		this.transfertime = transfertime;
	}
	public String getTransfertype() {
		return transfertype;
	}
	public void setTransfertype(String transfertype) {
		this.transfertype = transfertype;
	}
	public String getTransferaccount() {
		return transferaccount;
	}
	public void setTransferaccount(String transferaccount) {
		this.transferaccount = transferaccount;
	}


}
