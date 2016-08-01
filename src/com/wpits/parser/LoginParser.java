package com.wpits.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LoginParser  implements Serializable {

	private String Balance;
	private String MaxBalance;
	private String MinBalance;
	private String agentParentId;
	String agentParentStatus;
	String AgentIdProof,agentIdProofValue,mobile,level,roleId,statusId;
	JSONArray persnal,perAddress;

	String  AddressVillage,AddressDescription,Addresscol,AddressPostoffice,AddressUnion,AddressPriPhone
	,AddressFax,Circle,City,Country,District,State,SubDistrict,Status,agentId,dob;
public String getAgentId() {
		return agentId;
	}


	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}


String bankAccountTypeCode,image,bankName,accountNo,bankCode,bankId,bankAccountTypeName,sign,
time,bankAccountType,branch;
	String AgentName,AgentFather,AgentMother,AgentMail,AgentCode,AgentGender;
	//public LoginParser(){}

	public LoginParser(String jsonString){
		init(jsonString);
	}


	public void init(String jsonData) {

		try {
			jsonData = jsonData.trim();
			JSONParser jsonParser = new JSONParser();

			JSONObject jsonObject = (JSONObject)jsonParser.parse(jsonData);


			Balance = (String)jsonObject.get("Balance");
			MaxBalance = (String)jsonObject.get("MaxBalance");
			MinBalance = (String)jsonObject.get("MinBalance");
			agentId = (String)jsonObject.get("agentId");

			agentParentId = (String)jsonObject.get("agentParentId");
			agentParentStatus = (String)jsonObject.get("agentParentStatus");
			AgentIdProof = (String)jsonObject.get("AgentIdProof");
			agentIdProofValue = (String)jsonObject.get("agentIdProofValue");
			mobile = (String)jsonObject.get("mobile");
			dob=(String)jsonObject.get("dob");
			level = (String)jsonObject.get("level");
			roleId = (String)jsonObject.get("roleId");
			bankAccountTypeCode = (String)jsonObject.get("bankAccountTypeCode");
			image = (String)jsonObject.get("image");
			bankName = (String)jsonObject.get("bankName");
			accountNo = (String)jsonObject.get("accountNo");
			bankCode = (String)jsonObject.get("bankCode");
			bankId = (String)jsonObject.get("bankId");
			bankAccountTypeName = (String)jsonObject.get("bankAccountTypeName");
			sign = (String)jsonObject.get("sign");
			time = (String)jsonObject.get("time");
			bankAccountType = (String)jsonObject.get("bankAccountType");
			branch = (String)jsonObject.get("branch");
			Status= (String)jsonObject.get("statusId");

			persnal = (JSONArray)jsonObject.get("persnal");

			AgentName=      (String) persnal.get(0);
			AgentFather=	(String) persnal.get(1);
			AgentMother=	(String) persnal.get(2);
			AgentMail=      (String) persnal.get(3);
			AgentCode=      (String) persnal.get(4);
			AgentGender=	(String) persnal.get(5);

			perAddress = (JSONArray)jsonObject.get("perAddress");


			AddressVillage=	    (String) perAddress.get(0);


			AddressDescription=	(String) perAddress.get(1);


			Addresscol=         (String) perAddress.get(2);



			AddressPostoffice=	(String) perAddress.get(3);
			AddressUnion=       (String) perAddress.get(4);
			AddressPriPhone=	(String) perAddress.get(5);
			AddressFax=         (String) perAddress.get(6);
			Circle=             (String) perAddress.get(7);
			City=               (String) perAddress.get(8);
			Country=            (String) perAddress.get(9);
			District=           (String) perAddress.get(10);
			State=              (String) perAddress.get(11);
			SubDistrict=        (String) perAddress.get(12);



			//System.out.println(Balance+":"+MaxBalance+":"+MinBalance+":"+agentParentId+":"+agentParentStatus+":"+
				//	AgentIdProof+":"+agentIdProofValue+":"+mobile+":"+level+":"+roleId+":"+statusId+":"+persnal.toString()
				//	+":"+perAddress.toString());			


		} catch (Exception e) {

			// TODO: handle exception
			e.printStackTrace();
		}
	}



	public String getDob() {
		return dob;
	}


	public void setDob(String dob) {
		this.dob = dob;
	}


	public String getBalance() {
		return Balance;
	}


	public void setBalance(String balance) {
		Balance = balance;
	}


	public String getMaxBalance() {
		return MaxBalance;
	}


	public void setMaxBalance(String maxBalance) {
		MaxBalance = maxBalance;
	}


	public String getMinBalance() {
		return MinBalance;
	}


	public void setMinBalance(String minBalance) {
		MinBalance = minBalance;
	}


	public String getAgentParentId() {
		return agentParentId;
	}


	public void setAgentParentId(String agentParentId) {
		this.agentParentId = agentParentId;
	}


	public String getAgentParentStatus() {
		return agentParentStatus;
	}


	public void setAgentParentStatus(String agentParentStatus) {
		this.agentParentStatus = agentParentStatus;
	}


	public String getAgentIdProof() {
		return AgentIdProof;
	}


	public void setAgentIdProof(String agentIdProof) {
		AgentIdProof = agentIdProof;
	}


	public String getAgentIdProofValue() {
		return agentIdProofValue;
	}


	public void setAgentIdProofValue(String agentIdProofValue) {
		this.agentIdProofValue = agentIdProofValue;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	public String getStatusId() {
		return statusId;
	}


	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}


	public JSONArray getPersnal() {
		return persnal;
	}


	public void setPersnal(JSONArray persnal) {
		this.persnal = persnal;
	}


	public JSONArray getPerAddress() {
		return perAddress;
	}


	public void setPerAddress(JSONArray perAddress) {
		this.perAddress = perAddress;
	}


	public String getAddressVillage() {
		return AddressVillage;
	}


	public void setAddressVillage(String addressVillage) {
		AddressVillage = addressVillage;
	}


	public String getAddressDescription() {
		return AddressDescription;
	}


	public void setAddressDescription(String addressDescription) {
		AddressDescription = addressDescription;
	}


	public String getAddresscol() {
		return Addresscol;
	}


	public void setAddresscol(String addresscol) {
		Addresscol = addresscol;
	}


	public String getAddressPostoffice() {
		return AddressPostoffice;
	}


	public void setAddressPostoffice(String addressPostoffice) {
		AddressPostoffice = addressPostoffice;
	}


	public String getAddressUnion() {
		return AddressUnion;
	}


	public void setAddressUnion(String addressUnion) {
		AddressUnion = addressUnion;
	}


	public String getAddressPriPhone() {
		return AddressPriPhone;
	}


	public void setAddressPriPhone(String addressPriPhone) {
		AddressPriPhone = addressPriPhone;
	}


	public String getAddressFax() {
		return AddressFax;
	}


	public void setAddressFax(String addressFax) {
		AddressFax = addressFax;
	}


	public String getCircle() {
		return Circle;
	}


	public void setCircle(String circle) {
		Circle = circle;
	}


	public String getCity() {
		return City;
	}


	public void setCity(String city) {
		City = city;
	}


	public String getCountry() {
		return Country;
	}


	public void setCountry(String country) {
		Country = country;
	}


	public String getDistrict() {
		return District;
	}


	public void setDistrict(String district) {
		District = district;
	}


	public String getState() {
		return State;
	}


	public void setState(String state) {
		State = state;
	}


	public String getSubDistrict() {
		return SubDistrict;
	}


	public void setSubDistrict(String subDistrict) {
		SubDistrict = subDistrict;
	}


	public String getStatus() {
		return Status;
	}


	public void setStatus(String status) {
		Status = status;
	}


	public String getBankAccountTypeCode() {
		return bankAccountTypeCode;
	}


	public void setBankAccountTypeCode(String bankAccountTypeCode) {
		this.bankAccountTypeCode = bankAccountTypeCode;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getBankName() {
		return bankName;
	}


	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


	public String getAccountNo() {
		return accountNo;
	}


	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}


	public String getBankCode() {
		return bankCode;
	}


	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}


	public String getBankId() {
		return bankId;
	}


	public void setBankId(String bankId) {
		this.bankId = bankId;
	}


	public String getBankAccountTypeName() {
		return bankAccountTypeName;
	}


	public void setBankAccountTypeName(String bankAccountTypeName) {
		this.bankAccountTypeName = bankAccountTypeName;
	}


	public String getSign() {
		return sign;
	}


	public void setSign(String sign) {
		this.sign = sign;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getBankAccountType() {
		return bankAccountType;
	}


	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}


	public String getBranch() {
		return branch;
	}


	public void setBranch(String branch) {
		this.branch = branch;
	}


	public String getAgentName() {
		return AgentName;
	}


	public void setAgentName(String agentName) {
		AgentName = agentName;
	}


	public String getAgentFather() {
		return AgentFather;
	}


	public void setAgentFather(String agentFather) {
		AgentFather = agentFather;
	}


	public String getAgentMother() {
		return AgentMother;
	}


	public void setAgentMother(String agentMother) {
		AgentMother = agentMother;
	}


	public String getAgentMail() {
		return AgentMail;
	}


	public void setAgentMail(String agentMail) {
		AgentMail = agentMail;
	}


	public String getAgentCode() {
		return AgentCode;
	}


	public void setAgentCode(String agentCode) {
		AgentCode = agentCode;
	}


	public String getAgentGender() {
		return AgentGender;
	}


	public void setAgentGender(String agentGender) {
		AgentGender = agentGender;
	}


	public static void main(String a[]){

		String str = "{\"stanza-name\":\"login\",\"stanza-type\":\"login\",\"stanza-attributes\":{\"from\":\"sanjay\",\"password\":\"1234\"}}";
		String str1="{\"agentParentStatus\":\"1\",\"agentParentId\":\"1\",\"agentIdProofValue\":\"8651236523\",\"persnal\":[\"Kumar Sharma\",\"\",\"\",\"-\",\"312376\",\"1\"],\"AgentIdProof\":\"2\",\"MinBalance\":\"10000\",\"statusId\":\"1\",\"level\":\"4\",\"MaxBalance\":\"10000\",\"perAddress\":[\"\",\"123 5\",null,\"\",null,\"\",null,\"Mayur Vihar Phase 3\",\"OLD DELHI\",\"DELHI\",\"DELHI\",\"DELHI AREA\",\"ACTIVE\"],\"Balance\":\"100\",\"mobile\":\"9015398151\",\"roleId\":\"4\"}";
		JSONParser parse=new JSONParser();

		//JSONObject obj=(JSONObject)parse.parse(new FileReader("/src/text.json"));
		LoginParser parser = new LoginParser(str1);



	}
}
