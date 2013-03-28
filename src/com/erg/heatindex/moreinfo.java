package com.erg.heatindex;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**import android.widget.EditText; */

public class moreinfo extends Activity {
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moreinfo);   
        
        //final Button disclaimer_button = (Button) findViewById(R.id.disclaimer_button);
        
        Button sign_button = (Button) findViewById(R.id.sign_button);
        sign_button.setOnClickListener(new View.OnClickListener() {
    		//@Override
    		public void onClick(View v) {      			
    			 //Intent i = new Intent(mainmenu.this, placeholder.class);
    			Intent i = new Intent(moreinfo.this, signsandsymptoms.class);
    		     startActivity(i);    			
    		}
    	});
        
        
        Button firstaid_button = (Button) findViewById(R.id.firstaid_button);
        firstaid_button.setOnClickListener(new View.OnClickListener() {
    		//@Override
    		public void onClick(View v) {      			
    			 Intent i = new Intent(moreinfo.this, firstaid.class);
    		     startActivity(i);    			
    		}
    	});
        
        
        Button tips_button = (Button) findViewById(R.id.tips_button);
        tips_button.setOnClickListener(new View.OnClickListener() {
    		//@Override
    		public void onClick(View v) {      			
    			 Intent i = new Intent(moreinfo.this, moredetail.class);
    		     startActivity(i);    			
    		}
    	});
        
        Button contact_button = (Button) findViewById(R.id.contact_button);
        contact_button.setOnClickListener(new View.OnClickListener() {
    		//@Override
    		public void onClick(View v) {      			
    			 Intent i = new Intent(moreinfo.this, contactosha.class);
    		     startActivity(i);    			
    		}
    	});
        
        Button about_button = (Button) findViewById(R.id.about_button);
        about_button.setOnClickListener(new View.OnClickListener() {
    		//@Override
    		public void onClick(View v) {      			
    			 Intent i = new Intent(moreinfo.this, about.class);
    		     startActivity(i);    			
    		}
    	});
        
        
        Button home_button = (Button) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) { 
        	   Intent goNext = new Intent(moreinfo.this, HeatIndex.class);
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
 
    }
}