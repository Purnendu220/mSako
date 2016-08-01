package com.wpits.modelclass;

public class Transferhistory {
//(String id,String receivercustid,String receivername,String receiverbankname,
	//String receiverbankifsccode,String recevieraccno,String amount,String time)
	
private	String id;
private	String receivercustid;
private String receivername;
private String receiverbankname;
private String receiverbankifsccode;
private String recevieraccno;
private String amount;
private String time;
	public Transferhistory(String id,String receivercustid,String receivername,String receiverbankname,
			String receiverbankifsccode,String recevieraccno,String amount,String time)
	{
		this.id=id;
		this.receivercustid=receivercustid;
		this.receivername=receivername;
		this.receiverbankname=receiverbankname;
		this.receiverbankifsccode=receiverbankifsccode;
		this.recevieraccno=recevieraccno;
		this.amount=amount;
		this.time=time;

	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReceivercustid() {
		return receivercustid;
	}
	public void setReceivercustid(String receivercustid) {
		this.receivercustid = receivercustid;
	}
	public String getReceivername() {
		return receivername;
	}
	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}
	public String getReceiverbankname() {
		return receiverbankname;
	}
	public void setReceiverbankname(String receiverbankname) {
		this.receiverbankname = receiverbankname;
	}
	public String getReceiverbankifsccode() {
		return receiverbankifsccode;
	}
	public void setReceiverbankifsccode(String receiverbankifsccode) {
		this.receiverbankifsccode = receiverbankifsccode;
	}
	public String getRecevieraccno() {
		return recevieraccno;
	}
	public void setRecevieraccno(String recevieraccno) {
		this.recevieraccno = recevieraccno;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
