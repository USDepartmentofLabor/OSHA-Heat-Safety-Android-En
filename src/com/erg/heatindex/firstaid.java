package com.erg.heatindex;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TableRow;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText; 
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

//for links -jm
import android.text.Html; 
import android.text.method.LinkMovementMethod;


public class firstaid extends Activity {
	private final String DEBUG_TAG = "HeadIndex";
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstaid);    
        
        TextView myEdit = ((TextView) findViewById(R.id.titletxt));         
        myEdit.setText("Heat Illness: First Aid");
        myEdit.setContentDescription("Heat Illness: First Aid");
        
        // row 1
        TextView info11text = (TextView) findViewById(R.id.info11text);
        TextView info12text = (TextView) findViewById(R.id.info12text);
        TextView info13text = (TextView) findViewById(R.id.info13text);
        TextView info14text = (TextView) findViewById(R.id.info14text);
        TextView info15text = (TextView) findViewById(R.id.info15text);
        TextView info16text = (TextView) findViewById(R.id.info16text);
        TextView info17text = (TextView) findViewById(R.id.info17text);
        
        //Heat stroke
        info11text.setText("HEAT STROKE IS A MEDICAL EMERGENCY. Call 911.\nWhile waiting for help:");
        //info11text.setContentDescription("HEAT STROKE IS A MEDICAL EMERGENCY. Call 911. While waiting for help:");
        
        info12text.setText("Place worker in shady, cool area");
        //info12text.setContentDescription("Place worker in shady, cool area");
        
        info13text.setText("Loosen clothing, remove outer clothing");
        //info13text.setContentDescription("Loosen clothing, remove outer clothing");
        
        info14text.setText("Fan air on worker; cool packs in armpits");
        //info14text.setContentDescription("Fan air on worker; cool packs in armpits");
        
        info15text.setText("Wet worker with cool water; apply ice packs, cool compresses, or ice if available");
        //info15text.setContentDescription("Wet worker with cool water; apply ice packs, cool compresses, or ice if available");
        
        info16text.setText("Provide fluids (preferably water) as soon as possible");
        //info16text.setContentDescription("Provide fluids (preferably water) as soon as possible");
        
        info17text.setText("Stay with worker until help arrives");
        //info17text.setContentDescription("Stay with worker until help arrives");
   
        // row 2  
        TextView info21text = (TextView) findViewById(R.id.info21text);
        TextView info22text = (TextView) findViewById(R.id.info22text);
        TextView info23text = (TextView) findViewById(R.id.info23text);
        TextView info24text = (TextView) findViewById(R.id.info24text);
        TextView info25text = (TextView) findViewById(R.id.info25text);
        
        //Heat exhaustion
        info21text.setText("Have worker sit or lie down in a cool, shady area");
        //info21text.setContentDescription("Have worker sit or lie down in a cool, shady area");
        
        info22text.setText("Give worker plenty of water or other cool beverages to drink");
        //info22text.setContentDescription("Give worker plenty of water or other cool beverages to drink");
        
        info23text.setText("Cool worker with cold compresses/ice packs");
        //info23text.setContentDescription("Cool worker with cold compresses/ice packs");
        
        info24text.setText("Take to clinic or emergency room for medical evaluation and treatment if signs or symptoms worsen or do not improve within 60 minutes");
        //info24text.setContentDescription("Take to clinic or emergency room for medical evaluation and treatment if signs or symptoms worsen or do not improve within 60 minutes");
        
        info25text.setText("Do not return to work that day");
        //info25text.setContentDescription("Do not return to work that day");
        
        // row 4
        TextView info41text = (TextView) findViewById(R.id.info41text);
        TextView info42text = (TextView) findViewById(R.id.info42text);
        TextView info43text = (TextView) findViewById(R.id.info43text);
        TextView info44text = (TextView) findViewById(R.id.info44text);
        
        //Heat cramps
        info41text.setText("Have worker rest in shady, cool area");
        //info41text.setContentDescription("Have worker rest in shady, cool area");
        
        info42text.setText("Have worker drink water or other cool beverages");
        //info42text.setContentDescription("Have worker drink water or other cool beverages");
        
        info43text.setText("Wait a few hours before allowing worker to return to heavy work"); 
        //info43text.setContentDescription("Wait a few hours before allowing worker to return to heavy work");
        
        info44text.setText("Seek medical attention if cramps don't go away"); 
        //info44text.setContentDescription("Seek medical attention if cramps don't go away");
        
        // row 5
        TextView info51text = (TextView) findViewById(R.id.info51text);
        TextView info52text = (TextView) findViewById(R.id.info52text);
        
        //Heat rash
        info51text.setText("Try to work in a cooler, less humid environment when possible");
        //info51text.setContentDescription("Try to work in a cooler, less humid environment when possible");
        
        info52text.setText("Keep the affected area dry");
        //info52text.setContentDescription("Keep the affected area dry");
        
    
        // Info buttons
        Button	signs_button1 = (Button) findViewById(R.id.firstaid_button1);
        signs_button1.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent goMainMenu = new Intent(firstaid.this, signsandsymptoms.class);
			    startActivity(goMainMenu);  
			}
		});
        Button	signs_button2 = (Button) findViewById(R.id.firstaid_button2);
        signs_button2.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent goMainMenu = new Intent(firstaid.this, signsandsymptoms.class);
			    startActivity(goMainMenu);  
			}
		});

        Button	signs_button4 = (Button) findViewById(R.id.firstaid_button4);
        signs_button4.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent goMainMenu = new Intent(firstaid.this, signsandsymptoms.class);
			    startActivity(goMainMenu);  
			}
		});
        Button	signs_button5 = (Button) findViewById(R.id.firstaid_button5);
        signs_button5.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent goMainMenu = new Intent(firstaid.this, signsandsymptoms.class);
			    startActivity(goMainMenu);  
			}
		});
        
        
        
      //navaigation buttons
        Button home_button = (Button) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) { 
        	   Intent goNext = new Intent(firstaid.this, HeatIndex.class);
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
    			 Intent goMainMenu = new Intent(firstaid.this, moreinfo.class);
    		     startActivity(goMainMenu);    			
    		}
    	});  
    }  
}
       