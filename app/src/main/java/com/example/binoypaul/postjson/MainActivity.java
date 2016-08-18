package com.example.binoypaul.postjson;


import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    TextView name;
    TextView place;
    TextView thing;
    TextView result;

    //Variables to catch the output
    String op1;
    String op2;
    String op3;

    String placeText, thingText;
    Long nameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (TextView) findViewById(R.id.nameInput);
        place = (TextView) findViewById(R.id.placeInput);
        thing = (TextView) findViewById(R.id.thingInput);
        result = (TextView) findViewById(R.id.Result);
    }

    public void actionTime(View v) {

        nameText = Long.parseLong(name.getText().toString());
        placeText = place.getText().toString();
        thingText = thing.getText().toString();
        Log.i("bTest", "Name is " + name.getText().toString());
        Log.i("bTest", "Place is " + place.getText().toString());
        Log.i("bTest", "Thing is " + thing.getText().toString());

        if(isConnected())
        {Log.i("bTest", "You are online");
        }
        else
        {Log.i("bTest", "You are offline");
        }

        //Always make sure the Async task's doInBackground has the network connection code. Else it slows the main thread
        AsyncT asyn = new AsyncT();
        asyn.execute();
        }



//Code to check if connection exists Not working correctly - check it
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
 //Add the below line to AndroidManifest.xml to make the above code to check if connection exists work
// <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>


    /* Inner class to get response */
    class AsyncT extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("bTest","This is in Pre execute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i("bTest", "This is in doInBackground");
            //Start
            StringBuilder sb = new StringBuilder();
            // https://httpbin.org/post is the site I use for testing this code
          //  String http = "https://httpbin.org/post";  // This is the server URL that will be hit with the post request , define your restAPI over here
          //  String http = "http://10.0.2.2:8080/BinoAlphaApp/locationRetrieve";
            String http = "http://c1ff5078.ngrok.io/BinoAlphaApp/locationRetrieve";
            // String http = "https://muerp.mu-sigma.com/restHCM/rest/isEngagementCreatedWithSowIdAndSubgroupId";  // This is the localHOST URL that will be hitting the restAPI
            Log.i("bTest", "Name is "+nameText+" Place is "+placeText+" Thing is "+thingText);
            Log.i("bTest","The URL is "+http);
            HttpURLConnection urlConnection=null;
            try {
                URL url = new URL(http);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setUseCaches(false);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.setRequestProperty("Content-Type","application/json");

                /*set the username and password here if you have one - begin*/
             //   urlConnection.setRequestProperty("UserId","pmt_user");
              //  urlConnection.setRequestProperty("password","musigma@321");
                /*set the username and password here if you have one - end*/

             //   urlConnection.setRequestProperty("Host", "post");
                //  urlConnection.setRequestProperty("Accept","*/*");
                urlConnection.connect();

                //Create JSONObject here, use this space to pack your params that you get from the user and want to send to the server URL
                JSONObject jsonSuperObj = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonParam = new JSONObject();

              /*jsonParam.put("name", nameText);
                jsonParam.put("place", placeText);
                jsonParam.put("thing", thingText);
                */
                jsonParam.put("locationId" , nameText); //"MuSigmaf64589" -- for Null Engagement  ,  MuSigma16491dd -- For engagement
              //  jsonParam.put("pmpkSubgroupId" , placeText); //"MuSigma1cc608" -- for Null Engagement  ,   MuSigma159dbb5 -- For Engagement
                System.out.println("The jsonParam that will be sent to the server is ###: "+jsonParam);

                jsonArray.put(jsonParam);
                Log.i("bTest","jsonArray is : "+jsonArray);
                jsonSuperObj.put("sows",jsonArray);
                Log.i("bTest","jsonSuperObj is "+jsonSuperObj);
                //This piece of code gets the whole response from the server as a JSON
                //Split it as you wish
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                Log.i("bTest","Th 1");
                /* This is the part where you actually send the JSON Object to the server - begin */
           //     out.write(jsonParam.toString());
                out.write( jsonSuperObj.toString());
                Log.i("bTest","Th 2");
                /* This is the part where you actually send the JSON Object to the server - ends */

                out.close();
                Log.i("bTest","Th 3");
                int HttpResult =urlConnection.getResponseCode();
                Log.i("bTest","Th 4");
               // if(HttpResult ==HttpURLConnection.HTTP_OK){
                    Log.i("bTest","Th 5");
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream(),"utf-8"));
                    Log.i("bTest","Th 6");
                    String line = null;
                    Log.i("bTest","Th 7");
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    Log.i("bTest","Th 8");
                    br.close();
                    Log.i("bTest","Th 9");
                    System.out.println("This is here"+sb.toString());
                    Log.i("bTest","Th 10");
                    JSONObject  ResponseAsJson = new JSONObject(sb.toString());
                    Log.i("bTest","Th 11");
                    System.out.println("The Response from the server is packed as json object : "+ResponseAsJson);
                    Log.i("bTest","Th 12");
                    //This is where you will spilt your JSON that you require, I have named the required json data's key as 'Json'
                    //Please look at the Response data from the server and modify this code Bino
                  //  JSONObject JsonRequired = new JSONObject(ResponseAsJson.getString("json"));
                   // System.out.println("");
                   // System.out.println("Json data required : "+JsonRequired);

                    //Now use the json Data to get your data as key value pair
                /*    System.out.println("name is "+JsonRequired.getString("name"));
                    System.out.println("place is "+JsonRequired.getString("place"));
                    System.out.println("thing is "+JsonRequired.getString("thing"));
                    */
                    op1=ResponseAsJson.getString("locationDetails");
                    Log.i("bTest","Th 13");
                   // op2=JsonRequired.getString("place");
                   // op3=JsonRequired.getString("thing");



             //   }else{
                //    Log.i("bTest","I m in else 1");
                    System.out.println(urlConnection.getResponseMessage());
              //  }
            } catch (MalformedURLException e) {

                e.printStackTrace();
            }
            catch (IOException e) {

                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch(Exception exep)
            {
                Log.i("bTest","This is the exception "+exep);
            }
            finally{
                if(urlConnection!=null)
                    urlConnection.disconnect();
            }
            //End

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i("bTest","This is in ABC Post execute");

            result.setText("Ans : "+op1);
            super.onPostExecute(aVoid);
        }
    }


    }


