package com.wpits.utils;

import java.util.List;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Bundle;



public class SingleChoiceDialogFragment extends DialogFragment 
{
    public static final String DATA = "items";
     
    public static final String SELECTED = "selected";
     
    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) 
    {
        Resources res = getActivity().getResources();
        Bundle bundle = getArguments();
         
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
         
        dialog.setTitle("Please Select");
        dialog.setPositiveButton("Cancel", new PositiveButtonClickListener());
         
        List<String> list = (List<String>)bundle.get(DATA);
        int position = bundle.getInt(SELECTED);
         
        CharSequence[] cs = list.toArray(new CharSequence[list.size()]);
        dialog.setSingleChoiceItems(cs, position, selectItemListener);
         
        return dialog.create();
    }
     
    class PositiveButtonClickListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which) 
        {
            dialog.dismiss();
        }
    }
     
    OnClickListener selectItemListener = new OnClickListener() 
    {
 
        @Override
        public void onClick(DialogInterface dialog, int which) 
        {
            // process 
            //which means position
            dialog.dismiss();
        }
     
    };
}   