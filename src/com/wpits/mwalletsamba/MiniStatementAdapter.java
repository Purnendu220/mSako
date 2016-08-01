package com.wpits.mwalletsamba;

import java.util.ArrayList;





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
public class MiniStatementAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<ModelMinistatment> mMessages;



	public MiniStatementAdapter(Context context, ArrayList<ModelMinistatment> messages) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.listrow, parent, false);
			holder.textdate = (TextView) convertView.findViewById(R.id.textdate);
			holder.textamount = (TextView) convertView.findViewById(R.id.textamount);
			holder.texttype = (TextView) convertView.findViewById(R.id.texttype);
			holder.textremark = (TextView) convertView.findViewById(R.id.textremark);
			holder.textname = (TextView) convertView.findViewById(R.id.textname);

		

			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		
		holder.textdate.setText(mMessages.get(position).getCreated_date());
		holder.textamount.setText(mMessages.get(position).getamount()+" KES");
		holder.texttype.setText(mMessages.get(position).getTrans_type());
		if(mMessages.get(position).getTrans_type().equalsIgnoreCase("dr"))
		{
			
			if(mMessages.get(position).getType().equalsIgnoreCase("paywithoutid")){
				holder.textname.setText(mMessages.get(position).getDestmob());
				holder.textremark.setText(mMessages.get(position).getRemark());
			}
			else if(mMessages.get(position).getType().equalsIgnoreCase("topup")){
				holder.textname.setText(mMessages.get(position).getDestmob());
				holder.textremark.setText(mMessages.get(position).getOperater());
       
			}
			else if(mMessages.get(position).getType().equalsIgnoreCase("Twitter")){
				holder.textname.setText(mMessages.get(position).getDestmob());
				holder.textremark.setText(mMessages.get(position).getType());
       
			}
			else if(mMessages.get(position).getType().contains("m SAKO")){
				holder.textname.setText(mMessages.get(position).getDestmob());
				holder.textremark.setText(mMessages.get(position).getRemark());
       
			}
			else{
				holder.textname.setText(mMessages.get(position).getDestname());
				holder.textremark.setText(mMessages.get(position).getDestmob());
			}
		}
		else{
			if(mMessages.get(position).getType().equalsIgnoreCase("paywithoutid")){
				holder.textname.setText(mMessages.get(position).getSrcmob());
				holder.textremark.setText(mMessages.get(position).getRemark());
			}
			else if(mMessages.get(position).getType().equalsIgnoreCase("topup")){
				holder.textname.setText(mMessages.get(position).getSrcmob());
				holder.textremark.setText(mMessages.get(position).getOperater());
       
			}
			else if(mMessages.get(position).getType().equalsIgnoreCase("Twitter")){
				holder.textname.setText(mMessages.get(position).getSrcmob());
				holder.textremark.setText(mMessages.get(position).getType());
       
			}
			else if(mMessages.get(position).getType().contains("m SAKO")){
				holder.textname.setText(mMessages.get(position).getDestmob());
				holder.textremark.setText(mMessages.get(position).getRemark());
       
			}
			else{
				holder.textname.setText(mMessages.get(position).getSrcname());
				holder.textremark.setText(mMessages.get(position).getSrcmob());
			}

		}

	
		return convertView;
	}
	private static class ViewHolder
	{
		TextView textdate,textamount,texttype,textremark,textname;
		//EditText quatity;
	}

	@Override
	public long getItemId(int position) {
		//Unimplemented, because we aren't using Sqlite.
		return position;
	}

}
