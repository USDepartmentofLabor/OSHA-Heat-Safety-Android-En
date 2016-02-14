package com.erg.heatindex;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * Create by SLuo on 8/28/2015.
 */

public class ProcessXML extends AsyncTask<String[], Integer, Long> {

    private ParsedDataSetWeather myParsedExampleDataSet;
    private ProgressDialog pDialog;
    private HeatIndex activity;
    private String url;


    public ProcessXML(HeatIndex activity, String url) {
        this.activity = activity;
        this.url = url;
    }

    protected Long doInBackground(String[]... params){
            long totalSize = 0;

            try{
                URL url = new URL(this.url);

                /* Get a SAXParser from the SAXPArserFactory. */
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();

                /* Get the XMLReader of the SAXParser we created. */
                XMLReader xr = sp.getXMLReader();

                /* Create a new ContentHandler and apply it to the XML-Reader*/
                HandlerWeather myExampleHandler = new HandlerWeather();
                xr.setContentHandler(myExampleHandler);

                /* Parse the xml-data from our URL. */
                //sarah 2015 comment out
                xr.parse(new InputSource(url.openStream()));

                myParsedExampleDataSet = myExampleHandler.getParsedData();

                totalSize = 1;
                return totalSize;
            }
            catch (Exception e) {
                //Log.e(DEBUG_TAG, "NOAA failed", e);
                return totalSize;
            }
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(activity);
        pDialog.setTitle("Get Weather Information");
        pDialog.setMessage("Loading...");
        pDialog.show();
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    @Override
    protected void onPostExecute(Long totalSize) {
        //call back data to main thread
        if (null != pDialog) {
            pDialog.dismiss();
        }
        activity.callBackData(myParsedExampleDataSet);
    }

    public ParsedDataSetWeather getMyData(){
        return myParsedExampleDataSet;
    }
}
