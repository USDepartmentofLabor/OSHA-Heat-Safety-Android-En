package com.erg.heatindex;

import java.util.Vector;

public class ParsedDataSetWeather {
    private String extractedString = null;
    private int extractedInt = 0;
    
    private Vector temperature = new Vector();
    private Vector humidity = new Vector();
    private Vector maxtime = new Vector();
    

    public String getExtractedString() {
            return extractedString;
    }
    public void setExtractedString(String extractedString) {
            this.extractedString = extractedString;
    }

    public int getExtractedInt() {
            return extractedInt;
    }
    public void setExtractedInt(int extractedInt) {
            this.extractedInt = extractedInt;
    }
    
    public void addtemperature(String myVal) {
    	temperature.add(myVal);
    }
    
    public void addhumidity(String myVal) {
    	humidity.add(myVal);
    }
    
    public Vector gettemperature() {
    	return temperature;
    }
    
    public Vector  gethumidity() {
    	return humidity;
    }
    
    public void addmaxtime(String myVal) {
    	maxtime.add(myVal);
    }
    public Vector getmaxtime() {
    	return maxtime;
    }
    
    
    
    public String toString(){
	    String myString="";
	    myString = temperature.toString() + humidity.toString();       
	    return myString;
}
    
    public String toString1(){
    	    String myString="";
    	    myString = temperature.toString();
            //return this.extractedString;
    	    return myString;
    }
    
    public String toString2(){
	    String myString="";
	    myString = humidity.toString();
        //return this.extractedString;
	    return myString;
}
}

