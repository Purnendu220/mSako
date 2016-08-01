package com.wpits.mwalletsamba;

import java.util.ArrayList;






import com.wpits.modelclass.NotificationModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class NotificationAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<NotificationModel> mMessages;



	public NotificationAdapter(Context context, ArrayList<NotificationModel> messages) {
		super();
		this.mContext = context;
		this.mMessages = messages;
	}
	@Override
	public int getCount() {
		return mMessages.size();
	}
	@Override
	public Object getItem(int position) {		
		return mMessages.get(position);
	}
	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder; 
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.listrownotification, parent, false);
			holder.textnotificationsubject = (TextView) convertView.findViewById(R.id.textnotificationsubject);
			holder.textremark = (TextView) convertView.findViewById(R.id.textremark);
			holder.textdate = (TextView) convertView.findViewById(R.id.textdate);
			holder.textsender = (TextView) convertView.findViewById(R.id.textsender);

		

			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		holder.textnotificationsubject.setText(mMessages.get(position).getNotificationsubject());
		holder.textremark.setText(mMessages.get(position).getNotificationdescription());
		holder.textdate.setText("Received on:-"+mMessages.get(position).getNotificationdate());
		holder.textsender.setText(mMessages.get(position).getNotificationsender());

		

	
		return convertView;
	}
	private static class ViewHolder
	{
		TextView textnotificationsubject,textremark,textdate,textsender;
	
	}

	@Override
	public long getItemId(int position) {
		//Unimplemented, because we aren't using Sqlite.
		return position;
	}

}
