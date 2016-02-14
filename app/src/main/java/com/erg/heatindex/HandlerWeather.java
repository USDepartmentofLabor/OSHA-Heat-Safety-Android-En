package com.erg.heatindex;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
 
public class HandlerWeather extends DefaultHandler{
 
        // ===========================================================
        // Fields
        // ===========================================================
        
        private boolean in_dwml = false;
        private boolean in_data = false;
        private boolean in_parameters = false;
        private boolean in_temperature = false;
        private boolean in_humidity = false;
        private boolean in_name = false;
        private boolean in_value = false;
        
        private boolean in_value_temp = false;
        private boolean in_value_humid = false;
        
        
        private boolean in_location = false;
        private boolean in_location_key = false;
        
        private boolean in_start_valid_time = false;
        
        private int mystarttemp=0;
        private int mystarthumid=0;

        private String myTimeString = null;
        
        
        private ParsedDataSetWeather myParsedExampleDataSetWeather = new ParsedDataSetWeather();
 
        // ===========================================================
        // Getter & Setter
        // ===========================================================
 
        public ParsedDataSetWeather getParsedData() {
                return this.myParsedExampleDataSetWeather;
        }
 
        // ===========================================================
        // Methods
        // ===========================================================
        @Override
        public void startDocument() throws SAXException {
                this.myParsedExampleDataSetWeather = new ParsedDataSetWeather();
        }
 
        @Override
        public void endDocument() throws SAXException {
                // Nothing to do
        }
 
        /** Gets be called on opening tags like: 
         * <tag> 
         * Can provide attribute(s), when xml was like:
         * <tag attribute="attributeValue">*/
        @Override
        public void startElement(String namespaceURI, String localName,
                        String qName, Attributes atts) throws SAXException {
        	/*
                if (localName.equals("dwml")) {
                        this.in_dwml = true;
                }else if (localName.equals("data")) {
                        this.in_data = true;
                }else if (localName.equals("location")) {
                        this.in_location = true;
                }
                else if (localName.equals("location-key")) {
                            this.in_location_key = true;
                }else if (localName.equals("point")) {
                        // Extract an Attribute
                        String attrValue = atts.getValue("latitude");
                        //int i = Integer.parseInt(attrValue);
                        myParsedExampleDataSetWeather.setExtractedString(attrValue);
                }*/
        	
        	if (localName.equals("dwml")) {
                this.in_dwml = true;
	        }else if (localName.equals("data")) {
	                this.in_data = true;
	        }else if (localName.equals("parameters")) {
	                this.in_parameters = true;
	                
	        }
	        else if (localName.equals("temperature")) {
	                   this.in_temperature = true;	                   
	                   String attrValue = atts.getValue("type");
	                   if (attrValue.equalsIgnoreCase("hourly")){
	                    	mystarttemp = 1;  
	                    } 	                    
	        }
	        else if (localName.equals("humidity")) {
                		this.in_humidity = true;
                		String attrValue = atts.getValue("type");  
                		if (attrValue.equalsIgnoreCase("relative")){
                			mystarthumid = 1;                
                		}
                
	        }   	        
	        else if (localName.equals("name")) {
	        	this.in_name = true;
	        } else if (localName.equals("value")) {
	        	this.in_value = true;	                
	        }
	        else if (localName.equals("start-valid-time")) {
                myTimeString = null;
	        	this.in_start_valid_time = true;	                
	        }      	
        	
        }
        
        /** Gets be called on closing tags like: 
         * </tag> */
        @Override
        public void endElement(String namespaceURI, String localName, String qName)
                        throws SAXException {  
        	/*
                if (localName.equals("dwml")) {
                    this.in_dwml = false;
                }else if (localName.equals("data")) {
                    this.in_data = false;
                }else if (localName.equals("location")) {
                    this.in_location = false;
                }
                else if (localName.equals("location_key")) {
                        this.in_location_key = false;
                }else if (localName.equals("point")) {
                	// Nothing to do here                
                }         */
        	if (localName.equals("dwml")) {
                this.in_dwml = false;
            }else if (localName.equals("data")) {
                this.in_data = false;
            }else if (localName.equals("parameters")) {
                this.in_parameters = false;
            }
            else if (localName.equals("temperature")) {
                    this.in_temperature = false;
                    this.mystarttemp = 0;
            }
            else if (localName.equals("humidity")) {    //sarah 2015
                this.in_humidity = false;
                this.mystarthumid = 0;
            }else if (localName.equals("name")) {
            		this.in_name = false;
            } else if (localName.equals("value")) {
            		this.in_value = false;	                
            }
            else if (localName.equals("start-valid-time")) {
                myParsedExampleDataSetWeather.addmaxtime(myTimeString);
                myTimeString = null;
        		this.in_start_valid_time = false;
        }
        	
        }
        
        /** Gets be called on the following structure: 
         * <tag>characters</tag> */
        @Override
    public void characters(char ch[], int start, int length) {
                if(this.in_value){
                	if (this.mystarttemp==1){
                		myParsedExampleDataSetWeather.addtemperature(new String(ch, start, length));
                	}
                	if (this.mystarthumid==1){
                		myParsedExampleDataSetWeather.addhumidity(new String(ch, start, length));
                	}
                	
                //myParsedExampleDataSetWeather.setExtractedString(new String(ch, start, length));
                }
                
                if(this.in_start_valid_time){
                    myTimeString += (new String(ch, start, length));
                    //myParsedExampleDataSetWeather.addmaxtime(new String(ch, start, length));
                }
                
    }
}
