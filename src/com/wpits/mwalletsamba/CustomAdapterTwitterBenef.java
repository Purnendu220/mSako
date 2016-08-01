package com.wpits.mwalletsamba;

import java.util.ArrayList;
import java.util.List;










import com.wpits.modelclass.BenefciaryModelclass;
import com.wpits.modelclass.Twittermodelclass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
 
public class CustomAdapterTwitterBenef extends ArrayAdapter<Twittermodelclass> {
 
    private final Activity context;
    private final ArrayList<Twittermodelclass> list;
	private SparseBooleanArray mSelectedItemsIds;


    public CustomAdapterTwitterBenef(Activity context, ArrayList<Twittermodelclass> list) {
        super(context, R.layout.customlayout, list);
        this.context = context;
        this.list = list;
		mSelectedItemsIds = new SparseBooleanArray();

    }
 
    static class ViewHolder {
        protected TextView textbenefname;
      
    }
 
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
   
	
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.customlayout, parent, false );
            ViewHolder   viewHolder = new ViewHolder();
            viewHolder.textbenefname = (TextView) view.findViewById(R.id.benefname);
         

            view.setTag(viewHolder);
            
        } else {
        	
            view = convertView;
        }

        final ViewHolder  holder = (ViewHolder) view.getTag();
holder.textbenefname.setText(list.get(position).getBenefname());
    
  
     return view;
    }
}
