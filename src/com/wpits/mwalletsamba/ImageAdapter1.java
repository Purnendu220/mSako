package com.wpits.mwalletsamba;


import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter1 extends BaseAdapter {
	private Context mContext;

	// Keep all Images in array
	public Integer[] mThumbIds = {
			R.drawable.deposit_money, R.drawable.transfer_money,R.drawable.loan,
			R.drawable.ministatement,R.drawable.notification,R.drawable.changempin, 
			R.drawable.referfriend, R.drawable.faqs,R.drawable.help

	};

	public String[] mDescriptions={
			"Deposit Money","Transfer Money","Loan"
			,"Mini Statement","Notifications","Change MPIN"
			,"Refer Friends","FAQs","Help"

	};


	float scale;
	// Constructor
	public ImageAdapter1(Context c){
		mContext = c;
		Resources resources = mContext.getResources();
		scale = resources.getDisplayMetrics().density;
	}

	@Override
	public int getCount() {
		return mThumbIds.length;
	}

	@Override
	public Object getItem(int position) {
		return mThumbIds[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder; 
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);
			holder.imageview1 = (ImageView) convertView.findViewById(R.id.imageview1);
			holder.textViewdescription = (TextView) convertView.findViewById(R.id.textViewdescription);
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		holder.textViewdescription.setSelected(true);
		holder.imageview1.setImageResource(mThumbIds[position]);
		holder.imageview1.setScaleType(ImageView.ScaleType.CENTER_CROP);
		//holder.imageview1.setLayoutParams(new GridView.LayoutParams(Math.round(100*scale),Math.round(100*scale)));
		holder.textViewdescription.setText(mDescriptions[position]);

		return convertView;
	}
	private static class ViewHolder
	{
		ImageView imageview1;
		TextView textViewdescription;

	}
}