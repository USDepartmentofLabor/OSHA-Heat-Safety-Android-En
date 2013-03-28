package com.erg.heatindex;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout; 

//added for links - jm
import android.text.Html; 
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import java.util.regex.Pattern;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;

public class precautions extends Activity {
	
	
	 private LinearLayout Low;
	 private LinearLayout Moderate;
	 private LinearLayout High;
	 private LinearLayout VeryHigh;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.supervisor);
        setContentView(R.layout.precautions);
        
        final Button home_button = (Button) findViewById(R.id.home_button);
        final Button main_button = (Button) findViewById(R.id.main_button);
        //4 groups to show/hide
        //final LinearLayout Low;
        //final LinearLayout Moderate;
        //final LinearLayout High;
        //final LinearLayout VeryHigh;
        
        double valueHIdouble = 0;
        String myMaxTime="";
        
        //get the parameters in the extras
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
        	valueHIdouble = extras.getDouble("HIdouble");
        	myMaxTime = extras.getString("MaxTime");
        }       
        
        TextView myActionLabel = (TextView) findViewById(R.id.view1);
        TextView myMaxHeatIndex = (TextView) findViewById(R.id.maxheatindex);
        
        TextView myMaxTimeView = (TextView) findViewById(R.id.maxtime);
        TextView myTimeView = (TextView) findViewById(R.id.time);
        
        //for links
        TextView content1 = (TextView) findViewById(R.id.high3);
        content1.setMovementMethod(LinkMovementMethod.getInstance());
        content1.setText(R.string.high3);
        
        TextView content2 = (TextView) findViewById(R.id.veryhigh3);
        content2.setMovementMethod(LinkMovementMethod.getInstance());
        content2.setText(R.string.veryhigh3);       
        
        TextView location1 = (TextView) findViewById(R.id.low3);    
        clickify(location1, "Use more precautions for layers of protective clothing and work in direct sun");
        
        TextView location2 = (TextView) findViewById(R.id.mod2);    
        clickify(location2, "cool a worker");
        
        TextView location3 = (TextView) findViewById(R.id.mod2);    
        clickify(location3, "heat stroke");
        
        TextView location4 = (TextView) findViewById(R.id.mod4);    
        clickify(location4, "recognize heat-related illness");
        TextView location5 = (TextView) findViewById(R.id.mod4);    
        clickify(location5, "What to do if someone gets sick");
        
        TextView location6 = (TextView) findViewById(R.id.high2);    
        clickify(location6, "cool a worker");
        TextView location7 = (TextView) findViewById(R.id.high2);    
        clickify(location7, "heat stroke");
        
        TextView location8 = (TextView) findViewById(R.id.high4);    
        clickify(location8, "recognize heat-related illness");
        TextView location9 = (TextView) findViewById(R.id.high4);    
        clickify(location9, "What to do if someone gets sick");
        
        TextView location10 = (TextView) findViewById(R.id.veryhigh2);    
        clickify(location10, "cool a worker");
        TextView location11 = (TextView) findViewById(R.id.veryhigh2);    
        clickify(location11, "heat stroke");     
        TextView location12 = (TextView) findViewById(R.id.veryhigh4);    
        clickify(location12, "recognize heat-related illness");
        TextView location13 = (TextView) findViewById(R.id.veryhigh4);    
        clickify(location13, "What to do if someone gets sick");   
        
        //4 groups to show/hide
        Low = (LinearLayout) findViewById(R.id.Low);
        Moderate = (LinearLayout) findViewById(R.id.Moderate);
        High = (LinearLayout) findViewById(R.id.High);
        VeryHigh = (LinearLayout) findViewById(R.id.VeryHigh);
            
        //Low
        TextView LowIntro = (TextView) findViewById(R.id.LowIntro);
        LowIntro.setMovementMethod(LinkMovementMethod.getInstance());
       
        //Moderate
        TextView ModIntro = (TextView) findViewById(R.id.ModIntro);
        ModIntro.setMovementMethod(LinkMovementMethod.getInstance());
        
        //High
        TextView HighIntro = (TextView) findViewById(R.id.HighIntro);
        HighIntro.setMovementMethod(LinkMovementMethod.getInstance());
        
        //Very High
        TextView VeryHighIntro = (TextView) findViewById(R.id.VeryHighIntro);
        VeryHighIntro.setMovementMethod(LinkMovementMethod.getInstance());        
  
        //VERY HIGH
        if (valueHIdouble > 115){
        	myActionLabel.setBackgroundColor(Color.RED);
        	myActionLabel.setText("VERY HIGH TO EXTREME");
        	//hide
        	Low.setVisibility(View.GONE);
        	Moderate.setVisibility(View.GONE);
        	High.setVisibility(View.GONE);
        	
        	myActionLabel.setContentDescription("VERY HIGH TO EXTREME");
        }        
        
        //HIGH
        if (valueHIdouble > 103 && valueHIdouble <= 115){
        	myActionLabel.setBackgroundColor(Color.rgb(247, 141, 0));
        	myActionLabel.setText("HIGH");
        	//hide
        	Low.setVisibility(View.GONE);
        	Moderate.setVisibility(View.GONE);
        	VeryHigh.setVisibility(View.GONE);
        	
        	myActionLabel.setContentDescription("HIGH");
        }
        
        //MODERATE
        if (valueHIdouble >= 91 &&  valueHIdouble <= 103){
        	myActionLabel.setBackgroundColor(Color.rgb(255,	211, 155));
        	myActionLabel.setText("MODERATE");
        	//hide
        	Low.setVisibility(View.GONE);
        	High.setVisibility(View.GONE);
        	VeryHigh.setVisibility(View.GONE);
        	
        	myActionLabel.setContentDescription("MODERATE");
        }
        //LOW
        if (valueHIdouble < 91){
        	myActionLabel.setBackgroundColor(Color.YELLOW);
        	myActionLabel.setText("LOWER (CAUTION)");
        	//hide
        	Moderate.setVisibility(View.GONE);
        	High.setVisibility(View.GONE);
        	VeryHigh.setVisibility(View.GONE);
        	
        	myActionLabel.setContentDescription("LOWER (CAUTION)");
        } 
        
   
        String heatMaxValue = Double.valueOf(valueHIdouble).toString();          
        myMaxHeatIndex.setText(heatMaxValue);
        
        myMaxHeatIndex.setContentDescription(heatMaxValue);
        
        //set the time for MAX calculation
        
        if (myMaxTime.equalsIgnoreCase("")){
        	myMaxTimeView.setVisibility(View.GONE);
        	myTimeView.setVisibility(View.GONE);  
        }             
        else {
        	myMaxTimeView.setText(myMaxTime);
        	myMaxTimeView.setContentDescription(myMaxTime);
        } 
        
 
        //buttons on the footer
        home_button.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View v) {  
    			finish();    			   			
    		}
    	});     
       
        //back button
        Button back_button = (Button) findViewById(R.id.back_button);
          back_button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) { 
          	   finish();
             } 
          });
       
        
        main_button.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View v) {      			
    			 Intent goMain = new Intent(precautions.this, moreinfo.class);
    		     startActivity(goMain);    			
    		}
    	});   
        
        //end of buttons on the footer  
    }
    
    public void clickify(TextView view, final String clickableText) {   
        CharSequence text = view.getText();      
        String string = text.toString(); 
        
        view.setMovementMethod(LinkMovementMethod.getInstance());  
        Spannable spans = (Spannable) view.getText();  
         
            
        int start = string.indexOf(clickableText);    
        int end = start + clickableText.length();    
        
            ClickableSpan clickSpan = new ClickableSpan() {     
                @Override     
                public void onClick(View widget)     {        //put whatever you like here, below is an example          
                    //showAlert("A","B","C", false);
                	if (clickableText.equalsIgnoreCase("Use more precautions for layers of protective clothing and work in direct sun")){
                		 Intent goNext = new Intent(precautions.this, moderate.class);
            		     startActivity(goNext); 
                	}
                	if (clickableText.equalsIgnoreCase("cool a worker")){
                    		 Intent goNext = new Intent(precautions.this, firstaid.class);
                		     startActivity(goNext); 
                    	}
                	if (clickableText.equalsIgnoreCase("heat stroke")){
                    		 Intent goNext = new Intent(precautions.this, signsandsymptoms.class);
                		     startActivity(goNext); 
                    	}
                	if (clickableText.equalsIgnoreCase("recognize heat-related illness")){
                    		 Intent goNext = new Intent(precautions.this, signsandsymptoms.class);
                		     startActivity(goNext); 
                    	}
                	if (clickableText.equalsIgnoreCase("what to do if someone gets sick")){
               		 		Intent goNext = new Intent(precautions.this, firstaid.class);
               		 		startActivity(goNext); 
               	}
                }  
              
            };  
            spans.setSpan(clickSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            
    }  
}       