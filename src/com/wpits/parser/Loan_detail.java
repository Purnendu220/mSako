package com.wpits.parser;

import java.io.Serializable;
import java.util.ArrayList;

public class Loan_detail  implements Serializable
{
	private String loan_acountstatus;

	private String loan_gauranterdetail;

	private String loan_adminfee;

	private String loan_ownreservedamount;

	private ArrayList<Gauranter> gauranter;

	private String loan_id;

	public String getLoan_acountstatus ()
	{
		return loan_acountstatus;
	}

	public void setLoan_acountstatus (String loan_acountstatus)
	{
		this.loan_acountstatus = loan_acountstatus;
	}

	public String getLoan_gauranterdetail ()
	{
		return loan_gauranterdetail;
	}

	public void setLoan_gauranterdetail (String loan_gauranterdetail)
	{
		this.loan_gauranterdetail = loan_gauranterdetail;
	}

	public String getLoan_adminfee ()
	{
		return loan_adminfee;
	}

	public void setLoan_adminfee (String loan_adminfee)
	{
		this.loan_adminfee = loan_adminfee;
	}

	public String getLoan_ownreservedamount ()
	{
		return loan_ownreservedamount;
	}

	public void setLoan_ownreservedamount (String loan_ownreservedamount)
	{
		this.loan_ownreservedamount = loan_ownreservedamount;
	}

	public ArrayList<Gauranter> getGauranter ()
	{
		return gauranter;
	}

	public void setGauranter (ArrayList<Gauranter> gauranter)
	{
		this.gauranter = gauranter;
	}

	public String getLoan_id ()
	{
		return loan_id;
	}

	public void setLoan_id (String loan_id)
	{
		this.loan_id = loan_id;
	}

	@Override
	public String toString()
	{
		return "ClassPojo [loan_acountstatus = "+loan_acountstatus+", loan_gauranterdetail = "+loan_gauranterdetail+", loan_adminfee = "+loan_adminfee+", loan_ownreservedamount = "+loan_ownreservedamount+", gauranter = "+gauranter+", loan_id = "+loan_id+"]";
	}
}

