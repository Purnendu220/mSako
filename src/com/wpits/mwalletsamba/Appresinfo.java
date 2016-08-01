package com.wpits.mwalletsamba;
import java.util.List;





import com.wpits.utils.CircleImageView;
import com.wpits.utils.Utility;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Appresinfo extends ArrayAdapter<ResolveInfo> {
	private List<ResolveInfo> appsList = null;
	private Context context;
	private PackageManager packageManager;

	public Appresinfo(Context context, int textViewResourceId,
			List<ResolveInfo> appsList) {
		super(context, textViewResourceId, appsList);
		this.context = context;
		this.appsList = appsList;
		packageManager = context.getPackageManager();
	}

	@Override
	public int getCount() {
		return ((null != appsList) ? appsList.size() : 0);
	}

	@Override
	public ResolveInfo getItem(int position) {
		return ((null != appsList) ? appsList.get(position) : null);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (null == view) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.snippet_list_row, null);
		}

		ResolveInfo applicationInfo = appsList.get(position);
		if (null != view) {
			TextView appName = (TextView) view.findViewById(R.id.app_name);
			CircleImageView iconview = (CircleImageView) view.findViewById(R.id.app_icon);

			appName.setText(applicationInfo.loadLabel(packageManager));
			Bitmap bm=Utility.drawableToBitmap(applicationInfo.loadIcon(packageManager));
			int size=Utility.dp2px(context, 75);
			iconview.setImageBitmap(Utility.getResizedBitmap(bm, size, size));
		}
		return view;
	}
};