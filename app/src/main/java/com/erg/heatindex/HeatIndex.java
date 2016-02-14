package com.erg.heatindex;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Vector;


public class HeatIndex extends Activity {
	private final String DEBUG_TAG = "HeadIndex";

	private double HIdouble = 0;

	private int myValid1 = 1;  //temp is number
	private int myValid2 = 1;    //humidity is number

	private int myValid11 = 1; //temp is in valid range
	private int myValid22 = 1; //humidity is in valid range

	private int dataValid = 1;    //if current temperature is lower than 80, dataValid=0

	public Hashtable myStates = new Hashtable();

	private ImageButton infoButton;

	private MenuItem myDoneMenu;
	private MenuItem myDOLMenu;

	private ViewFlipper viewFlipper;

	private boolean isSpanish = false;

	private ProgressBar spinner;

	//
	private int valueTemp = 0;  //will be displayed in the temperature box
	private int valueHumid = 0; //will be displayed in the humidity box
	private double valueCurrentHeatIndex = 0;     //feels like
	private double valueMaxHeatIndex = 0;       //max heat index
	private int myRiskLevel=0;
	private int myMaxRiskLevel=0;
	private String myMaxTime = "";   //store the time of the MAX


	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		final Context context = this;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);


		String myLanguage = Locale.getDefault().getDisplayLanguage();
		if (myLanguage.startsWith("es")){
			isSpanish = true;
		}

		//important, must define them final
		final EditText editLocation = ((EditText) findViewById(R.id.location));
		final EditText editText1 = ((EditText) findViewById(R.id.temp));
		final EditText editText2 = ((EditText) findViewById(R.id.humidity));

		final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		final WebView mWebView= (WebView) findViewById(R.id.mywebview);
		final WebView mWebViewMoreInfo= (WebView) findViewById(R.id.mywebviewMoreInfo);

		final ImageButton arrow1Button = (ImageButton) findViewById(R.id.arrow1);
		final ImageButton arrow2Button = (ImageButton) findViewById(R.id.arrow2);
		final ImageButton getCurrentLocationButton = (ImageButton) findViewById(R.id.arrow3);

		final TextView myRisk1 = (TextView) findViewById(R.id.risk);
		final TextView myRisk2 = (TextView) findViewById(R.id.risk2);


		spinner = (ProgressBar)findViewById(R.id.progressBar1);

		//final RelativeLayout dayMaxBlock = (RelativeLayout) findViewById(R.id.maxResults);

		//hide webview
		mWebView.setVisibility(View.GONE);
		mWebViewMoreInfo.setVisibility(View.GONE);

		//hide some areas
		arrow1Button.setVisibility(View.GONE);
		//dayMaxBlock.setVisibility(View.GONE);

		// get action bar
		ActionBar actionBar = getActionBar();

		//set Title to "Head Index"
		actionBar.setTitle(getString(R.string.app_name_long));
		actionBar.setDisplayShowTitleEnabled(true);

		//enabling home button
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);

		//set up the hashtable for state list
		myStates.put("Alabama", "AL");
		myStates.put("Alaska", "AK");
		myStates.put("Arizona", "AZ");
		myStates.put("Arkansas", "AR");
		myStates.put("California", "CA");
		myStates.put("Colorado", "CO");
		myStates.put("Connecticut", "CT");
		myStates.put("Delaware", "DE");
		myStates.put("Florida", "FL");
		myStates.put("Georgia", "GA");
		myStates.put("Hawaii", "HI");
		myStates.put("Idaho", "ID");
		myStates.put("Illinois", "IL");
		myStates.put("Indiana", "IN");
		myStates.put("Iowa", "IA");
		myStates.put("Kansas", "KS");
		myStates.put("Kentucky", "KY");
		myStates.put("Louisiana", "LA");
		myStates.put("Maine", "ME");
		myStates.put("Maryland", "MD");
		myStates.put("Massachusetts", "MA");
		myStates.put("Michigan", "MI");
		myStates.put("Minnesota", "MN");
		myStates.put("Mississippi", "MS");
		myStates.put("Missouri", "MO");
		myStates.put("Montana", "MT");
		myStates.put("Nebraska", "NE");
		myStates.put("Nevada", "NV");
		myStates.put("New Hampshire", "NH");
		myStates.put("New Jersey", "NJ");
		myStates.put("New Mexico", "NM");
		myStates.put("New York", "NY");
		myStates.put("North Carolina", "NC");
		myStates.put("North Dakota", "ND");
		myStates.put("Ohio", "OH");
		myStates.put("Oklahoma", "OK");
		myStates.put("Oregon", "OR");
		myStates.put("Pennsylvania", "PA");
		myStates.put("Rhode Island", "RI");
		myStates.put("South Carolina", "SC");
		myStates.put("South Dakota", "SD");
		myStates.put("Tennessee", "TN");
		myStates.put("Texas", "TX");
		myStates.put("Utah", "UT");
		myStates.put("Vermont", "VT");
		myStates.put("Virginia", "VA");
		myStates.put("Washington", "WA");
		myStates.put("West Virginia", "WV");
		myStates.put("Wisconsin", "WI");
		myStates.put("Wyoming", "WY");
		myStates.put("District of Columbia", "D.C.");


		//calculate current location info
		String myLocation = getCurrentLocationName();
		editLocation.setText(myLocation.toString());
		getCalculateDataByEnteredAddressStr();
		spinner.setVisibility(View.GONE);

		//new MyAsyncTask(spinner).execute();

		//making location read only, it will pop up soft keyboard as well.
		editLocation.setInputType(InputType.TYPE_CLASS_TEXT);
		//editLocation.setInputType(InputType.TYPE_NULL);

		//existing version of IPHONE
		//can not edit location
		//after clicking, it will load the current address again
		editLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editLocation.setText("");
			}
		});


		editLocation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				int result = actionId & EditorInfo.IME_MASK_ACTION;

				try {
					switch (result) {
						//case EditorInfo.IME_ACTION_NEXT:
						case EditorInfo.IME_ACTION_GO:
							//to close soft keyboard
							imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

							String myEnteredLocation2 = getLocationByAddress(editLocation.getText().toString());
							editLocation.setText(myEnteredLocation2.toString());


							if (myEnteredLocation2.toString() == getString(R.string.txtNotFound)) {
								cleanUIforMainPage();
								//hide progress
								//spinner.setVisibility(View.GONE);
							} else {
								//go to calculate this entered address
								getCalculateDataByEnteredAddressStr();
								//hide progress
								//spinner.setVisibility(View.GONE);
							}
							break;
					}
					return true;
				} catch (Exception e) {
					Log.e(DEBUG_TAG, "NOAA failed", e);
					return false;
				}
			} //end of function

		});

		//manual data entry
		editText1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				int result = actionId & EditorInfo.IME_MASK_ACTION;
				switch (result) {
					case EditorInfo.IME_ACTION_DONE:
						//calling Manual Data Entry action
						manualDataAction();
						editLocation.setText(getString(R.string.txtForManualData));
						//end of real action
						break;
					case EditorInfo.IME_ACTION_NEXT:
						// next stuff
						break;
				}
				return true;
			}
		});

		//manual data entry
		editText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				int result = actionId & EditorInfo.IME_MASK_ACTION;
				switch (result) {
					case EditorInfo.IME_ACTION_DONE:
						//calling Manual Data Entry action
						manualDataAction();
						editLocation.setText(getString(R.string.txtForManualData));
						//end of real action
						break;
					case EditorInfo.IME_ACTION_NEXT:
						// next stuff
						break;
				}
				return true;
			}
		});

		//information about this app
		infoButton = (ImageButton) findViewById(R.id.info);
		infoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Show the next Screen
				viewFlipper.showNext();

				//displaying custom ActionBar
				ActionBar actionBar = getActionBar();
				View mActionBarView = getLayoutInflater().inflate(R.layout.my_action_bar, null);
				actionBar.setCustomView(mActionBarView);
				actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
				actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

				resetMyCustomizedButtonsOnActionBar(3);
				TextView myTitle = (TextView) findViewById(R.id.myTitle);
				myTitle.setText(getString(R.string.txtForTitleMoreInfo));

				myDoneMenu.setVisible(true);
				myDOLMenu.setVisible(false);
			}
		});

		myRisk1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (myRiskLevel>0) {
					//to close soft keyboard
					imm.hideSoftInputFromWindow(myRisk1.getWindowToken(), 0);
					goCurrentPrecautionView();
				}
			}
		});

		myRisk2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (myMaxRiskLevel>0) {
					imm.hideSoftInputFromWindow(myRisk2.getWindowToken(), 0);
					goMaxPrecautionView();
				}
			}
		});

		arrow1Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				imm.hideSoftInputFromWindow(arrow1Button.getWindowToken(), 0);
				goCurrentPrecautionView();
			}
		});


		arrow2Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				imm.hideSoftInputFromWindow(arrow2Button.getWindowToken(),0);
				goMaxPrecautionView();
			}
		});


		getCurrentLocationButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String myLocation = getCurrentLocationName();
				editLocation.setText(myLocation.toString());
				getCalculateDataByEnteredAddressStr();

				// hide soft keyboard
				View view = getCurrentFocus();
				if (view != null) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
				}
			}
		});
	} //end of function OnCreate



	public void goCurrentPrecautionView(){

		WebView mWebView= (WebView) findViewById(R.id.mywebview);
		mWebView.setVisibility(View.VISIBLE);

		//hiding default app icon
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);

		//displaying custom ActionBar
		View mActionBarView = getLayoutInflater().inflate(R.layout.my_action_bar, null);
		actionBar.setCustomView(mActionBarView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		TextView myTitle = (TextView) findViewById(R.id.myTitle);
		myTitle.setText(getString(R.string.txtForTitlePrecautions));

		resetMyCustomizedButtonsOnActionBar(1);


		if (myRiskLevel == 4) {
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 0, 0)));
			if (isSpanish) {
				mWebView.loadUrl("file:///android_asset/precautions_veryhigh_es.html");
			}
			else{
				mWebView.loadUrl("file:///android_asset/precautions_veryhigh.html");
			}
		}
		if (myRiskLevel == 3) {
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 128, 0)));
			if (isSpanish) {
				mWebView.loadUrl("file:///android_asset/precautions_high_es.html");
			}
			else{
				mWebView.loadUrl("file:///android_asset/precautions_high.html");
			}
		}
		if (myRiskLevel == 2) {
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255,172,0)));
			if (isSpanish) {
				mWebView.loadUrl("file:///android_asset/precautions_moderate_es.html");
			}
			else{
				mWebView.loadUrl("file:///android_asset/precautions_moderate.html");
			}
		}
		if (myRiskLevel == 1) {
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 255, 51)));
			if (isSpanish) {
				mWebView.loadUrl("file:///android_asset/precautions_lower_es.html");
			}
			else{
				mWebView.loadUrl("file:///android_asset/precautions_lower.html");
			}
		}

		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setSaveFormData(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.setWebViewClient(new MyWebViewClient());

		myDoneMenu.setVisible(false);
		myDOLMenu.setVisible(false);

		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setIcon(android.R.color.transparent);
	}

	public void goMaxPrecautionView() {
		WebView mWebView = (WebView) findViewById(R.id.mywebview);
		mWebView.setVisibility(View.VISIBLE);

		ActionBar actionBar = getActionBar();

		//hiding default app icon
		actionBar.setDisplayShowHomeEnabled(false);

		//displaying custom ActionBar
		View mActionBarView = getLayoutInflater().inflate(R.layout.my_action_bar, null);
		actionBar.setCustomView(mActionBarView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		resetMyCustomizedButtonsOnActionBar(1);
		TextView myTitle = (TextView) findViewById(R.id.myTitle);
		myTitle.setText(getString(R.string.txtForTitlePrecautions));

		if (myMaxRiskLevel == 4) {
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 0, 0)));
			if (isSpanish) {
				mWebView.loadUrl("file:///android_asset/precautions_veryhigh_es.html");
			}
			else{
				mWebView.loadUrl("file:///android_asset/precautions_veryhigh.html");
			}
		}
		if (myMaxRiskLevel == 3) {
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 128, 0)));
			if (isSpanish) {
				mWebView.loadUrl("file:///android_asset/precautions_high_es.html");
			}
			else{
				mWebView.loadUrl("file:///android_asset/precautions_high.html");
			}
		}
		if (myMaxRiskLevel == 2) {
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255,172,0)));
			if (isSpanish) {
				mWebView.loadUrl("file:///android_asset/precautions_moderate_es.html");
			}
			else{
				mWebView.loadUrl("file:///android_asset/precautions_moderate.html");
			}
		}
		if (myMaxRiskLevel == 1 || myMaxRiskLevel == 0) {
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 255, 51)));
			if (isSpanish) {
				mWebView.loadUrl("file:///android_asset/precautions_lower_es.html");
			}
			else{
				mWebView.loadUrl("file:///android_asset/precautions_lower.html");
			}
		}

		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setSaveFormData(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.setWebViewClient(new MyWebViewClient());

		myDoneMenu.setVisible(false);
		myDOLMenu.setVisible(false);

		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setIcon(android.R.color.transparent);
	}



	/* get geo info of current place */
	public double getLocationLatitude() {
		Log.d(DEBUG_TAG, " --- getLocationLatitude - BEGIN");

		try {
			LocationManager locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

			Location location = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (location == null) {
				location = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			}

			if (location != null) {
				Log.d(DEBUG_TAG, " --- getLocationLatitude - END");
				return location.getLatitude();
			} else {
				Log.i(DEBUG_TAG, "Location failed - no provider");
				Log.d(DEBUG_TAG, " --- getLocationLatitude - END");
				return 0;
			}
		} catch (Exception e) {
			Log.e(DEBUG_TAG, "Location failed", e);
			Log.d(DEBUG_TAG, " --- getLocationLatitude - END");
			return 0;
		}
	}

	//default  place
	public double getLocationLongitude() {
		Log.d(DEBUG_TAG, " --- getLocationLongitude - BEGIN");

		try {
			LocationManager locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

			Location location = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (location == null) {
				location = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			}

			if (location != null) {
				//   onLocationChanged(location);
				double myTest = location.getLongitude();
				Log.d(DEBUG_TAG, " --- getLocationLongitude - END");
				return location.getLongitude();
			} else {
				Log.i(DEBUG_TAG, "Location failed Hui - no provider");
				Log.d(DEBUG_TAG, " --- getLocationLongitude - END");
				return 0;
			}
		} catch (Exception e) {
			Log.e(DEBUG_TAG, "Location failed", e);
			Log.d(DEBUG_TAG, " --- getLocationLongitude - END");
			return 0;
		}
	}
