package com.wpits.mwalletsamba;

import java.util.ArrayList;

import org.json.JSONObject;
import org.json.simple.JSONArray;

import com.wpits.mwalletsamba.ModelMinistatment;

public class Minisummaryparser {
public ArrayList<ModelMinistatment>  parsedata(String jsonData){
	try {
		System.out.println(jsonData);
		ArrayList<ModelMinistatment> ministatement = new ArrayList<ModelMinistatment>();
	
		//org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();
		//JSONObject jsonObject = (JSONObject)jsonParser.parse(jsonData);
		JSONObject jsonObject = new JSONObject(jsonData);
		org.json.JSONArray cast = jsonObject.getJSONArray("jsonArray");
		for (int i=0; i<cast.length(); i++) {
		    JSONObject value = cast.getJSONObject(i);
		    String 	amount = (String)value.get("amount");
			String trans_type = (String)value.get("trans_type");
			String created_date = (String)value.get("created_date");
			String type = (String)value.get("type");
			String remark = (String)value.get("remark");
			String operater = (String)value.get("operater");
			String dest_mob = (String)value.get("dest_mob");
			String src_mob = (String)value.get("src_mob");
			String mobile_number = (String)value.get("mobile_no");
			String dest_name = (String)value.get("dest_name");
			String src_name = (String)value.get("src_name");
			ministatement.add(getministatement(amount, trans_type, created_date, type, remark, operater,dest_mob,src_mob,mobile_number,dest_name,src_name));

		}
	
return ministatement;
		
	} catch (Exception e) {
		
		// TODO: handle exception
		e.printStackTrace();
		return null;
	}
}
private ModelMinistatment getministatement(String amount,String trans_type,String created_date,String type,String remark,String operater,String dest_mob,String src_mob,String mob,String destname,String srcname)
{
	return new ModelMinistatment(amount, trans_type, created_date, type, remark, operater, dest_mob, src_mob, mob, destname, srcname);


}
}
