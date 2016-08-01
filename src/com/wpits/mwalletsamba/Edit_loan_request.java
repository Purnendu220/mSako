package com.wpits.mwalletsamba;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;

import com.wpits.data.TodoDAO;
import com.wpits.modelclass.SpinnerModel;
import com.wpits.mwalletsamba.AndroidGridLayoutActivity.Logout;
import com.wpits.mwalletsamba.loan_activity.SubmitLoanApplication;
import com.wpits.mwalletsamba.loan_activity.SubmitLoanApplicationEmergency;
import com.wpits.parser.Gauranter;
import com.wpits.parser.LoanAccountDetailParser;
import com.wpits.parser.LoanGauranterStatusList;
import com.wpits.parser.LoanRequstResponse;
import com.wpits.parser.LoanTypeDetailParser;
import com.wpits.parser.LoanTypeList;
import com.wpits.parser.UserLoginparser;
import com.wpits.parser.UserLogoutParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.hardware.camera2.TotalCaptureResult;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.cardemulation.OffHostApduService;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Edit_loan_request extends Activity implements OnClickListener {

	Button btnloan;
	Spinner spinnerloantenure;
	//Spinner spinnerloantype;
	EditText editTextloanamount;
	EditText guaranter1msisdn,guaranter1amount,
	guaranter2msisdn,guaranter2amount,
	guaranter3msisdn,guaranter3amount,
	guaranter4msisdn,guaranter4amount,
	guaranter5msisdn,guaranter5amount,
	editTextmygaurantee;
	TextView textViewtotal;
	String loantype,loantenure;
	HashMap<String, String> mapgauranter;
	double loanlimit=0;
	double loanamount;
	double remaninggauranteeamount;
	double myreserveamount;
	double g1amount=0,g2amount=0,g3amount=0,g4amount=0,g5amount=0,mygauranteeamount=0;

	boolean isgauranter1available=false,
			isgauranter2available=false, 
			isgauranter3available=false, 
			isgauranter4available=false, 
			isgauranter5available=false;
	TextView textViewloanlimit;
	UserLoginparser userobject;
	Animation animSideDown,animSideUp;
	ScrollView scrollView1;
	TodoDAO dao;
	CustomAdapter adapter;
	Edit_loan_request activity = null;
	Resources res;

	String re1="(\\d+)";
	String ownreserveamountformule;
	LoanAccountDetailParser loanobject;
	LoanTypeList loantypemodel;
	SpinnerModel loantypeselectedmodel;
	TextView textViewloantype;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loan_);
		initview();
		textViewloantype=(TextView)findViewById(R.id.textViewloantype);
		textViewloantype.setOnClickListener(this);
		activity=this;
		res = getResources(); 
		Intent iuser=getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");
			loanobject=(LoanAccountDetailParser)iuser.getExtras().getSerializable("loanobject");
			loantypeselectedmodel=(SpinnerModel)iuser.getExtras().getSerializable("loantype");


		}
		animSideDown = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_down);
		animSideUp = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_up);
		loanlimit=Double.parseDouble(userobject.getUserDetails().getUserWalletbalance())*3;
		btnloan.setOnClickListener(this);
		/*CustomListViewValuesArr.add(new SpinnerModel("Please select loan type",""));
		try {
			new GetLoanDetails().execute();

		} catch (Exception e) {
			// TODO: handle exception
			showAlert("Unable to get loan details please try after some time.", "loan");
		}*/



		editTextloanamount.setVisibility(View.VISIBLE);
		editTextloanamount.setAnimation(animSideDown);
		editTextloanamount.setText("0");
		SpinnerModel model=	loantypeselectedmodel;
		loantype=model.getOther_desc();
		textViewloantype.setText(model.getLoan_desc());
		loantypemodel=	dao.getloantypedetail(model.getOther_desc());
		if (loantypemodel!=null&&loantypemodel.getLoan_type_name().equalsIgnoreCase("Emergency")) {

			scrollView1.setVisibility(View.GONE);
			editTextmygaurantee.setVisibility(View.GONE);

		}
		else{
			scrollView1.setVisibility(View.VISIBLE);
			editTextmygaurantee.setVisibility(View.VISIBLE);
		}
		if (loantypemodel.getLoan_tenure()!=null) {
			ArrayList<SpinnerModel> arraylisttenure = new ArrayList<SpinnerModel>();
			arraylisttenure.add(new SpinnerModel("Please select loan tenure",""));

			String[] tenure=loantypemodel.getLoan_tenure().split(",");
			for (int i = 0; i < tenure.length; i++) {
				arraylisttenure.add(new SpinnerModel(tenure[i]+" Months",tenure[i]));

			}
			CustomAdapter adaptertenure = new CustomAdapter(activity, R.layout.spinner_rows, arraylisttenure,res);
			spinnerloantenure.setAdapter(adaptertenure);
		}
		String loanlimitformule=loantypemodel.getLoan_limit();
		ownreserveamountformule=loantypemodel.getOwn_reserve_amount();
		if (loanlimitformule!=null) {
			Pattern p = Pattern.compile(re1,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = p.matcher(loanlimitformule);
			if (m.find())
			{
				String int1=m.group(1);
				try {
					loanlimit=Double.parseDouble(userobject.getUserDetails().getUserWalletbalance())*Double.parseDouble(int1);
					textViewloanlimit.setText("Your Loan Limit is "+loanlimit);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					loanlimit=0;
					textViewloanlimit.setText("Your Loan Limit is "+loanlimit);

				}

			}
			else{
				loanlimit=0;
				textViewloanlimit.setText("Your Loan Limit is "+loanlimit);

			}
		}




		/*	spinnerloantype.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (spinnerloantype.getSelectedItemPosition()>0) {

					editTextloanamount.setVisibility(View.VISIBLE);
					editTextloanamount.setAnimation(animSideDown);
					editTextloanamount.setText("0");
					SpinnerModel model=	(SpinnerModel) arg0.getItemAtPosition(arg2);
					loantype=model.getOther_desc();
					loantypemodel=	dao.getloantypedetail(model.getOther_desc());
					if (loantypemodel!=null&&loantypemodel.getLoan_type_name().equalsIgnoreCase("Emergency")) {

						scrollView1.setVisibility(View.GONE);
						editTextmygaurantee.setVisibility(View.GONE);

					}
					else{
						scrollView1.setVisibility(View.VISIBLE);
						editTextmygaurantee.setVisibility(View.VISIBLE);
					}
					if (loantypemodel.getLoan_tenure()!=null) {
						ArrayList<SpinnerModel> arraylisttenure = new ArrayList<SpinnerModel>();
						arraylisttenure.add(new SpinnerModel("Please select loan tenure",""));

						String[] tenure=loantypemodel.getLoan_tenure().split(",");
						for (int i = 0; i < tenure.length; i++) {
							arraylisttenure.add(new SpinnerModel(tenure[i]+" Months",tenure[i]));

						}
						CustomAdapter adaptertenure = new CustomAdapter(activity, R.layout.spinner_rows, arraylisttenure,res);
						spinnerloantenure.setAdapter(adaptertenure);
					}
					String loanlimitformule=loantypemodel.getLoan_limit();
					ownreserveamountformule=loantypemodel.getOwn_reserve_amount();
					if (loanlimitformule!=null) {
						Pattern p = Pattern.compile(re1,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
						Matcher m = p.matcher(loanlimitformule);
						if (m.find())
						{
							String int1=m.group(1);
							try {
								loanlimit=Double.parseDouble(userobject.getUserDetails().getUserWalletbalance())*Double.parseDouble(int1);
								textViewloanlimit.setText("Your Loan Limit is "+loanlimit);

							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
								loanlimit=0;
								textViewloanlimit.setText("Your Loan Limit is "+loanlimit);

							}

						}
						else{
							loanlimit=0;
							textViewloanlimit.setText("Your Loan Limit is "+loanlimit);

						}
					}



				}
				else{
					loantype="";
					loantenure="";
					editTextloanamount.setText("");
					editTextloanamount.setVisibility(View.INVISIBLE);
					editTextloanamount.setAnimation(animSideUp);


					editTextmygaurantee.setText("");
					editTextmygaurantee.setVisibility(View.INVISIBLE);
					editTextmygaurantee.setAnimation(animSideUp);
					scrollView1.setVisibility(View.INVISIBLE);
					scrollView1.setAnimation(animSideUp);
					spinnerloantenure.setVisibility(View.INVISIBLE);
					spinnerloantenure.setAnimation(animSideUp);

					ArrayList<SpinnerModel> arraylisttenure = new ArrayList<SpinnerModel>();
					arraylisttenure.add(new SpinnerModel("Please select loan tenure",""));
					CustomAdapter adaptertenure = new CustomAdapter(activity, R.layout.spinner_rows, arraylisttenure,res);
					spinnerloantenure.setAdapter(adaptertenure);
				}
				System.out.println(loantype+":"+spinnerloantype.getSelectedItemPosition());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});*/

		spinnerloantenure.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (spinnerloantenure.getSelectedItemPosition()>0) {

					SpinnerModel model=	(SpinnerModel) arg0.getItemAtPosition(arg2);
					loantenure=model.getOther_desc();

				}
				else{
					loantenure="";
				}
				//	System.out.println(loantenure+":"+spinnerloantype.getSelectedItemPosition());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		editTextloanamount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				try {
					loanamount=Double.parseDouble(s.toString());
					remaninggauranteeamount=loanamount;
					if (loanamount>loanlimit) {

						showAlert("Your can take loan upto "+loanlimit+" KES only.","amount");
					}
					if (loanamount>0) {
						if (loantypemodel!=null&&loantypemodel.getLoan_type_name().equalsIgnoreCase("Emergency")) {

							spinnerloantenure.setVisibility(View.VISIBLE);
							spinnerloantenure.setAnimation(animSideDown);

						}
						else{

							editTextmygaurantee.clearAnimation();
							scrollView1.clearAnimation();
							editTextmygaurantee.setVisibility(View.VISIBLE);
							editTextmygaurantee.setAnimation(animSideDown);
							scrollView1.setVisibility(View.VISIBLE);
							scrollView1.setAnimation(animSideDown);
							spinnerloantenure.setVisibility(View.VISIBLE);
							spinnerloantenure.setAnimation(animSideDown);
							if (ownreserveamountformule!=null) {
								
								System.out.println("ownreserveamountformule"+ownreserveamountformule);
								Pattern p = Pattern.compile(re1,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
								Matcher m = p.matcher(ownreserveamountformule);
								if (m.find())
								{
									String int1=m.group(1);
									try {
										myreserveamount=loanamount/Double.parseDouble(int1);
										editTextmygaurantee.setText(String.valueOf(myreserveamount)); 

									} catch (Exception e) {
										// TODO: handle exception
										e.printStackTrace();
										myreserveamount=0;

									}

								}
								else{
									myreserveamount=0;

								}	

							}
							else{
								editTextmygaurantee.setEnabled(true);
								editTextmygaurantee.setText(String.valueOf(loanamount));
								myreserveamount=loanamount;

							}	
						}



					}
					else{
						/*editTextmygaurantee.clearAnimation();
						scrollView1.clearAnimation();

						editTextmygaurantee.setVisibility(View.INVISIBLE);
						editTextmygaurantee.setAnimation(animSideUp);
						scrollView1.setVisibility(View.INVISIBLE);
						scrollView1.setAnimation(animSideUp);
						spinnerloantenure.setVisibility(View.INVISIBLE);
						spinnerloantenure.setAnimation(animSideUp);*/
					}




				} catch (Exception e) {
					// TODO: handle exception
					/*editTextmygaurantee.clearAnimation();
					scrollView1.clearAnimation();
					editTextmygaurantee.setVisibility(View.GONE);
					editTextmygaurantee.setAnimation(animSideUp);
					scrollView1.setVisibility(View.INVISIBLE);
					scrollView1.setAnimation(animSideUp);
					spinnerloantenure.setVisibility(View.INVISIBLE);
					spinnerloantenure.setAnimation(animSideUp);
					editTextloanamount.setError("Please provide valid loan amount");*/
				}

			}
		});

		editTextmygaurantee.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				try {
					remaninggauranteeamount=loanamount-Double.parseDouble(s.toString());
					System.out.println(remaninggauranteeamount);
					if (remaninggauranteeamount<0) {

						showAlert1("Your gaurantee limit is more than loan amount.","amount");
					}
					else{

					}



				} catch (Exception e) {
					// TODO: handle exception
					editTextloanamount.setError("Please provide valid loan amount");
				}

			}
		});
		guarantergauranteecheck();

		if (loanobject!=null) {

			editTextloanamount.setText(loanobject.getLoan_amount());

			//editTextmygaurantee.setText(loanobject.get);
			if (loanobject.getGauranterlist()!=null&&loanobject.getGauranterlist().size()>0) {
				double total=0;
				for (int i = 0; i < loanobject.getGauranterlist().size(); i++) {

					LoanGauranterStatusList loangauranterstatuslist=loanobject.getGauranterlist().get(i);
					total=total+Double.parseDouble(loangauranterstatuslist.getGuarantorReserveValue());
					switch (i) {
					case 0:
						guaranter1msisdn.setText(loangauranterstatuslist.getGuarantorMsisdn());
						guaranter1amount.setText(loangauranterstatuslist.getGuarantorReserveValue());
						break;

					case 1:
						guaranter2msisdn.setText(loangauranterstatuslist.getGuarantorMsisdn());
						guaranter2amount.setText(loangauranterstatuslist.getGuarantorReserveValue());
						break;
					case 2:
						guaranter3msisdn.setText(loangauranterstatuslist.getGuarantorMsisdn());
						guaranter3amount.setText(loangauranterstatuslist.getGuarantorReserveValue());
						break;
					case 3:
						guaranter4msisdn.setText(loangauranterstatuslist.getGuarantorMsisdn());
						guaranter4amount.setText(loangauranterstatuslist.getGuarantorReserveValue());
						break;
					case 4:
						guaranter5msisdn.setText(loangauranterstatuslist.getGuarantorMsisdn());
						guaranter5amount.setText(loangauranterstatuslist.getGuarantorReserveValue());
						break;

					default:
						break;
					}

				}
				textViewtotal.setText("Total Gaurantee Amount is "+total);
			} 
			try {
				spinnerloantenure.setSelection(1);

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}


	public void guarantergauranteecheck(){
		guaranter1amount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {	}
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				try {
					double localvalue;
					g1amount=Double.parseDouble(s.toString());

					double amt=myreserveamount+g1amount;
					textViewtotal.setText("Total Guarantee Amount "+amt);
					System.out.println(g1amount);
					if (remaninggauranteeamount<0) {

						showAlert1("Your gaurantee limit is more than loan amount.","g1");
					}
					else{

					}
				} catch (Exception e) {
					// TODO: handle exception
					editTextloanamount.setError("Please provide valid gaurantee amount");
				}
			}
		});
		guaranter2amount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {	}
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				try {
					g2amount=Double.parseDouble(s.toString());
					System.out.println(g2amount);
					double amt=myreserveamount+g1amount+g2amount;
					textViewtotal.setText("Total Guarantee Amount "+amt);
					if (remaninggauranteeamount<0) {

						showAlert1("Your gaurantee limit is more than loan amount.","g2");
					}
					else{

					}
				} catch (Exception e) {
					// TODO: handle exception
					editTextloanamount.setError("Please provide valid gaurantee amount");
				}
			}
		});

		guaranter3amount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {	}
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				try {
					g3amount=Double.parseDouble(s.toString());
					System.out.println(g3amount);
					double amt=myreserveamount+g1amount+g2amount+g3amount;
					textViewtotal.setText("Total Guarantee Amount "+amt);
					if (remaninggauranteeamount<0) {

						showAlert1("Your gaurantee limit is more than loan amount.","g3");
					}
					else{

					}
				} catch (Exception e) {
					// TODO: handle exception
					editTextloanamount.setError("Please provide valid gaurantee amount");
				}
			}
		});
		guaranter4amount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {	}
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				try {
					g4amount=Double.parseDouble(s.toString());
					System.out.println(g4amount);
					double amt=myreserveamount+g1amount+g2amount+g3amount+g4amount;
					textViewtotal.setText("Total Guarantee Amount "+amt);
					if (remaninggauranteeamount<0) {

						showAlert1("Your gaurantee limit is more than loan amount.","g4");
					}
					else{

					}
				} catch (Exception e) {
					// TODO: handle exception
					editTextloanamount.setError("Please provide valid gaurantee amount");
				}
			}
		});

		guaranter5amount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {	}
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				try {
					g5amount=Double.parseDouble(s.toString());
					System.out.println(g5amount);
					double amt=myreserveamount+g1amount+g2amount+g3amount+g4amount+g5amount;
					textViewtotal.setText("Total Guarantee Amount "+amt);

					if (remaninggauranteeamount<0) {

						showAlert1("Your gaurantee limit is more than loan amount.","g5");
					}
					else{

					}
				} catch (Exception e) {
					// TODO: handle exception
					editTextloanamount.setError("Please provide valid loan amount");
				}
			}
		});


	}
	public void initview(){

		btnloan=(Button)findViewById(R.id.btnloan);
		textViewloanlimit=(TextView)findViewById(R.id.textViewloanlimit);
		textViewtotal=(TextView)findViewById(R.id.textViewtotal);
		editTextloanamount=(EditText)findViewById(R.id.editTextloanamount);

		guaranter1msisdn=(EditText)findViewById(R.id.guaranter1msisdn);
		guaranter2msisdn=(EditText)findViewById(R.id.guaranter2msisdn);
		guaranter3msisdn=(EditText)findViewById(R.id.guaranter3msisdn);
		guaranter4msisdn=(EditText)findViewById(R.id.guaranter4msisdn);
		guaranter5msisdn=(EditText)findViewById(R.id.guaranter5msisdn);
		guaranter1amount=(EditText)findViewById(R.id.guaranter1amount);
		guaranter2amount=(EditText)findViewById(R.id.guaranter2amount);
		guaranter3amount=(EditText)findViewById(R.id.guaranter3amount);
		guaranter4amount=(EditText)findViewById(R.id.guaranter4amount);
		guaranter5amount=(EditText)findViewById(R.id.guaranter5amount);

		editTextmygaurantee=(EditText)findViewById(R.id.editTextmygaurantee);
		scrollView1=(ScrollView)findViewById(R.id.scrollView1);
		//spinnerloantype=(Spinner)findViewById(R.id.spinnerloantype);
		spinnerloantenure=(Spinner)findViewById(R.id.spinnerloantenure);
		mapgauranter=new HashMap<String, String>();
		dao=new TodoDAO(getApplicationContext());


	}

	public void requestloadloan()
	{

		if (loantype!=null&&loantype.length()>0) {

			if (editTextloanamount!=null&&editTextloanamount.getText().toString().length()>0) {
				if (loantenure!=null&&loantenure.length()>0) {
					if (loantypemodel.getLoan_type_name().equalsIgnoreCase("Emergency")) {
						new SubmitLoanApplicationEmergency().execute();
					} 
					else {



						if (editTextmygaurantee!=null&&editTextmygaurantee.getText().toString().length()>0) {



							boolean isanyerror=validategauranter();
							System.out.println(isanyerror);
							if (isanyerror) {


							} else {

								if (!isgauranter1available&&!isgauranter2available&&!isgauranter3available&&!isgauranter4available&&!isgauranter5available) {

									Toast.makeText(getApplicationContext(), "Please provide atleast one gauranter.", Toast.LENGTH_LONG).show();

								}
								else{
									if (isgauranter1available) {
										mapgauranter.put(guaranter1msisdn.getText().toString(), guaranter1amount.getText().toString());	
									}
									if (isgauranter2available) {
										mapgauranter.put(guaranter2msisdn.getText().toString(), guaranter2amount.getText().toString());	

									}
									if (isgauranter3available) {
										mapgauranter.put(guaranter3msisdn.getText().toString(), guaranter3amount.getText().toString());	

									}
									if (isgauranter4available) {
										mapgauranter.put(guaranter4msisdn.getText().toString(), guaranter4amount.getText().toString());	

									}
									if (isgauranter5available) {
										mapgauranter.put(guaranter5msisdn.getText().toString(), guaranter5amount.getText().toString());	

									}
									Iterator it = mapgauranter.entrySet().iterator();
									boolean ismegauranter=false;
									while (it.hasNext()) {
										JSONObject obj1=new JSONObject();
										Map.Entry pair = (Map.Entry)it.next();
										obj1.put("gaurantermsisdn", pair.getKey());
										System.out.println(pair.getKey());
										if (pair.getKey().toString().equalsIgnoreCase(userobject.getUserDetails().getUserMobile())) {
											ismegauranter=true;
										} else {

										}
									}

									if (ismegauranter) {
										showAlert("You Cannot be gauranter for your own loan", "me");
									}
									else{


										double totalgauranteeamount=g1amount+g2amount+g3amount+g4amount+g5amount+myreserveamount;
										if (totalgauranteeamount<loanamount) {
											showAlert("Gaurantee Amount of all gauranter must be equals to loanamount.", "gamt");

										} 
										else {
											if (totalgauranteeamount>loanamount) {
												showAlert1("Your gaurantee amount is more that your loan amount.", "amtge");

											}
											else{
												runOnUiThread(new Runnable() {

													@Override
													public void run() {
														// TODO Auto-generated method stub
														new SubmitLoanApplication().execute();
														Toast.makeText(getApplicationContext(), "You Can Apply for loan now", Toast.LENGTH_LONG).show();	
													}
												});  

											}

										}
									}
								}
								//setloanrequested();

								//new SubmitLoanApplication().execute();

							}
						}
						else{
							Toast.makeText(getApplicationContext(), "Please provide your gaurantee amount.", Toast.LENGTH_LONG).show();


						}

					}
				} else {
					Toast.makeText(getApplicationContext(), "Please select loan tenure..", Toast.LENGTH_LONG).show();

				}

			} else {
				Toast.makeText(getApplicationContext(), "Please provide loan amount.", Toast.LENGTH_LONG).show();



			}

		}  
		else{
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Please select loan type", Toast.LENGTH_LONG).show();

				}
			});
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnloan:
			if (isNetworkAvailable()) {
				requestloadloan();

			}
			else{
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "Please Connect to Internet.", Toast.LENGTH_LONG).show();
					}
				});
			}
			break;
		case R.id.textViewloantype:
			new GetLoanDetails1().execute();

			break;
		default:
			break;
		}
	}
	public boolean validategauranter()
	{
		boolean isanyerror=false;

		if (guaranter1msisdn.getText().toString().length()>0&&guaranter1msisdn.getText().toString().length()>=10) {
			if (guaranter1amount.getText().toString().length()>0) {

				//Get The gauranter detail here**************
				isgauranter1available=true;

			} else {

				guaranter1amount.setError("Please provide amount for gauranter one.");
				isgauranter1available=false;
				isanyerror=true;

			}



		}
		else{
			if (guaranter1amount.getText().toString().length()>0) {
				guaranter1msisdn.setError("please provide valid guaranter");
				isgauranter1available=false;
				isanyerror=true;


			} else {
				if (guaranter1msisdn.getText().toString().length()>0) {
					guaranter1msisdn.setError("please provide valid guaranter");
					isgauranter1available=false;
					isanyerror=true;

				} else {
					//Gauranter 1 is not Available for the loan
					isgauranter1available=false;
				}     

			}

		}

		//-------------------------------------------------------------------------------------------------------------------------------------------------
		if (guaranter2msisdn.getText().toString().length()>0&&guaranter2msisdn.getText().toString().length()>=10) {
			if (guaranter2amount.getText().toString().length()>0) {

				//Get The gauranter detail here**************
				isgauranter2available=true;

			} else {

				guaranter2amount.setError("Please provide amount for gauranter.");
				isgauranter2available=false;
				isanyerror=true;
			}



		}
		else{
			if (guaranter2amount.getText().toString().length()>0) {
				guaranter2msisdn.setError("please provide valid guaranter");
				isgauranter2available=false;
				isanyerror=true;


			} else {
				if (guaranter2msisdn.getText().toString().length()>0) {
					guaranter2msisdn.setError("please provide valid guaranter");
					isgauranter2available=false;
					isanyerror=true;

				} else {
					//Gauranter 1 is not Available for the loan
					isgauranter2available=false;
				}     

			}

		}

		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------					
		if (guaranter3msisdn.getText().toString().length()>0&&guaranter3msisdn.getText().toString().length()>=10) {
			if (guaranter3amount.getText().toString().length()>0) {

				//Get The gauranter detail here**************
				isgauranter3available=true;

			} else {

				guaranter3amount.setError("Please provide amount for gauranter .");
				isgauranter3available=false;
				isanyerror=true;
			}



		}
		else{
			if (guaranter3amount.getText().toString().length()>0) {
				guaranter3msisdn.setError("please provide valid guaranter");
				isgauranter3available=false;
				isanyerror=true;

			} else {
				if (guaranter3msisdn.getText().toString().length()>0) {
					guaranter3msisdn.setError("please provide valid guaranter");
					isgauranter3available=false;
					isanyerror=true;

				} else {
					isgauranter3available=false;

					//Gauranter 1 is not Available for the loan
				}     

			}

		}

		//--------------------------------------------------------------------------------------------------------------------------------
		if (guaranter4msisdn.getText().toString().length()>0&&guaranter4msisdn.getText().toString().length()>=10) {
			if (guaranter4amount.getText().toString().length()>0) {

				//Get The gauranter detail here**************
				isgauranter4available=true;

			} else {

				guaranter4amount.setError("Please provide amount for gauranter .");
				isgauranter4available=false;
				isanyerror=true;

			}



		}
		else{
			if (guaranter4amount.getText().toString().length()>0) {
				guaranter4msisdn.setError("please provide valid guaranter");
				isgauranter4available=false;
				isanyerror=true;


			} else {
				if (guaranter4msisdn.getText().toString().length()>0) {
					guaranter4msisdn.setError("please provide valid guaranter");
					isgauranter4available=false;
					isanyerror=true;

				} else {
					//Gauranter 1 is not Available for the loan
					isgauranter4available=false;

				}     

			}

		}

		//----------------------------------------------------------------------------------------------------------------------------------------------------------
		if (guaranter5msisdn.getText().toString().length()>0&&guaranter5msisdn.getText().toString().length()>=10) {
			if (guaranter5amount.getText().toString().length()>0) {

				//Get The gauranter detail here**************
				isgauranter5available=true;

			} else {

				guaranter5amount.setError("Please provide amount for gauranter one.");
				isgauranter5available=false;
				isanyerror=true;
			}



		}
		else{
			if (guaranter5amount.getText().toString().length()>0) {
				guaranter5msisdn.setError("please provide valid guaranter");
				isgauranter5available=false;
				isanyerror=true;


			} else {
				if (guaranter5msisdn.getText().toString().length()>0) {
					guaranter5msisdn.setError("please provide valid guaranter");
					isgauranter5available=false;
					isanyerror=true;

				} else {
					//Gauranter 1 is not Available for the loan
					isgauranter5available=false;
				}     

			}

		}
		return isanyerror;



	}

	public void showAlert(String message,final String type)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(Edit_loan_request.this);

		//Setting Dialog Title
		alertDialog.setTitle("Alert");
		alertDialog.setCancelable(false);
		//Setting Dialog Message
		alertDialog.setMessage(message);
		//On Pressing Setting button
		alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() 
		{   
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				runOnUiThread( new Runnable() {
					public void run() {
						if (type.equalsIgnoreCase("amount")) {
							editTextloanamount.setText("0");
							editTextloanamount.requestFocus();
						}
						if (type.equalsIgnoreCase("loan")) {
							Intent i=new Intent(getApplicationContext(),AndroidGridLayoutActivity.class);
							i.putExtra("user_obj", userobject);
							startActivity(i);
							finish();
						}
						if (type.equalsIgnoreCase("me")) {

						}

					}
				});


			}
		});


		alertDialog.show();
	}

	public void showAlert1(String message,final String type)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(Edit_loan_request.this);

		//Setting Dialog Title
		alertDialog.setTitle("Alert");
		alertDialog.setCancelable(false);
		//Setting Dialog Message
		alertDialog.setMessage(message);
		//On Pressing Setting button
		alertDialog.setPositiveButton("No Issue", new DialogInterface.OnClickListener() 
		{   
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				runOnUiThread( new Runnable() {
					public void run() {

						if(type.equalsIgnoreCase("amtge")){

							Toast.makeText(getApplicationContext(), "You Can Apply for loan now", Toast.LENGTH_LONG).show();	
							new SubmitLoanApplication().execute();

						}


					}
				});


			}
		});

		alertDialog.setNegativeButton("Change", new DialogInterface.OnClickListener() 
		{   
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				runOnUiThread( new Runnable() {
					public void run() {
						if (type.equalsIgnoreCase("amount")) {
							editTextmygaurantee.setText(String.valueOf(loanamount));
							editTextmygaurantee.requestFocus();
						}
						if(type.equalsIgnoreCase("amtge")){


						}

					}
				});


			}
		});



		alertDialog.show();
	}

	public class SubmitLoanApplication extends AsyncTask<String, Void, LoanApplicationObject> {
		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(Edit_loan_request.this, "Proceeding ",
					"Please Wait");
		}

		@Override
		protected LoanApplicationObject doInBackground(String... arg0) {


			return new LoanApplicationObject(userobject.getUserDetails().getUserMobile(), String.valueOf(loanamount), mapgauranter, loantype, loantenure, userobject.getSessionid(), userobject.getUser_type(),String.valueOf(myreserveamount));
		}

		@Override
		protected void onPostExecute(LoanApplicationObject strFromDoInBg) {
			if (strFromDoInBg!=null) {
				Intent i=new Intent(getApplicationContext(),MpinOtpVerificationLoan.class);
				i.putExtra("user_obj", userobject);
				i.putExtra("loan_obj", strFromDoInBg);
				startActivity(i);
				//moveTaskToBack(true);
			}
			else{
				Toast.makeText(getApplicationContext(), "Loan Application Not submited try after some time.", Toast.LENGTH_LONG).show();

			}

			pd.cancel();


		}
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager 
		= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	private void setloanrequested() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean("is_loan_applied",true);
		editor.commit();
	}

	public class GetLoanDetails extends AsyncTask<String, Void, LoanTypeDetailParser> {

		ProgressDialog pd;
		String result;
		public ArrayList<SpinnerModel> CustomListViewValuesArr = new ArrayList<SpinnerModel>();

		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(Edit_loan_request.this, "Getting loan details.... ",
					"Please Wait");
		}

		@Override
		protected LoanTypeDetailParser doInBackground(String... arg0) {SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 

		HttpGetLoanTypeDetail loandetail=new HttpGetLoanTypeDetail();
		LoanTypeDetailParser loanobj=loandetail.httpusergetloandetail(getApplicationContext(), userobject.getSessionid());
		return loanobj;

		}

		@Override
		protected void onPostExecute(LoanTypeDetailParser strFromDoInBg) {
			pd.dismiss();
			if (strFromDoInBg!=null) {
				try {


					if (strFromDoInBg.getResult_code().equalsIgnoreCase("200")) {

						ArrayList<LoanTypeList> list=strFromDoInBg.getLoan_typelist();
						if (list.size()>0) {
							//ArrayAdapter<String> adapter = new ArrayAdapter<String>(loan_activity.this, android.R.layout.simple_spinner_item,list);
							dao.insertloantypedetails(strFromDoInBg);
							for (int i = 0; i < list.size(); i++) {
								CustomListViewValuesArr.add(new SpinnerModel(list.get(i).getLoan_type_name(),list.get(i).getLoan_type_id()));

							}
							//adapter = new CustomAdapter(activity, R.layout.spinner_rows, CustomListViewValuesArr,res);
							//spinnerloantype.setAdapter(adapter);
							//Handler handler=new Handler();
							Handler handler1=new Handler();
							/*handler.postDelayed(new Runnable(){
								@Override
								public void run() {
									try {
										spinnerloantype.setSelection(1);


									} catch (Exception e) {
										// TODO: handle exception
										e.printStackTrace();
									}
								}
							}, 1000);*/
							handler1.postDelayed(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									if (loanobject!=null) {

										editTextloanamount.setText(loanobject.getLoan_amount());

										//editTextmygaurantee.setText(loanobject.get);
										if (loanobject.getGauranterlist()!=null&&loanobject.getGauranterlist().size()>0) {
											double total=0;
											for (int i = 0; i < loanobject.getGauranterlist().size(); i++) {

												LoanGauranterStatusList loangauranterstatuslist=loanobject.getGauranterlist().get(i);
												total=total+Double.parseDouble(loangauranterstatuslist.getGuarantorReserveValue());
												switch (i) {
												case 0:
													guaranter1msisdn.setText(loangauranterstatuslist.getGuarantorMsisdn());
													guaranter1amount.setText(loangauranterstatuslist.getGuarantorReserveValue());
													break;

												case 1:
													guaranter2msisdn.setText(loangauranterstatuslist.getGuarantorMsisdn());
													guaranter2amount.setText(loangauranterstatuslist.getGuarantorReserveValue());
													break;
												case 2:
													guaranter3msisdn.setText(loangauranterstatuslist.getGuarantorMsisdn());
													guaranter3amount.setText(loangauranterstatuslist.getGuarantorReserveValue());
													break;
												case 3:
													guaranter4msisdn.setText(loangauranterstatuslist.getGuarantorMsisdn());
													guaranter4amount.setText(loangauranterstatuslist.getGuarantorReserveValue());
													break;
												case 4:
													guaranter5msisdn.setText(loangauranterstatuslist.getGuarantorMsisdn());
													guaranter5amount.setText(loangauranterstatuslist.getGuarantorReserveValue());
													break;

												default:
													break;
												}

											}
											textViewtotal.setText("Total Gaurantee Amount is "+total);
										} 
										try {
											spinnerloantenure.setSelection(1);

										} catch (Exception e2) {
											e2.printStackTrace();
										}
									}
								}
							}, 2000);
						}
						else{

							showAlert("Unable to get loan deatail please try after some time", "loan");
						}
					} 
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}





		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();

		Intent i=new Intent(getApplicationContext(),Loanstatus.class);
		i.putExtra("user_obj", userobject);
		startActivity(i);
	}
	public class SubmitLoanApplicationEmergency extends AsyncTask<String, Void, LoanApplicationObject> {
		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(Edit_loan_request.this, "Proceeding ",
					"Please Wait");
		}

		@Override
		protected LoanApplicationObject doInBackground(String... arg0) {


			return new LoanApplicationObject(userobject.getUserDetails().getUserMobile(), String.valueOf(loanamount), mapgauranter, loantype, loantenure, userobject.getSessionid(), userobject.getUser_type(),String.valueOf(0));
		}

		@Override
		protected void onPostExecute(LoanApplicationObject strFromDoInBg) {
			if (strFromDoInBg!=null) {
				Intent i=new Intent(getApplicationContext(),MpinOtpVerificationLoan.class);
				i.putExtra("user_obj", userobject);
				i.putExtra("loan_obj", strFromDoInBg);
				startActivity(i);
				//moveTaskToBack(true);
			}
			else{
				Toast.makeText(getApplicationContext(), "Loan Application Not submited try after some time.", Toast.LENGTH_LONG).show();

			}

			pd.cancel();


		}
	}

	public void changeloantype(final SpinnerModel loantypeselectedmodel1 ){
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				scrollView1.clearAnimation();
				editTextmygaurantee.clearAnimation();
				SpinnerModel model=	loantypeselectedmodel1;
				loantype=model.getOther_desc();
				textViewloantype.setText(model.getLoan_desc());
				 loantypemodel=	dao.getloantypedetail(model.getOther_desc());
				System.out.println(loantypemodel.getLoan_type_name());
				if (loantypemodel!=null&&loantypemodel.getLoan_type_name().equalsIgnoreCase("Emergency")) {
					System.out.println("inside if");
					
					scrollView1.setVisibility(View.GONE);
					editTextmygaurantee.setVisibility(View.GONE);

				}
				else{
					System.out.println("inside else");

					scrollView1.setVisibility(View.VISIBLE);
					editTextmygaurantee.setVisibility(View.VISIBLE);
				}
				if (loantypemodel.getLoan_tenure()!=null) {
					ArrayList<SpinnerModel> arraylisttenure = new ArrayList<SpinnerModel>();
					arraylisttenure.add(new SpinnerModel("Please select loan tenure",""));

					String[] tenure=loantypemodel.getLoan_tenure().split(",");
					for (int i = 0; i < tenure.length; i++) {
						arraylisttenure.add(new SpinnerModel(tenure[i]+" Months",tenure[i]));

					}
					CustomAdapter adaptertenure = new CustomAdapter(activity, R.layout.spinner_rows, arraylisttenure,res);
					spinnerloantenure.setAdapter(adaptertenure);
				}
				String loanlimitformule=loantypemodel.getLoan_limit();
				ownreserveamountformule=loantypemodel.getOwn_reserve_amount();
				if (loanlimitformule!=null) {
					Pattern p = Pattern.compile(re1,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
					Matcher m = p.matcher(loanlimitformule);
					if (m.find())
					{
						String int1=m.group(1);
						try {
							loanlimit=Double.parseDouble(userobject.getUserDetails().getUserWalletbalance())*Double.parseDouble(int1);
							textViewloanlimit.setText("Your Loan Limit is "+loanlimit);

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							loanlimit=0;
							textViewloanlimit.setText("Your Loan Limit is "+loanlimit);

						}

					}
					else{
						loanlimit=0;
						textViewloanlimit.setText("Your Loan Limit is "+loanlimit);

					}
				}


			}
		});


	}

	void showloantypedialog(final ArrayList<SpinnerModel> model){
		LayoutInflater factory = LayoutInflater.from(this);
		final View previewDialogView = factory.inflate( R.layout.loan_type_dialog, null);
		final AlertDialog exitDialog = new AlertDialog.Builder(Edit_loan_request.this,R.style.DialogTheme).create();
		ListView listView1=(ListView)previewDialogView.findViewById(R.id.listView1);


		ListLoanTypeAdapter	adapter = new ListLoanTypeAdapter(getApplicationContext(),model);
		listView1.setAdapter(adapter);
		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				changeloantype(model.get(arg2));
				exitDialog.dismiss();
			}
		});
		exitDialog.setView(previewDialogView);
		exitDialog.setCancelable(false);


		exitDialog.show();
	}

	public class GetLoanDetails1 extends AsyncTask<String, Void, LoanTypeDetailParser> {

		ProgressDialog pd;
		String result;
		public ArrayList<SpinnerModel> CustomListViewValuesArr = new ArrayList<SpinnerModel>();

		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(Edit_loan_request.this, "Getting loan details.... ",
					"Please Wait");
		}

		@Override
		protected LoanTypeDetailParser doInBackground(String... arg0) {SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 

		HttpGetLoanTypeDetail loandetail=new HttpGetLoanTypeDetail();
		LoanTypeDetailParser loanobj=loandetail.httpusergetloandetail(getApplicationContext(), userobject.getSessionid());
		return loanobj;

		}

		@Override
		protected void onPostExecute(LoanTypeDetailParser strFromDoInBg) {
			pd.dismiss();
			if (strFromDoInBg!=null) {
				try {


					if (strFromDoInBg.getResult_code().equalsIgnoreCase("200")) {

						ArrayList<LoanTypeList> list=strFromDoInBg.getLoan_typelist();
						if (list.size()>0) {
							//ArrayAdapter<String> adapter = new ArrayAdapter<String>(loan_activity.this, android.R.layout.simple_spinner_item,list);
							dao.insertloantypedetails(strFromDoInBg);
							for (int i = 0; i < list.size(); i++) {
								CustomListViewValuesArr.add(new SpinnerModel(list.get(i).getLoan_type_name(),list.get(i).getLoan_type_id()));

							}

							showloantypedialog(CustomListViewValuesArr);
						}
						else{

							showAlert("Unable to get loan deatail please try after some time", "loan");
						}
					} 
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}





		}
	}
}
