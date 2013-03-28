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


public class contactosha extends Activity {
	private final String DEBUG_TAG = "HeadIndex";
	

	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactosha);    
        
        TextView myEdit = ((TextView) findViewById(R.id.titletxt));         
        myEdit.setText("Contact OSHA");
        myEdit.setContentDescription("Contact OSHA"); 
   
        //for links
        TextView content1 = (TextView) findViewById(R.id.contact1);
        content1.setMovementMethod(LinkMovementMethod.getInstance());
        content1.setText(R.string.txtContact1);
        content1.setContentDescription(this.getString(R.string.txtContact1_screenreader)); 
        
        TextView content2 = (TextView) findViewById(R.id.contact2);
        content2.setMovementMethod(LinkMovementMethod.getInstance());
        content2.setText(R.string.txtContact2);
        content2.setContentDescription(this.getString(R.string.txtContact2_screenreader)); 
        

      //navaigation buttons
        Button home_button = (Button) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) { 
        	   Intent goNext = new Intent(contactosha.this, HeatIndex.class);
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
    			 Intent goMainMenu = new Intent(contactosha.this, moreinfo.class);
    		     startActivity(goMainMenu);    			
    		}
    	});  
    }  
}
       