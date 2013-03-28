package com.erg.heatindex;

import java.util.Date;
import java.util.Vector;
import java.util.List;
import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText; 
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationManager;
import android.location.Criteria;


public class HeatIndex extends Activity {
	private final String DEBUG_TAG = "HeadIndex";
	
	private double HIdouble = 0;	
	private int myValid1 = 1 ;  //temp is number
	private int myValid2 = 1 ;	//humidity is number
	
	private int myValid11 = 1 ; //temp is in valid range
	private int myValid22 = 1 ; //humidity is in valid range
	
	private int notify1 = 1 ;
	private int notify2 = 1 ;	
	private double valueTemp = 0;  //will be displayed in the two boxes for current and day max
	private double valueHumid = 0;
	private int buttonClicked = 1; // 1=current, 2=max	
	
	private String myMaxTime="";	 
	private int maxClick = 0;			
	
	//private String testIndex = ""; 
	//private String valueTemptest = "";  //will be displayed in the two boxes for current and day max
	//private String valueHumidtest = "";
	//private String myLattest = "";  //will be displayed in the two boxes for current and day max
	//private String myLongtest = "";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main); 
        
        //important, must define them final
        final EditText  editText1 = ((EditText) findViewById(R.id.edit_input_temp));
        final EditText  editText2 = ((EditText) findViewById(R.id.edit_input_humid)); 
        final Button 	max_submit_final = (Button) findViewById(R.id.max_submit);        
        final Button 	current_submit_final = (Button) findViewById(R.id.current_submit); 
        final LinearLayout dayMaxBlock = (LinearLayout) findViewById(R.id.daymaxblock);
        final TextView maxTimeResult = (TextView) findViewById(R.id.time_result);
        final InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);        
        
        
        //focus on the current button when page is loading
        current_submit_final.requestFocus();

        dayMaxBlock.setVisibility(View.GONE);
        
        //for links
        //TextView location1 = (TextView) findViewById(R.id.noaatime);    
        //clickify(location1, "NOAA Data Time");
        
        
        Button cmd_submit = (Button) findViewById(R.id.cmd_submit);
        cmd_submit.setOnClickListener(new View.OnClickListener() {     
        	
    		@Override
    		public void onClick(View v) {
    			
    			cleanUIforResults();
    			
    			dayMaxBlock.setVisibility(View.GONE);
    			
    			myValid1 = 1 ;
    			myValid2 = 1 ;	
    			
    			myValid11 = 1 ;
    			myValid22 = 1 ;
    			
    			//Hide Soft Keyboard			    
			    imm.hideSoftInputFromWindow(((EditText) findViewById(R.id.edit_input_humid)).getWindowToken(), 0);
    			//---end

    			try {
    				maxClick = 0;
    				myMaxTime="";
    				
    				/* Get what user typed to the EditText. */
    				String input1 = ((EditText) findViewById(R.id.edit_input_temp))
    						.getText().toString();
    				String input2 = ((EditText) findViewById(R.id.edit_input_humid))
    						.getText().toString();	
    				
    				if (!checkIfNumber(input1)){
    					new AlertDialog.Builder(editText1.getContext())
    					.setTitle("Error")
    					.setMessage("Please enter a number in the temperature box.")
    					.setNeutralButton("OK", new DialogInterface.OnClickListener() {
    					      public void onClick(DialogInterface dialog, int which) {    					        
    					    }
    					})
    					.show(); 
    				    myValid1=0; 
    				}
    				else {     				
	    				if (Double.parseDouble(input1)<80){     	
	    				     new AlertDialog.Builder(editText1.getContext())
	    					.setTitle("Alert")
	    					.setMessage("A heat index value is not accurate for temperatures less than 80 degrees Fahrenheit.")
	    					.setNeutralButton("OK", new DialogInterface.OnClickListener() {
	    					      public void onClick(DialogInterface dialog, int which) {    					        
	    					    }
	    					})
	    					.show(); 
	    				    myValid1=1;
	    				    myValid11=0;    				     
	    				}
	    				else {
	    					myValid1=1;
	    					myValid11=1;
	    				}    
    				}
    				
    				
    				if (!checkIfNumber(input2) && myValid1==1 && myValid11==1 ){
    					new AlertDialog.Builder(editText2.getContext())
    					.setTitle("Error")
    					.setMessage("Please enter a number in the relative humidity box.")
    					.setNeutralButton("OK", new DialogInterface.OnClickListener() {
    					      public void onClick(DialogInterface dialog, int which) {    					        
    					    }
    					})
    					.show(); 
    				    //myValid2=0; 
    				}
    				else {    					
	    				if (Double.parseDouble(input2)>100 && myValid1==1 && myValid11==1 ){        				   
	    				     new AlertDialog.Builder(editText2.getContext())
	    					.setTitle("Alert")
	    					.setMessage("A heat index value is not accurate for relative humidities greater than 100%.")
	    					.setNeutralButton("OK", new DialogInterface.OnClickListener() {
	    					      public void onClick(DialogInterface dialog, int which) {    					        
	    					    }
	    					})
	    					.show(); 
	    				    //myValid2=0;    				     
	    				}
	    				else {
	    					myValid2=1;
	    				}
    					myValid2=1;    					
    				}
    				
    				if (myValid1==1 && myValid2==1){    					
    					double d1 = Double.parseDouble(input1);
	    				double d2 = Double.parseDouble(input2);
	    				double myValue;
	    		        myValue = calIndex.heatIndexCal(d1, d2);
	    		        HIdouble = myValue;
	    		        
	    		        setUIforResults(HIdouble);	    
	    			} 
    			} catch (Exception e) {    				
    				Log.e(DEBUG_TAG, "WeatherQueryError", e);
    			}
    		}
    	});         
        
        
        current_submit_final.setOnClickListener(new View.OnClickListener() {

    		@Override
    		public void onClick(View v) {  
    			cleanUIforResults();
    			
    			buttonClicked=1;
    			int getGPS = 1;
    			double myLongValue = getLocationLongitude();
    			dayMaxBlock.setVisibility(View.VISIBLE);				
    			
				if (myLongValue==0){
					getGPS = 0;
					new AlertDialog.Builder(current_submit_final.getContext())
					.setTitle("Notification")
					.setMessage("Can not get your location information.")
					.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					      public void onClick(DialogInterface dialog, int which) { 
					    }
					})
					.show(); 					
				} 
			    
				if (getGPS==1) {
    			 if (notify1==0){
	    			 new AlertDialog.Builder(current_submit_final.getContext())
						.setTitle("Notification")
						.setMessage("We do not collect your GPS info.")
						.setNeutralButton("OK", new DialogInterface.OnClickListener() {
						      public void onClick(DialogInterface dialog, int which) {    
						    	  notify1=1;
						    }
						})
					.show();     			 	
    			 }    			
	    			try { 
	    				//clean the input fields
	    				editText1.setText("");
	    				editText2.setText("");
	    				
	    				maxClick = 2;
	    				myMaxTime="";  
	    				
	    				//GPS info
	    				String myLat = String.valueOf(getLocationLatitude());
	    				String myLong = String.valueOf(getLocationLongitude());	    				
	    				
	    				//hardcoded gas info for testing
	    				//String myLat = "42.46";
	    				//String myLong = "-71.25";
	    				
	    				//myLattest= myLat;
	    				//myLongtest = myLong;
	    				
	    				//build url   
	    				String myUrl = "http://forecast.weather.gov/MapClick.php?lat=" + myLat+ "&lon=" + myLong + "&FcstType=digitalDWML";
	    				
	    				
	    				double myValue;
	    				myValue = getNOAAData(myUrl);
	    				
	    				if (myValue ==0){
	    					 new AlertDialog.Builder(current_submit_final.getContext())
	 						.setTitle("Notification")
	 						.setMessage("The NOAA weather data are currently not available, please enter your temperature and relative humidity manually.")
	 						.setNeutralButton("OK", new DialogInterface.OnClickListener() {
	 						      public void onClick(DialogInterface dialog, int which) {    
	 						    	  
	 						    }
	 						})
	 					.show(); 
	    				
	    				}
	    				else {
		    				HIdouble = myValue;
		    		        
		    		        setUIforResults(HIdouble);	
	
		    		        //reset the input fields
		    		        Double myResult1 = new Double(valueTemp);
		    		        Double myResult2 = new Double(valueHumid);
	
		    				editText1.setText(myResult1.toString());
		    				editText2.setText(myResult2.toString());	 
		    				
		    				//((TextView)findViewById(R.id.testplace)).setText(testIndex);
	    				} 
	    				}
	    		        catch (Exception e) {
	    		            /* Display any Error to the GUI. */
	    		        	((EditText) findViewById(R.id.edit_input_temp)).setText("Error: " + e.getMessage());
	    		            Log.e(DEBUG_TAG, "WeatherQueryError", e);
	    		    } 
				} //end of check GPS
    		}
    	});          
               
        
        max_submit_final.setOnClickListener(new View.OnClickListener() {
        	
    		@Override
    		public void onClick(View v) {  
    			cleanUIforResults();
    			
    			buttonClicked=2;

    			int getGPS = 1;
    			double myLongValue = getLocationLongitude();    			
    			
    			if (myLongValue==0){
					getGPS = 0;
					new AlertDialog.Builder(current_submit_final.getContext())
					.setTitle("Notification")
					.setMessage("Can not get your location information.")
					.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					      public void onClick(DialogInterface dialog, int which) { 
					    }
					})
					.show(); 					
				}
				
				if (getGPS==1) {
    			 if (notify2==0){
    				 
	    			 new AlertDialog.Builder(max_submit_final.getContext())
						.setTitle("Notification")
						.setMessage("We do not collect your GPS info.")
						.setNeutralButton("OK", new DialogInterface.OnClickListener() {
						      public void onClick(DialogInterface dialog, int which) {   
						    	  notify2=1;
						    }
						})
					.show(); 
    			 }
    				 try {      					
    					dayMaxBlock.setVisibility(View.VISIBLE);    	    	    	
    	    	    	
    					//clean the input fields
 	    				editText1.setText("");
 	    				editText2.setText("");
    					maxClick = 1;   
	    				
	    				//GPS info
	    				String myLat = String.valueOf(getLocationLatitude());
	    				String myLong = String.valueOf(getLocationLongitude());	    				
	    			
	    				//new weather service
	    				String myUrl = "http://forecast.weather.gov/MapClick.php?lat=" + myLat+ "&lon=" + myLong + "&FcstType=digitalDWML";
	    				   				
	    				double myValue;
	    				myValue = getNOAAData(myUrl);
	    				
	    				if (myValue ==0){
	    					 new AlertDialog.Builder(current_submit_final.getContext())
	 						.setTitle("Notification")
	 						.setMessage("The NOAA weather data are currently not available, please enter your temperature and relative humidity manually.")	 						
	 						.setNeutralButton("OK", new DialogInterface.OnClickListener() {
	 						      public void onClick(DialogInterface dialog, int which) {    
	 						    	  
	 						    }
	 						})
	 					.show(); 	    				
	    				}
	    				else {	    				    				
		    				HIdouble = myValue;
		    		        setUIforResults(HIdouble);	 
		    		        //reset the input fields
		    		        Double myResult1 = new Double(valueTemp);
		    		        Double myResult2 = new Double(valueHumid);	
		    				editText1.setText(myResult1.toString());
		    				editText2.setText(myResult2.toString());
	    				}	    		        
    		        }
    		        catch (Exception e) {
    		            /* Display any Error to the GUI. */
    		        	((EditText) findViewById(R.id.edit_input_temp)).setText("Error: " + e.getMessage());
    		            Log.e(DEBUG_TAG, "WeatherQueryError", e);
    		        }  
				}
    		}
    	});        
        
        
        Button main_button = (Button) findViewById(R.id.main_button);
        main_button.setOnClickListener(new View.OnClickListener() {

    		@Override
    		public void onClick(View v) {      			
    			 Intent goMainMenu = new Intent(HeatIndex.this, moreinfo.class);
    		     startActivity(goMainMenu);    			
    		}
    	});        
        
        
        Button cmd_supervisor = (Button) findViewById(R.id.cmd_supervisor);
        cmd_supervisor.setOnClickListener(new View.OnClickListener() {

    		@Override
    		public void onClick(View v) {      			
    			 Intent i = new Intent(HeatIndex.this, precautions.class);
    			 i.putExtra("HIdouble", HIdouble);
    			 i.putExtra("MaxTime", myMaxTime);
    		     startActivity(i);    			
    		}
    	});        
    } //end of function       
    
    
    public void getLocation() {
        try {
            LocationManager locMgr= (LocationManager)getSystemService(LOCATION_SERVICE);
            Location recentLoc=locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);                 
            recentLoc.getLatitude();
            recentLoc.getLongitude();      
            Log.i(DEBUG_TAG, "loc: " + recentLoc.toString());         
        }
        catch (Exception e) {
            Log.e(DEBUG_TAG, "Location failed", e);
        }
    }
    
    public double getLocationLatitude() {
        try {
        	String bestProvider;        	
            LocationManager locMgr= (LocationManager)getSystemService(LOCATION_SERVICE);            
    		List<String> providers = locMgr.getAllProviders();  
    		Criteria criteria = new Criteria();
    		bestProvider = locMgr.getBestProvider(criteria, false);    		
    		Location location = locMgr.getLastKnownLocation(bestProvider);    	
    		return location.getLatitude();     
        }
        catch (Exception e) {
            Log.e(DEBUG_TAG, "Location failed", e);
            return 0;
        }
    }
    
    public double getLocationLongitude() {
        try {
        	String bestProvider;        	
            LocationManager locMgr= (LocationManager)getSystemService(LOCATION_SERVICE);            
    		List<String> providers = locMgr.getAllProviders();  
    		Criteria criteria = new Criteria();
    		bestProvider = locMgr.getBestProvider(criteria, false);    		
    		Location location = locMgr.getLastKnownLocation(bestProvider);    	
    		return location.getLongitude();             
        }
        catch (Exception e) {
            Log.e(DEBUG_TAG, "Location failed", e);
            return 0;
        }
    }    
    
    public double getNOAAData(String urlP) {
        try {        	
        	
        	//testIndex="Test for developers:" + "Lat=" + myLattest +  " Long=" + myLongtest + " ";
        	
        URL url = new URL(urlP);  		
		
        /* Get a SAXParser from the SAXPArserFactory. */
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();

        /* Get the XMLReader of the SAXParser we created. */
        XMLReader xr = sp.getXMLReader();
        /* Create a new ContentHandler and apply it to the XML-Reader*/ 
        HandlerWeather myExampleHandler = new HandlerWeather();
        xr.setContentHandler(myExampleHandler);    
        
        /* Parse the xml-data from our URL. */
        xr.parse(new InputSource(url.openStream()));
        /* Parsing has finished. */

        /* Our ExampleHandler now provides the parsed data to us. */
        ParsedDataSetWeather parsedExampleDataSet = myExampleHandler.getParsedData();

        /* Set the result to be displayed in our GUI. */
        //((EditText) findViewById(R.id.edit_input_temp)).setText(parsedExampleDataSet.toString());       
       
        //get today's date
		Date myDate = new Date();    				
		
		//get Date
		int myDay = myDate.getDate();		
		//get current hour
		int myHour = myDate.getHours();
		//int myHour = 12;
		
	
		//the size of the data for today's calculation
		int myVecSize = 24-myHour;
        
        //temperature vector, humid vector and time vector for storing the data from XML
        Vector myTempVec = parsedExampleDataSet.gettemperature();
        Vector myHumidVec = parsedExampleDataSet.gethumidity();	
        Vector myTimeVec = parsedExampleDataSet.getmaxtime();
        
        //results HeatIndex Array
        double[] myHIarray = new double[myTempVec.size()];
        
        //put vector values to array for calculation later       
        double[] myTempArray = new double[myTempVec.size()];
        for (int i=0; i<myTempVec.size(); i++){
            String val = (String)myTempVec.get(i);
            myTempArray[i] = Double.parseDouble(val);    	
        }
        
        double[] myHumidArray = new double[myHumidVec.size()];
        for (int i=0; i<myHumidVec.size(); i++){
            String val = (String)myHumidVec.get(i);
            myHumidArray[i] = Double.parseDouble(val);   
        }	           
        
        //the following code is handling the cache in the getting weather data
        //pick up the data for current hour + 1
        //if there is cache, skip those cached values       
        
        int myCurrentIndex = 0;      //the index of the first data for our calculation
        for (int i=0; i<myTempVec.size(); i++){
            String val = (String)myTimeVec.get(i); 
            String getDay = val.substring(8,10);
            String getTime = val.substring(11,13);    
           
            if (myDay == Integer.parseInt(getDay) &&  myHour<Integer.parseInt(getTime)){
            	myCurrentIndex = i;            
            	//testIndex = testIndex + "myCurrentIndex" + "=" + i + "    getDay=" + getDay + "  myDay=" + myDay + "  myVecSize=" + myVecSize;
            	break;
            }
        }       
        
        //the useful data for our calculation
        //excluding the cached data for previous days/hours and data for future days
        double[] myTempArray2 = new double[myVecSize];    
        for (int i=0; i<myVecSize; i++){           
            myTempArray2[i] = myTempArray[i+ myCurrentIndex];  
            //valueTemptest = valueTemptest + i  + "=   " + String.valueOf(myTempArray2[i]);
        }
        
        double[] myHumidArray2 = new double[myVecSize];
        for (int i=0; i<myVecSize; i++){           
            myHumidArray2[i] = myHumidArray[i+ myCurrentIndex];      
            //valueHumidtest = valueHumidtest + i  + "=   "  + String.valueOf(myHumidArray2[i]);
        }	              
        
        //testIndex = testIndex + "Temp:  " + valueTemptest + "   Humidity: "  + valueHumidtest;             
        
        int size = myTempArray2.length;
        
        //calculate heatindex and put them in myHIarray
        for (int i=0; i<size; i++){
        	myHIarray[i]= calIndex.heatIndexCal(myTempArray2[i], myHumidArray2[i]);  
        }

        double myValue=0;   //variable for storing the HI value shown on the screen later
        
        //current button is clicked
        if (buttonClicked==1 && size>0){		 
		  valueTemp = myTempArray2 [0];
		  valueHumid = myHumidArray2 [0];
		  myValue=myHIarray[0];
        }
        
        //find the max heatindex for day max
        int mycount=0;    //index for the max HeatIndex
        if (buttonClicked==2 && size>0){
	        for (int i=0; i<size; i++){
	        	if (i==0){
	        		myValue = myHIarray[i];
	        		mycount=0;
	        	}
	        	else {
	        		if (myValue<myHIarray[i]){
	        			myValue = myHIarray[i];
	        			mycount++;
	        		}
	        	}
	        }        
        }   
        //processing the values for MAX button clicking
		if (buttonClicked==2 && size>0){
			valueTemp = myTempArray2[mycount];
			valueHumid = myHumidArray2[mycount];
		}

		if (size>0){
	        String myMaxTimeStr ="";
	        
	        if (buttonClicked==1){	        	
	        	myMaxTimeStr = (String)myTimeVec.get(myCurrentIndex);
	        }
	        if (buttonClicked==2){
	        	myMaxTimeStr = (String)myTimeVec.get(mycount+myCurrentIndex);
	        }	      
	        //sample format from XML 2011-06-09T17:00:00-04:00
	        myMaxTimeStr = myMaxTimeStr.substring(11,13);
	            
	        //format to 8am, 2pm etc
	        int intHourValue;
	        intHourValue = Integer.parseInt(myMaxTimeStr);
	        String ampm = "am";
	        
	        if (intHourValue<10){
	        	myMaxTimeStr = myMaxTimeStr.substring(1);         	
	        	myMaxTimeStr = myMaxTimeStr + " " + "am";
	        }
	        else{
	        	if (intHourValue>12){
	        		intHourValue = intHourValue-12;
	        		ampm = "pm";        	
	        	}
	        	if (intHourValue==12){        		
	        		ampm = "pm";        	
	        	}
	        	myMaxTimeStr = String.valueOf(intHourValue) + " " + ampm;
	        }     
	                      
	        	myMaxTime = myMaxTimeStr;
		}        	
		else{
        		myValue=0;
        		myMaxTime="";
        }           
            
            return myValue;
        }
        catch (Exception e) {
            Log.e(DEBUG_TAG, "Location failed", e);
            return 0;
        }        
    }   
    
    public void setUIforResults(double HIdoubleP) {
    	 //display on the screen
          Double myResult = new Double(HIdoubleP);
          TextView myHI_Result = (TextView) findViewById(R.id.hi_result);
          TextView myRisk_Result = (TextView) findViewById(R.id.risk_result);
          
          myHI_Result.setText(myResult.toString()+" °F");         
          myHI_Result.setBackgroundColor(Color.rgb(243, 243, 243));
          myHI_Result.setContentDescription(myResult.toString()+" °fahrenheit");
          
	        if (maxClick == 1 || maxClick == 2 ){
	        	  //set NOAA time        	  
	        	  ((TextView) findViewById(R.id.time_result)).setText("NOAA Data Time "+myMaxTime);   
	        	  ((TextView) findViewById(R.id.time_result)).setContentDescription("NOAA Data Time "+myMaxTime);   
	        	  
	        }
	        
	    	if (HIdoubleP>115){        	
	    		myRisk_Result.setText("VERY HIGH TO EXTREME");
	    		myRisk_Result.setBackgroundColor(Color.RED);
	    		myRisk_Result.setContentDescription("VERY HIGH TO EXTREME");
	        }	   
	        if (HIdoubleP>103 && HIdoubleP<=115){        	
	        	myRisk_Result.setText("HIGH");
	        	myRisk_Result.setBackgroundColor(Color.rgb(247, 141, 0));
	        	myRisk_Result.setContentDescription("HIGH");
	        }	        
	        if (HIdoubleP>=91 &&  HIdoubleP<=103){        	
	        	myRisk_Result.setText("MODERATE");
	        	myRisk_Result.setBackgroundColor(Color.rgb(255,	211, 155));
	        	myRisk_Result.setContentDescription("MODERATE");
	        }
	        if (HIdoubleP<91){        	
	        	myRisk_Result.setText("LOWER (CAUTION)");
	        	myRisk_Result.setBackgroundColor(Color.YELLOW);
	        	myRisk_Result.setContentDescription("LOWER (CAUTION)");	        	
	        }       
    }  
    
    
    public void cleanUIforResults() {  	
         TextView myHI_Result = (TextView) findViewById(R.id.hi_result);
         TextView myRisk_Result = (TextView) findViewById(R.id.risk_result);
         
         myHI_Result.setText("");         
         myHI_Result.setBackgroundColor(Color.rgb(220, 220, 220));
         myHI_Result.setContentDescription("");
         
         myRisk_Result.setText("");
     	 myRisk_Result.setBackgroundColor(Color.rgb(220, 220, 220));
     	 myRisk_Result.setContentDescription("");
         
        ((TextView) findViewById(R.id.time_result)).setText("");     
        ((TextView) findViewById(R.id.time_result)).setContentDescription("");  
        
        HIdouble=0;
		myMaxTime="";
   }  
    
    
 public boolean checkIfNumber(String in) {
        
        try {
            Double.parseDouble(in);        
        } catch (NumberFormatException ex) {
            return false;
        }        
        return true;
    }
}