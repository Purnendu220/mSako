package com.wpits.parser;

import java.io.Serializable;

public class Gauranter implements Serializable
{
    private String gauranter_status;

    private String gauranter_number;

    private String gauranter_status_acc;

    public Gauranter(String gauranter_status, String gauranter_number,
			String gauranter_status_acc) {
		super();
		this.gauranter_status = gauranter_status;
		this.gauranter_number = gauranter_number;
		this.gauranter_status_acc = gauranter_status_acc;
	}

	public String getGauranter_status ()
    {
        return gauranter_status;
    }

    public void setGauranter_status (String gauranter_status)
    {
        this.gauranter_status = gauranter_status;
    }

    public String getGauranter_number ()
    {
        return gauranter_number;
    }

    public void setGauranter_number (String gauranter_number)
    {
        this.gauranter_number = gauranter_number;
    }

    public String getGauranter_status_acc ()
    {
        return gauranter_status_acc;
    }

    public void setGauranter_status_acc (String gauranter_status_acc)
    {
        this.gauranter_status_acc = gauranter_status_acc;
    }

}