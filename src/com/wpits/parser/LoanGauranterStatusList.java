package com.wpits.parser;

import java.io.Serializable;

public class LoanGauranterStatusList implements Serializable {
	private String GuarantorReserveValue;
	private String GuarantorMsisdn;
	private String GuarantorFirstName;
	private String GauranterAcceptence;



	public LoanGauranterStatusList(String guarantorReserveValue,
			String guarantorMsisdn, String guarantorFirstName,
			String gauranterAcceptence) {
		super();
		GuarantorReserveValue = guarantorReserveValue;
		GuarantorMsisdn = guarantorMsisdn;
		GuarantorFirstName = guarantorFirstName;
		GauranterAcceptence = gauranterAcceptence;
	}
	public String getGuarantorReserveValue() {
		return GuarantorReserveValue;
	}
	public void setGuarantorReserveValue(String guarantorReserveValue) {
		GuarantorReserveValue = guarantorReserveValue;
	}
	public String getGuarantorMsisdn() {
		return GuarantorMsisdn;
	}
	public void setGuarantorMsisdn(String guarantorMsisdn) {
		GuarantorMsisdn = guarantorMsisdn;
	}
	public String getGuarantorFirstName() {
		return GuarantorFirstName;
	}
	public void setGuarantorFirstName(String guarantorFirstName) {
		GuarantorFirstName = guarantorFirstName;
	}
	public String getGauranterAcceptence() {
		return GauranterAcceptence;
	}
	public void setGauranterAcceptence(String gauranterAcceptence) {
		GauranterAcceptence = gauranterAcceptence;
	}


}
