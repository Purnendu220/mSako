package com.wpits.mwalletsamba;

import java.util.ArrayList;








import com.wpits.modelclass.NotificationModel;
import com.wpits.modelclass.SpinnerModel;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class LoanTypeAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<SpinnerModel> mMessages;



	public LoanTypeAdapter(Context context, ArrayList<SpinnerModel> messages) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.loan_type_list_item, parent, false);
			holder.buttonapplyloan = (TextView) convertView.findViewById(R.id.textviewapplyloan);




			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		holder.buttonapplyloan.setText(mMessages.get(position).getLoan_desc());





		return convertView;
	}
	private static class ViewHolder
	{
		TextView buttonapplyloan;

	}

	@Override
	public long getItemId(int position) {
		//Unimplemented, because we aren't using Sqlite.
		return position;
	}

}
