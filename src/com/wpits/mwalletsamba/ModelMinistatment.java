package com.wpits.mwalletsamba;

public class ModelMinistatment {
	String 	amount ;
	String trans_type ;
	String created_date ;
	String type ;
	String remark ;
	String operater ;
	String dest_mob;
	String src_mob;
	String mob;
	String destname;
	String srcname;
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDestmob() {
		return dest_mob;
	}
	public void setDestmob(String dest_mob) {
		this.dest_mob = dest_mob;
	}
	public String getSrcmob() {
		return src_mob;
	}
	public void setSrc_mob(String src_mob) {
		this.src_mob = src_mob;
	}
	public String getMob() {
		return mob;
	}
	public void setMob(String mob) {
		this.mob = mob;
	}
	
	public ModelMinistatment(String amount, String trans_type,
			String created_date, String type, String remark, String operater,
			String dest_mob, String src_mob, String mob, String destname,
			String srcname) {
		super();
		this.amount = amount;
		this.trans_type = trans_type;
		this.created_date = created_date;
		this.type = type;
		this.remark = remark;
		this.operater = operater;
		this.dest_mob = dest_mob;
		this.src_mob = src_mob;
		this.mob = mob;
		this.destname = destname;
		this.srcname = srcname;
	}
	public String getDestname() {
		return destname;
	}
	public void setDestname(String destname) {
		this.destname = destname;
	}
	public String getSrcname() {
		return srcname;
	}
	public void setSrcname(String srcname) {
		this.srcname = srcname;
	}
	public String getamount() {
		return amount;
	}
	public void setamount(String amount) {
		this.amount = amount;
	}
	public String getTrans_type() {
		return trans_type;
	}
	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
}
