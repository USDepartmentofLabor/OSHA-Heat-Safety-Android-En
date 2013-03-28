package com.erg.heatindex;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText; 
import android.widget.TableRow;
import android.content.Intent;


//testing
import android.location.LocationManager;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;


import java.util.Date;
import java.text.SimpleDateFormat;

import java.util.Vector;




public class signsandsymptoms extends Activity {
	private final String DEBUG_TAG = "HeadIndex";
	

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signsandsymptoms);    
        
        TextView myEdit = ((TextView) findViewById(R.id.titletxt));      
        myEdit.setText("Heat Illness: Signs and Symptoms");
        myEdit.setContentDescription("Heat Illness: Signs and Symptoms"); 
             
        
        // row 1
        TextView info11text = (TextView) findViewById(R.id.info11text);
        TextView info12text = (TextView) findViewById(R.id.info12text);
        TextView info13text = (TextView) findViewById(R.id.info13text);
        TextView info14text = (TextView) findViewById(R.id.info14text);
        TextView info15text = (TextView) findViewById(R.id.info15text);
        
        //Heat stroke
        info11text.setText("Red, hot, dry skin or excessive sweating");
        //info11text.setContentDescription("Red, hot, dry skin or excessive sweating");
        
        info12text.setText("Very high body temperature");
        //info12text.setContentDescription("Very high body temperature");
        
        info13text.setText("Confusion");
        //info13text.setContentDescription("Confusion");
        
        info14text.setText("Seizures");
        //info14text.setContentDescription("Seizures");
        
        info15text.setText("Fainting");
        //info15text.setContentDescription("Fainting");
        
        // row 2
        TextView info21text = (TextView) findViewById(R.id.info21text);
        TextView info22text = (TextView) findViewById(R.id.info22text);
        TextView info23text = (TextView) findViewById(R.id.info23text);
        TextView info24text = (TextView) findViewById(R.id.info24text);
        TextView info25text = (TextView) findViewById(R.id.info25text);
        TextView info26text = (TextView) findViewById(R.id.info26text);
        TextView info27text = (TextView) findViewById(R.id.info27text);
        TextView info28text = (TextView) findViewById(R.id.info28text);
        TextView info29text = (TextView) findViewById(R.id.info29text);
        TextView info210text = (TextView) findViewById(R.id.info210text);
        
        //Heat exhaustion
        info21text.setText("Cool, moist skin");
        //info21text.setContentDescription("Cool, moist skin");
        
        info22text.setText("Heavy sweating");
        //info22text.setContentDescription("Heavy sweating");
        
        info23text.setText("Headache");
        //info23text.setContentDescription("Headache");
        
        info24text.setText("Nausea or vomiting");
        //info24text.setContentDescription("Nausea or vomiting");
        
        info25text.setText("Dizziness");
        //info25text.setContentDescription("Dizziness");
        
        info26text.setText("Light headedness");
        //info26text.setContentDescription("Light headedness");
        
        info27text.setText("Weakness");
        //info27text.setContentDescription("Weakness");
        
        info28text.setText("Thirst");   
        //info28text.setContentDescription("Thirst");
        
        info29text.setText("Irritability"); 
        //info29text.setContentDescription("Irritability");
        
        info210text.setText("Fast heart beat"); 
        //info210text.setContentDescription("Fast heart beat");
         
         
        // row 4       
        TextView info41text = (TextView) findViewById(R.id.info41text);
        TextView info42text = (TextView) findViewById(R.id.info42text);
        TextView info43text = (TextView) findViewById(R.id.info43text); 
        
        //Heat cramps
        info41text.setText("Muscle spasms");
        //info41text.setContentDescription("Muscle spasms");
        
        info42text.setText("Pain");
        //info42text.setContentDescription("Pain");
        
        info43text.setText("Usually in abdomen, arms, or legs"); 
        //info43text.setContentDescription("Usually in abdomen, arms, or legs");
          
        // row 5
        TextView info51text = (TextView) findViewById(R.id.info51text);
        TextView info52text = (TextView) findViewById(R.id.info52text);
         
        //Heat rash
        info51text.setText("Clusters of red bumps on skin");
        //info51text.setContentDescription("Clusters of red bumps on skin");
        
        info52text.setText("Often appears on neck, upper chest, folds of skin");
        //info52text.setContentDescription("Often appears on neck, upper chest, folds of skin");
        
   
        // Info buttons
        Button	signs_button1 = (Button) findViewById(R.id.signs_button1);
        signs_button1.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent goMainMenu = new Intent(signsandsymptoms.this, firstaid.class);
			    startActivity(goMainMenu);  
			}
		});
        Button	signs_button2 = (Button) findViewById(R.id.signs_button2);
        signs_button2.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent goMainMenu = new Intent(signsandsymptoms.this, firstaid.class);
			    startActivity(goMainMenu);  
			}
		});

        Button	signs_button4 = (Button) findViewById(R.id.signs_button4);
        signs_button4.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent goMainMenu = new Intent(signsandsymptoms.this, firstaid.class);
			    startActivity(goMainMenu);  
			}
		});
        Button	signs_button5 = (Button) findViewById(R.id.signs_button5);
        signs_button5.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent goMainMenu = new Intent(signsandsymptoms.this, firstaid.class);
			    startActivity(goMainMenu);  
			}
		});
        
        
        
      //navaigation buttons
        Button home_button = (Button) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) { 
        	   Intent goNext = new Intent(signsandsymptoms.this, HeatIndex.class);
        	   startActivity(goNext);
        	   //finish();
           } 
        });
        
        //back button
        Button back_button = (Button) findViewById(R.id.back_button);
          back_button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) { 
          	   finish();
             } 
          });
        
        Button main_button = (Button) findViewById(R.id.main_button);
        main_button.setOnClickListener(new View.OnClickListener() {

    		@Override
    		public void onClick(View v) {      			
    			 Intent goMainMenu = new Intent(signsandsymptoms.this, moreinfo.class);
    		     startActivity(goMainMenu);    			
    		}
    	});  
    }  
}
       
