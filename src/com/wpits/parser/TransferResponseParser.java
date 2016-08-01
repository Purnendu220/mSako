package com.wpits.parser;

import java.io.Serializable;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TransferResponseParser  implements Serializable {

	private String resultCode;
	private String resultStatus;
	private String trasactionid;
	private String transactionamount;
	private String transactionreceiver;
	private String avilablebalance;
	private String transactiontime;




	public TransferResponseParser(String strjsondata) {
		// TODO Auto-generated constructor stub
		strjsondata = strjsondata.trim();
		System.out.println(strjsondata);
		JSONParser jsonParser = new JSONParser();

		try {
			JSONObject jsonObject = (JSONObject)jsonParser.parse(strjsondata);
			resultCode=(String)jsonObject.get("result_code");
			resultStatus=(String)jsonObject.get("result_status");
			trasactionid=(String)jsonObject.get("trasactionid");
			transactionamount=(String)jsonObject.get("transactionamount");
			transactionreceiver=(String)jsonObject.get("transactionreceiver");
			avilablebalance=(String)jsonObject.get("avilablebalance");
			transactiontime=(String)jsonObject.get("transactiontime");



		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultCode="4444";
			resultStatus="Transaction Failed";
		}

	}


	public String getTrasactionid() {
		return trasactionid;
	}





	public void setTrasactionid(String trasactionid) {
		this.trasactionid = trasactionid;
	}





	public String getTransactionamount() {
		return transactionamount;
	}





	public void setTransactionamount(String transactionamount) {
		this.transactionamount = transactionamount;
	}





	public String getTransactionreceiver() {
		return transactionreceiver;
	}





	public void setTransactionreceiver(String transactionreceiver) {
		this.transactionreceiver = transactionreceiver;
	}





	public String getAvilablebalance() {
		return avilablebalance;
	}





	public void setAvilablebalance(String avilablebalance) {
		this.avilablebalance = avilablebalance;
	}





	public String getTransactiontime() {
		return transactiontime;
	}





	public void setTransactiontime(String transactiontime) {
		this.transactiontime = transactiontime;
	}











	public TransferResponseParser(String resultCode, String resultStatus,
			String sessionid) {
		super();
		this.resultCode = resultCode;
		this.resultStatus = resultStatus;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}


}
