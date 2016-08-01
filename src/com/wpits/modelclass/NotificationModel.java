package com.wpits.modelclass;

import java.io.Serializable;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class NotificationModel implements Serializable {
	String id;
	String notificationsubject;
	String notificationdescription;
	String notificationdate;
	String notificationsender;


	public NotificationModel(String id, String notificationsubject,
			String notificationdescription, String notificationdate,
			String notificationsender) {
		super();
		this.id = id;
		this.notificationsubject = notificationsubject;
		this.notificationdescription = notificationdescription;
		this.notificationdate = notificationdate;
		this.notificationsender = notificationsender;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNotificationsubject() {
		return notificationsubject;
	}
	public void setNotificationsubject(String notificationsubject) {
		this.notificationsubject = notificationsubject;
	}
	public String getNotificationdescription() {
		return notificationdescription;
	}
	public void setNotificationdescription(String notificationdescription) {
		this.notificationdescription = notificationdescription;
	}
	public String getNotificationdate() {
		return notificationdate;
	}
	public void setNotificationdate(String notificationdate) {
		this.notificationdate = notificationdate;
	}
	public String getNotificationsender() {
		return notificationsender;
	}
	public void setNotificationsender(String notificationsender) {
		this.notificationsender = notificationsender;
	}

}