/* end of get geo info of current place */


	/* get geo info of entered address */
	public double getLocationLatitudeByEnteredAddress(String addressStr) {
		Log.d(DEBUG_TAG, " --- getLocationLatitudeByEnteredAddress - BEGIN");

		Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
		double retValue = 0;
		try {
			List<Address> addresses = gcd.getFromLocationName(addressStr, 10);

			for (Address adrs : addresses) {
				if (adrs != null) {
					retValue = adrs.getLatitude();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.d(DEBUG_TAG, " --- getLocationLatitudeByEnteredAddress - END");
		return retValue;
	}

	public double getLocationLongitudeByEnteredAddress(String addressStr) {
		Log.d(DEBUG_TAG, " --- getLocationLongitudeByEnteredAddress - BEGIN");

		Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
		double retValue = 0;
		try {

			List<Address> addresses = gcd.getFromLocationName(addressStr, 10);

			for (Address adrs : addresses) {
				if (adrs != null) {
					retValue = adrs.getLongitude();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.d(DEBUG_TAG, " --- getLocationLongitudeByEnteredAddress - END");
		return retValue;
	}
	/* end of get geo info of entered address */


	public String getLocationByAddress(String addressStr) {
		Log.d(DEBUG_TAG, " --- getLocationByAddress - BEGIN");

		String cityName = getString(R.string.txtNotFound);
		Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
		try {

			List<Address> addresses = gcd.getFromLocationName(addressStr, 10);

			for (Address adrs : addresses) {
				if (adrs != null) {

					String city = adrs.getLocality();
					String stateStr = adrs.getAdminArea();
					Object stateObj = stateStr;
					Object stateAb = myStates.get(stateObj);

					if (city != null && !city.equals("")) {
						if (stateAb != null) {
							cityName = city + " " + stateAb.toString();
						}
						else {
							cityName = city + " " + stateStr;
						}
						break;
					} else {
					}
					//cityName = city + " " + adrs.getAdminArea();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.d(DEBUG_TAG, " --- getLocationByAddress - END");
		return cityName;
	}


	public String getCurrentLocationName() {
		Log.d(DEBUG_TAG, " --- getCurrentLocationName - BEGIN");

		String cityName = getString(R.string.txtNotFound);
		Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());

		double myLongitude = getLocationLongitude();
		double myLatitude = getLocationLatitude();

		try {
			List<Address> addresses = gcd.getFromLocation(myLatitude, myLongitude, 10);

			for (Address adrs : addresses) {
				if (adrs != null) {

					String city = adrs.getLocality();
					String stateStr = adrs.getAdminArea();
					Object stateObj = stateStr;
					Object stateAb = myStates.get(stateObj);
					if (city != null && !city.equals("")) {
						cityName = city + ", " + stateAb.toString();
						break;
					} else {

					}
					// you should also try with addresses.get(0).toSring();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.d(DEBUG_TAG, " --- getCurrentLocationName - END");
		return cityName;
	}


	public double getHeadIndexFromNoaaData(ParsedDataSetWeather parsedExampleDataSet) {
		Log.d(DEBUG_TAG, " --- getHeadIndexFromNoaaData - BEGIN");

		try {
			dataValid = 1;

			//get today's date
			Calendar rightNow = Calendar.getInstance();
			int myDay = rightNow.get(Calendar.DAY_OF_MONTH);

			//get current hour
			int myHour = rightNow.get(Calendar.HOUR_OF_DAY);

			//the size of the data for today's calculation
			int myVecSize = 24 - myHour;

			//temperature vector, humid vector and time vector for storing the data from XML
			Vector myTempVec = parsedExampleDataSet.gettemperature();
			Vector myHumidVec = parsedExampleDataSet.gethumidity();
			Vector myTimeVec = parsedExampleDataSet.getmaxtime();

			//Log.d(DEBUG_TAG, "Temperature Vec size " + myTempVec.toString() );
			//Log.d(DEBUG_TAG, "Humidity Vec size    " + myHumidVec.toString() );
			//Log.d(DEBUG_TAG, "Time Vec size        " + myTimeVec.toString() );

			Log.d(DEBUG_TAG, " --- Temperature Vec size " + myTempVec.size() );
			Log.d(DEBUG_TAG, " --- Humidity Vec size    " + myHumidVec.size() );
			Log.d(DEBUG_TAG, " --- Time Vec size        " + myTimeVec.size() );

			//System.out.println(myTempVec.toString());
			//System.out.println(myHumidVec.toString());
			//System.out.println(myTimeVec.toString());

			//results HeatIndex Array
			double[] myHIarray = new double[myTempVec.size()];

			//put vector values to array for calculation later
			int[] myTempArray = new int[myTempVec.size()];
			for (int i = 0; i < myTempVec.size(); i++) {
				String val = (String) myTempVec.get(i);

				//Log.d(DEBUG_TAG, " Temp " + i + " " + val);

				myTempArray[i] = stringToInt(val);  //Integer.parseInt(val);


			}

			int[] myHumidArray = new int[myHumidVec.size()];
			for (int i = 0; i < myHumidVec.size(); i++) {
				String val = (String) myHumidVec.get(i);

				//Log.d(DEBUG_TAG, " Humid " + i + " " + val);

				myHumidArray[i] = stringToInt(val); //Integer.parseInt(val);
			}

			//the following code is handling the cache in the getting weather data
			//pick up the data for current hour + 1
			//if there is cache, skip those cached values
			int myCurrentIndex = 0;      //the index of the first data for our calculation
			for (int i = 0; i < myTempVec.size(); i++) {
				String val = (String) myTimeVec.get(i);

				//Log.d(DEBUG_TAG, " Time " + i + " " + val);

				String getDay = val.substring(8, 10);
				String getTime = val.substring(11, 13);

				//if (myDay == Integer.parseInt(getDay) && myHour < Integer.parseInt(getTime)) {
				if (myDay == stringToInt(getDay) && myHour < stringToInt(getTime)) {
					myCurrentIndex = i;
					break;
				}
			}

			//the useful data for our calculation
			//excluding the cached data for previous days/hours and data for future days
			int[] myTempArray2 = new int[myVecSize];
			for (int i = 0; i < myVecSize; i++) {
				myTempArray2[i] = myTempArray[i + myCurrentIndex];
			}

			int[] myHumidArray2 = new int[myVecSize];
			for (int i = 0; i < myVecSize; i++) {
				myHumidArray2[i] = myHumidArray[i + myCurrentIndex];
			}

			int size = myTempArray2.length;

			//calculate heat index for each hour and put them in myHIarray
			for (int i = 0; i < size; i++) {
				myHIarray[i] = calIndex.heatIndexCal(myTempArray2[i], myHumidArray2[i]);
			}

			double myValue = 0;   //variable for storing the HI value shown on the screen later

			//current value is calculated
			valueCurrentHeatIndex = 0;
			if (size > 0) {
				valueTemp = myTempArray2[0];
				valueHumid = myHumidArray2[0];
				myValue = myHIarray[0];
				valueCurrentHeatIndex = myValue;
				if (valueTemp < 80) {
					dataValid = 0;    //too low
				}
			}

			//find the max for today
			valueMaxHeatIndex = 0;
			int mycount = 0;    //index for the max HeatIndex
			if (size > 0) {
				for (int i = 0; i < size; i++) {
					if (i == 0) {
						myValue = myHIarray[i];
						mycount = 0;
					} else {
						if (myValue < myHIarray[i]) {
							myValue = myHIarray[i];
							mycount++;
						}
					}
				}
				valueMaxHeatIndex = myValue;
			}


			//comment out 2015
			//new code, check day max set
			//if all the temps are lower than 80, then it is datavalid=0
			int myCountMax = 0;
			if (size > 0) {
				for (int i = 0; i < size; i++) {
					if (myTempArray2[i] < 80) {
						myCountMax = myCountMax + 1;
					}
				}

				if (myCountMax == size) {
					dataValid = 0;   //invalid, pick the current temp and humidity
					valueTemp = myTempArray2[0];
					valueHumid = myHumidArray2[0];
				}
			}


			if (size > 0) {
				String myMaxTimeStr = "";
				myMaxTimeStr = (String) myTimeVec.get(mycount + myCurrentIndex);
				//sample format from XML 2011-06-09T17:00:00-04:00
				myMaxTimeStr = myMaxTimeStr.substring(11, 13);

				//format to 8am, 2pm etc
				int intHourValue;
				intHourValue = stringToInt(myMaxTimeStr); // Integer.parseInt(myMaxTimeStr);
				String ampm = "AM";

				if (intHourValue < 10) {
					myMaxTimeStr = myMaxTimeStr.substring(1);
					myMaxTimeStr = myMaxTimeStr + " " + "AM";
				} else {
					if (intHourValue > 12) {
						intHourValue = intHourValue - 12;
						ampm = "PM";
					}
					if (intHourValue == 12) {
						ampm = "PM";
					}

					myMaxTimeStr = String.valueOf(intHourValue) + ":00 " + ampm;
					String strForAt = getString(R.string.txtForAt);
					myMaxTimeStr = strForAt + " " + myMaxTimeStr;
				}

				myMaxTime = myMaxTimeStr;

				if (dataValid == 0) {
					myValue = 1;     //myValue=1 means we got the weather data, but it is too low and not valid for HI calculation
				}
			} else {
				myValue = 0;   //error in getting NOAA data
				myMaxTime = "";
			}

			Log.d(DEBUG_TAG, " --- getHeadIndexFromNoaaData - END");
			return myValue;
		} catch (Exception e) {
			Log.e(DEBUG_TAG, "NOAA failed", e);
			Log.d(DEBUG_TAG, " --- getHeadIndexFromNoaaData - END");
			return 0;
		}
	}


	public int stringToInt(String inValue) {
		int retValue = 0;

		try {

			retValue = Integer.parseInt(inValue);

		} catch (Exception e) {
			Log.e(DEBUG_TAG, "Integer Parser error ", e);
		}

		return retValue;
	}


	//this method is used for NOT very low = having precautions cases
	public void setUIforResults(double HIdoubleP) {
		//around up the HI result
		long myResultValue = Math.round(HIdoubleP);
		Long myResult = new Long(myResultValue);

		TextView myHI_Result = (TextView) findViewById(R.id.feelsLike);
		TextView myRisk_Result = (TextView) findViewById(R.id.risk);
		ImageButton arrow1Button = (ImageButton) findViewById(R.id.arrow1);

		RelativeLayout myRisk_Result_Big_Background = (RelativeLayout) findViewById(R.id.risk_result_big_background);
		ActionBar actionBar = getActionBar();

		String myLabelStr = getString(R.string.labelForFeelsLike);

		myHI_Result.setText(myLabelStr + " " + myResult.toString() + " °F");
		myHI_Result.setContentDescription(myLabelStr + myResult.toString() + " °fahrenheit");

		myRiskLevel = 0;
		if (HIdoubleP > 115) {
			myRisk_Result.setText(getString(R.string.txtForVeryHightoExtremeRisk));
			myRisk_Result.setContentDescription(getString(R.string.txtForVeryHightoExtremeRisk));
			//set the big background
			myRisk_Result_Big_Background.setBackgroundColor(Color.rgb(255, 0, 0));
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 0, 0)));

			myRiskLevel = 4;
		}
		if (HIdoubleP > 103 && HIdoubleP <= 115) {
			myRisk_Result.setText(getString(R.string.txtForHighRisk));
			myRisk_Result.setContentDescription(getString(R.string.txtForHighRisk));
			//set the big background
			myRisk_Result_Big_Background.setBackgroundColor(Color.rgb(255, 128, 0));
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 128, 0)));

			myRiskLevel = 3;
		}
		if (HIdoubleP >= 91 && HIdoubleP <= 103) {
			myRisk_Result.setText(getString(R.string.txtForModerateRisk));
			myRisk_Result.setContentDescription(getString(R.string.txtForModerateRisk));
			//set the big background
			myRisk_Result_Big_Background.setBackgroundColor(Color.rgb(255,172,0));
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255,172,0)));

			myRiskLevel = 2;
		}
		if (HIdoubleP < 91) {
			myRisk_Result.setText(getString(R.string.txtForLowerRisk));
			myRisk_Result.setContentDescription(getString(R.string.txtForLowerRisk));
			//set the big background
			myRisk_Result_Big_Background.setBackgroundColor(Color.rgb(255, 255, 51));
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 255, 51)));

			myRiskLevel = 1;
		}

		arrow1Button.setVisibility(View.VISIBLE);
	}

	//set max area
	public void setUIforMaxResult(double HIdoubleP) {
		RelativeLayout dayMaxBlock = (RelativeLayout) findViewById(R.id.maxResults);
		Double myResult = new Double(HIdoubleP);

		TextView maxText = (TextView) findViewById(R.id.max);
		TextView maxRisk = (TextView) findViewById(R.id.risk2);
		TextView maxTimeResult = (TextView) findViewById(R.id.time);
		ImageButton arrow2Button = (ImageButton) findViewById(R.id.arrow2);

		dayMaxBlock.setVisibility(View.VISIBLE);

		maxText.setVisibility(View.VISIBLE);
		maxRisk.setVisibility(View.VISIBLE);
		maxTimeResult.setVisibility(View.VISIBLE);
		arrow2Button.setVisibility(View.VISIBLE);

		myMaxRiskLevel = 0;

		if (HIdoubleP > 80) {
			if (HIdoubleP > 115) {
				maxRisk.setText(getString(R.string.txtForVeryHightoExtremeRisk));
				maxRisk.setContentDescription(getString(R.string.txtForVeryHightoExtremeRisk));

				myMaxRiskLevel = 4;
			}
			if (HIdoubleP > 103 && HIdoubleP <= 115) {
				maxRisk.setText(getString(R.string.txtForHighRisk));
				maxRisk.setContentDescription(getString(R.string.txtForHighRisk));
				myMaxRiskLevel = 3;
			}
			if (HIdoubleP >= 91 && HIdoubleP <= 103) {
				maxRisk.setText(getString(R.string.txtForModerateRisk));
				maxRisk.setContentDescription(getString(R.string.txtForModerateRisk));
				myMaxRiskLevel = 2;
			}
			if (HIdoubleP < 91) {
				maxRisk.setText(getString(R.string.txtForLowerRisk));
				maxRisk.setContentDescription(getString(R.string.txtForLowerRisk));
				myMaxRiskLevel = 1;
			}

			maxTimeResult.setText(myMaxTime);


		} else {
			maxRisk.setText(getString(R.string.txtForCurrentMinimalRisk));
			maxTimeResult.setVisibility(View.GONE);
			arrow2Button.setVisibility(View.GONE);
		}
	}

	// reset middle Current area
	public void cleanUIforResults() {
		((TextView) findViewById(R.id.now)).setText("");

		TextView myHI_Result = (TextView) findViewById(R.id.feelsLike);
		TextView myRisk_Result = (TextView) findViewById(R.id.risk);
		myRisk_Result.setText("");
		myHI_Result.setText("");
	}

	// reset max area
	public void cleanUIforMaxArea() {
		TextView maxText = (TextView) findViewById(R.id.max);
		TextView maxRisk = (TextView) findViewById(R.id.risk2);
		TextView maxTimeResult = (TextView) findViewById(R.id.time);
		ImageButton arrow2Button = (ImageButton) findViewById(R.id.arrow2);

		maxText.setVisibility(View.GONE);
		maxRisk.setVisibility(View.GONE);
		maxTimeResult.setVisibility(View.GONE);
		arrow2Button.setVisibility(View.GONE);
	}

	//reset the whole page
	//It is called when the entered location can be found.
	public void cleanUIforMainPage() {
		EditText editText1 = ((EditText) findViewById(R.id.temp));
		EditText editText2 = ((EditText) findViewById(R.id.humidity));
		editText1.setText("");
		editText2.setText("");

		// reset middle NOW area
		TextView myHI_Result = (TextView) findViewById(R.id.feelsLike);
		TextView myRisk_Result = (TextView) findViewById(R.id.risk);
		ImageButton arrow1Button = ((ImageButton) findViewById(R.id.arrow1));

		((TextView) findViewById(R.id.now)).setText("");
		myRisk_Result.setText("");
		myRisk_Result.setContentDescription("");
		myHI_Result.setText("");
		myHI_Result.setContentDescription("");
		arrow1Button.setVisibility(View.GONE);

		cleanUIforMaxArea();
	}


	//new method for showing temp and humidity when current temp is too low or all day is too low.
	public void setUIforCurrentDataTooLow() {
		EditText editText1 = ((EditText) findViewById(R.id.temp));
		EditText editText2 = ((EditText) findViewById(R.id.humidity));
		ImageButton arrow1Button = ((ImageButton) findViewById(R.id.arrow1));

		RelativeLayout myRisk_Result_Big_Background = (RelativeLayout) findViewById(R.id.risk_result_big_background);

		//reset the input fields
		editText1.setText(Integer.toString(valueTemp));
		editText2.setText(Integer.toString(valueHumid));

		//set risk level
		TextView myHI_Result = (TextView) findViewById(R.id.feelsLike);
		TextView myRisk_Result = (TextView) findViewById(R.id.risk);

		myRisk_Result.setText(getString(R.string.txtForCurrentMinimalRisk));
		myRisk_Result.setContentDescription(getString(R.string.txtForCurrentMinimalRisk));
		myHI_Result.setText("");
		myRisk_Result_Big_Background.setBackgroundColor(Color.WHITE);  //set the default gray color
		arrow1Button.setVisibility(View.GONE);

		//set the top action bar to white
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

		myRiskLevel = 0;
	}


	public void setUIforManualLow() {
		//set risk level
		TextView myHI_Result = (TextView) findViewById(R.id.feelsLike);
		TextView myRisk_Result = (TextView) findViewById(R.id.risk);
		RelativeLayout myRisk_Result_Big_Background = (RelativeLayout) findViewById(R.id.risk_result_big_background);
		TextView textMiddle = ((TextView) findViewById(R.id.now));
		ImageButton arrow1Button = ((ImageButton) findViewById(R.id.arrow1));

		myRisk_Result.setText(getString(R.string.txtForCurrentMinimalRisk));
		myRisk_Result.setContentDescription(getString(R.string.txtForCurrentMinimalRisk));
		myHI_Result.setText("");
		textMiddle.setText(getString(R.string.labelForCalculated));
		myRisk_Result_Big_Background.setBackgroundColor(Color.WHITE);  //set the default gray color
		arrow1Button.setVisibility(View.GONE);

		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

		myRiskLevel = 0;
	}

	//new 2015
	//manual data will not call NOAA
	//will not display max area
	private void manualDataAction() {
		cleanUIforResults();
		cleanUIforMaxArea();

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		myValid1 = 1;
		myValid2 = 1;

		myValid11 = 1;
		myValid22 = 1;

		//Hide Soft Keyboard
		imm.hideSoftInputFromWindow(((EditText) findViewById(R.id.humidity)).getWindowToken(), 0);
		//---end

		try {

			myMaxTime = "";

			/* Get what user typed to the EditText. */
			String input1 = ((EditText) findViewById(R.id.temp))
					.getText().toString();
			String input2 = ((EditText) findViewById(R.id.humidity))
					.getText().toString();

			if (Double.parseDouble(input1) < 80) {
				myValid11 = 0;
			}

			if (myValid11 == 1 && myValid22 == 1) {
				int d1 = Integer.parseInt(input1);
				int d2 = Integer.parseInt(input2);
				double myValue;
				myValue = calIndex.heatIndexCal(d1, d2);
				HIdouble = myValue;

				setUIforResults(HIdouble);
				TextView textMiddle = ((TextView) findViewById(R.id.now));
				textMiddle.setText(getString(R.string.labelForCalculated));
			} else {
				if (myValid1 == 1 && myValid2 == 1 && myValid11 == 0) {
					setUIforManualLow();
				}
			}



		} catch (Exception e) {
			Log.e(DEBUG_TAG, "WeatherQueryError", e);
		}
	}


	//user enters an address
	private void getCalculateDataByEnteredAddressStr() {
		Log.d(DEBUG_TAG, " --- getCalculateDataByEnteredAddressStr - BEGIN");

		EditText editLocation = ((EditText) findViewById(R.id.location));
		EditText editText1 = ((EditText) findViewById(R.id.temp));
		EditText editText2 = ((EditText) findViewById(R.id.humidity));
		TextView textMiddle = ((TextView) findViewById(R.id.now));

		cleanUIforResults();
		cleanUIforMaxArea();

		try {
			//clean the input fields
			editText1.setText("");
			editText2.setText("");
			myMaxTime = "";

			String myEnteredLocation = getLocationByAddress(editLocation.getText().toString());

			if (myEnteredLocation == getString(R.string.txtNotFound)) {
				new AlertDialog.Builder(editText1.getContext())
						.setTitle("Notification")
						.setMessage("We can not find the address you entered. Please verify it.")
						.setNeutralButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {

							}
						})
						.show();
			} else {
				double myLat = getLocationLatitudeByEnteredAddress(editLocation.getText().toString());
				double myLong = getLocationLongitudeByEnteredAddress(editLocation.getText().toString());

				//hardcoded gas info for testing
				//String myLat = "42.46";
				//String myLong = "-71.25";

				//build url
				String myUrl = "http://forecast.weather.gov/MapClick.php?lat=" + myLat + "&lon=" + myLong + "&FcstType=digitalDWML";

				//trigger an AsyncTask for getting noaa data
				new ProcessXML(this, myUrl).execute();
			}    //end of !"Not Found"
		}   //end of try
		catch (Exception e) {
			 Log.e(DEBUG_TAG, "WeatherQueryError", e);
		}

		Log.d(DEBUG_TAG, " --- getCalculateDataByEnteredAddressStr - END");
	} //end of function


    //callback  from AsyncTask
	public void callBackData(ParsedDataSetWeather myData) {
		Log.d(DEBUG_TAG, " --- callBackData - BEGIN");

		double myValue;
		myValue = getHeadIndexFromNoaaData(myData);

		EditText editText1 = ((EditText) findViewById(R.id.temp));
		EditText editText2 = ((EditText) findViewById(R.id.humidity));
		TextView textMiddle = ((TextView) findViewById(R.id.now));

		cleanUIforResults();
		cleanUIforMaxArea();
		try {
				if (myValue == 0) {
					new AlertDialog.Builder(editText1.getContext())
							.setTitle("Notification")
							.setMessage("The NOAA weather data are currently not available, please enter your temperature and relative humidity manually.")
							.setNeutralButton("OK", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {

								}
							})
							.show();
				} else {
					if (dataValid == 0) {   //less than 80
						setUIforCurrentDataTooLow();
						textMiddle.setText(getString(R.string.labelForNow));

						setUIforMaxResult(valueMaxHeatIndex);
					} else {
						setUIforResults(valueCurrentHeatIndex );
						textMiddle.setText(getString(R.string.labelForNow));

						//reset the input fields
						editText1.setText(Integer.toString(valueTemp));
						editText2.setText(Integer.toString(valueHumid));


						setUIforMaxResult(valueMaxHeatIndex);
					}
				}

			//Show the Toast for information notification
			showToast();

		}   //end of try
		catch (Exception e) {
			Log.e(DEBUG_TAG, "WeatherQueryError", e);
		}

		Log.d(DEBUG_TAG, " --- callBackData - END");
	} //end of function


     /* Some preparation for the APP and Menu actions.
     */
	/**
	 * Add the Actions to the Action Bar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/*
	Prepare the Menu when the page is loaded
	We hide/show the Done button on demand.
	 */
	public boolean onPrepareOptionsMenu(Menu menu){
		myDoneMenu = menu.findItem(R.id.action_done);
		myDoneMenu.setVisible(false);
		myDOLMenu = menu.findItem(R.id.action_dol);
		myDOLMenu.setVisible(true);
		return true;
	}


	/*
	reset Title Area.
	true - show home, title, dol logo menu etc
	false - show Done menu
	 */
	public boolean resetAppTitleArea(boolean isShow){
		if (isShow) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setDisplayUseLogoEnabled(true);
			myDOLMenu.setVisible(true);
			myDoneMenu.setVisible(false);
		}
		else{
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayUseLogoEnabled(false);
			myDOLMenu.setVisible(false);
			myDoneMenu.setVisible(true);
		}
		return true;
	}


	/*
	// To handle "Back" key press event for WebView to go back to previous screen.
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		final WebView mWebView= (WebView) findViewById(R.id.mywebview);
		final RelativeLayout mainscreen= (RelativeLayout) findViewById(R.id.mainscreen);

		mWebView.setVisibility(View.GONE);
		return true;
	}
	*/


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		final WebView mWebView= (WebView) findViewById(R.id.mywebview);
		switch (item.getItemId()) {
			case R.id.action_done:
				// Show the next Screen
				viewFlipper.showPrevious();
				resetAppTitleArea(true);
				resetMyHomeArea(true);
				resetBackgroundColorOfActionBar();
				return true;
			case R.id.action_dol:
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.dol.gov"));
				startActivity(browserIntent);
				return true;

			case android.R.id.home:
				Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.osha.gov"));
				startActivity(browserIntent2);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}


	public void myHomeButtonClickHandler(View target) {
		final WebView mWebView= (WebView) findViewById(R.id.mywebview);
		mWebView.setVisibility(View.GONE);
		resetAppTitleArea(true);
		resetMyHomeArea(true);
		resetBackgroundColorOfActionBar();
	}

	//true - show Home
	//false - hide Home
	public void resetMyHomeArea(boolean myOption) {
		ActionBar actionBar = getActionBar();
		if (myOption) {
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setDisplayUseLogoEnabled(true);
		}
		else{
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayUseLogoEnabled(false);
		}
	}

	public void myMoreInfoButtonClickHandler(View target) {
		final WebView mWebViewMoreInfo= (WebView) findViewById(R.id.mywebviewMoreInfo);
		mWebViewMoreInfo.setVisibility(View.GONE);
		resetMyCustomizedButtonsOnActionBar(3);
		setMyTitleInCustomizedActionBar(getString(R.string.txtForTitleMoreInfo));

		myDoneMenu.setVisible(true);
	}

	public void setMyTitleInCustomizedActionBar(String titleTxt) {
		TextView myTitle = (TextView) findViewById(R.id.myTitle);
		myTitle.setText(titleTxt);
	}


	public void resetMyCustomizedButtonsOnActionBar(int myOption) {
		final Button myHomeButton = (Button) findViewById(R.id.myHomeButton);
		final Button myMoreInfoButton = (Button) findViewById(R.id.myMoreInfoButton);

		if (myOption==1) {
			myHomeButton.setVisibility(View.VISIBLE);
			myMoreInfoButton.setVisibility(View.GONE);
		}
		if (myOption==2) {
			myHomeButton.setVisibility(View.GONE);
			myMoreInfoButton.setVisibility(View.VISIBLE);
		}
		if (myOption==3) {
			myHomeButton.setVisibility(View.GONE);
			myMoreInfoButton.setVisibility(View.GONE);
		}
	}


	public void resetBackgroundColorOfActionBar() {
		ActionBar actionBar = getActionBar();
		if (myRiskLevel == 4) {
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 0, 0)));
		}
		if (myRiskLevel == 3) {
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 128, 0)));
		}
		if (myRiskLevel == 2) {
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 153, 51)));
		}
		if (myRiskLevel == 1) {
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 255, 51)));
		}
		if (myRiskLevel == 0) {
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
		}
	}

	/*
    Button Handers for the Buttons on the MoreInfo page
     */
	public void myMoreInfoSignsClickHandler(View target) {
		myDoneMenu.setVisible(false);

		final WebView mWebViewMoreInfo= (WebView) findViewById(R.id.mywebviewMoreInfo);
		mWebViewMoreInfo.setVisibility(View.VISIBLE);
		resetMyCustomizedButtonsOnActionBar(2);
		setMyTitleInCustomizedActionBar(getString(R.string.txtForMoreInfoSigns));

		if (isSpanish) {
			mWebViewMoreInfo.loadUrl("file:///android_asset/Signs and Symptoms_es.html");
		}
		else{
			mWebViewMoreInfo.loadUrl("file:///android_asset/Signs and Symptoms.html");
		}

		mWebViewMoreInfo.getSettings().setJavaScriptEnabled(true);
		mWebViewMoreInfo.getSettings().setSaveFormData(true);
		mWebViewMoreInfo.getSettings().setBuiltInZoomControls(true);
	}

	public void myMoreInfoFirstAidClickHandler(View target) {
		myDoneMenu.setVisible(false);
		final WebView mWebViewMoreInfo= (WebView) findViewById(R.id.mywebviewMoreInfo);

		mWebViewMoreInfo.setVisibility(View.VISIBLE);
		resetMyCustomizedButtonsOnActionBar(2);
		setMyTitleInCustomizedActionBar(getString(R.string.txtForMoreInfoFirstAid));

		if (isSpanish) {
			mWebViewMoreInfo.loadUrl("file:///android_asset/First Aid_es.html");
		}
		else{
			mWebViewMoreInfo.loadUrl("file:///android_asset/First Aid.html");
		}

		mWebViewMoreInfo.getSettings().setJavaScriptEnabled(true);
		mWebViewMoreInfo.getSettings().setSaveFormData(true);
		mWebViewMoreInfo.getSettings().setBuiltInZoomControls(true);
	}

	public void myMoreInfoDetailsClickHandler(View target) {
		myDoneMenu.setVisible(false);
		final WebView mWebViewMoreInfo= (WebView) findViewById(R.id.mywebviewMoreInfo);

		mWebViewMoreInfo.setVisibility(View.VISIBLE);
		resetMyCustomizedButtonsOnActionBar(2);
		setMyTitleInCustomizedActionBar(getString(R.string.txtForMoreInfoDetails));

		if (isSpanish) {
			mWebViewMoreInfo.loadUrl("file:///android_asset/More Details_es.html");
		}
		else{
			mWebViewMoreInfo.loadUrl("file:///android_asset/More Details.html");
		}

		mWebViewMoreInfo.getSettings().setJavaScriptEnabled(true);
		mWebViewMoreInfo.getSettings().setSaveFormData(true);
		mWebViewMoreInfo.getSettings().setBuiltInZoomControls(true);
	}



	public void myMoreInfoContactClickHandler(View target) {
		myDoneMenu.setVisible(false);
		final WebView mWebViewMoreInfo= (WebView) findViewById(R.id.mywebviewMoreInfo);

		mWebViewMoreInfo.setVisibility(View.VISIBLE);
		resetMyCustomizedButtonsOnActionBar(2);
		setMyTitleInCustomizedActionBar(getString(R.string.txtForMoreInfoContact));

		if (isSpanish) {
			mWebViewMoreInfo.loadUrl("file:///android_asset/Contact OSHA_es.html");
		}
		else{
			mWebViewMoreInfo.loadUrl("file:///android_asset/Contact OSHA.html");
		}

		mWebViewMoreInfo.getSettings().setJavaScriptEnabled(true);
		mWebViewMoreInfo.getSettings().setSaveFormData(true);
		mWebViewMoreInfo.getSettings().setBuiltInZoomControls(true);
	}


	public void myMoreInfoAboutClickHandler(View target) {
		myDoneMenu.setVisible(false);
		final WebView mWebViewMoreInfo= (WebView) findViewById(R.id.mywebviewMoreInfo);

		mWebViewMoreInfo.setVisibility(View.VISIBLE);
		resetMyCustomizedButtonsOnActionBar(2);
		setMyTitleInCustomizedActionBar(getString(R.string.txtForMoreInfoAbout));

		if (isSpanish) {
			mWebViewMoreInfo.loadUrl("file:///android_asset/About This App_es.html");
		}
		else{
			mWebViewMoreInfo.loadUrl("file:///android_asset/About This App.html");
		}

		mWebViewMoreInfo.getSettings().setJavaScriptEnabled(true);
		mWebViewMoreInfo.getSettings().setSaveFormData(true);
		mWebViewMoreInfo.getSettings().setBuiltInZoomControls(true);
	}


	public boolean checkInternetConnection(Context context) {
		ConnectivityManager check = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (check != null) {
			NetworkInfo[] info = check.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {

						return true;
					}
				}
			}
		}

		return false;
	}

	public void showToast() {
		Log.d(DEBUG_TAG, " --- showToast - BEGIN");

		Context context = getApplicationContext();
		CharSequence text = getString(R.string.heatIndexUpdated);
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

		Log.d(DEBUG_TAG, " --- showToast - END");
	}
}



