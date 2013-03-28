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

public class moderate extends Activity {
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moderate);
        
        final Button home_button = (Button) findViewById(R.id.home_button);
        final Button main_button = (Button) findViewById(R.id.main_button);
        
        TextView myActionLabel = (TextView) findViewById(R.id.view1);
        
        //for links
        TextView location2 = (TextView) findViewById(R.id.mod2);    
        clickify(location2, "cool a worker");
        
        TextView location3 = (TextView) findViewById(R.id.mod2);    
        clickify(location3, "heat stroke");
        
        TextView location4 = (TextView) findViewById(R.id.mod4);    
        clickify(location4, "recognize heat-related illness");
        TextView location5 = (TextView) findViewById(R.id.mod4);    
        clickify(location5, "What to do if someone gets sick");
 
        //Moderate
        TextView ModIntro = (TextView) findViewById(R.id.ModIntro);
        ModIntro.setMovementMethod(LinkMovementMethod.getInstance());
   
        //MODERATE
        /*if (valueHIdouble >= 91 &&  valueHIdouble <= 103){*/
        	myActionLabel.setBackgroundColor(Color.rgb(255,	211, 155));
        	myActionLabel.setText("MODERATE");
       // }
   
 
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
    			 Intent goMain = new Intent(moderate.this, moreinfo.class);
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
                public void onClick(View widget)     {   
               	if (clickableText.equalsIgnoreCase("cool a worker")){
                   		 Intent goNext = new Intent(moderate.this, firstaid.class);
               		     startActivity(goNext); 
                   	}
               	if (clickableText.equalsIgnoreCase("heat stroke")){
                   		 Intent goNext = new Intent(moderate.this, signsandsymptoms.class);
               		     startActivity(goNext); 
                   	}
               	if (clickableText.equalsIgnoreCase("recognize heat-related illness")){
                   		 Intent goNext = new Intent(moderate.this, signsandsymptoms.class);
               		     startActivity(goNext); 
                   	}
               	if (clickableText.equalsIgnoreCase("what to do if someone gets sick")){
              		 		Intent goNext = new Intent(moderate.this, firstaid.class);
              		 		startActivity(goNext); 
               	}
                }  
              
            };  
            spans.setSpan(clickSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            
    }
    
}       