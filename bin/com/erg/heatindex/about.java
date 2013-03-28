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
//added for links - jm
import android.text.Html; 
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import java.util.regex.Pattern;



public class about extends Activity {
	private final String DEBUG_TAG = "HeadIndex";
	

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        
        TextView myEdit = ((TextView) findViewById(R.id.titletxt));         
        myEdit.setText("About This App");
        myEdit.setContentDescription("About This App"); 
        
        TextView content = (TextView) findViewById(R.id.disclaimer);
        content.setMovementMethod(LinkMovementMethod.getInstance());
        content.setText(R.string.txtDisclaimer);
        content.setContentDescription(this.getString(R.string.txtDisclaimer)); 
 
      //buttons on the footer
        final Button home_button = (Button) findViewById(R.id.home_button);
        //final Button disclaimer_button = (Button) findViewById(R.id.disclaimer_button);
        final Button main_button = (Button) findViewById(R.id.main_button);
      
        home_button.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View v) {  
    			 Intent goNext = new Intent(about.this, HeatIndex.class);
    		     startActivity(goNext);    			   			
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
    			 Intent goNext = new Intent(about.this, moreinfo.class);
    		     startActivity(goNext);    			
    		}
    	});   
        
        //end of buttons on the footer 
       
        
    }  
}
  
