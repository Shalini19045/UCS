package com.iiitd.ucsf.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.iiitd.ucsf.R;
import com.iiitd.ucsf.activities.MainActivity;
import com.iiitd.ucsf.models.Data;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import com.iiitd.ucsf.application.ucsf;

public class Utilities {

    private static Context appcontext;
 public static void saveDataToList(Data saveddata,Context c){
        ArrayList<Data> dataArrayList;
        dataArrayList = getListOfdata(c);
        if(dataArrayList == null)
        { dataArrayList = new ArrayList<>();}
        dataArrayList.add(saveddata);
        saveListOfdata(dataArrayList,c);
        //
        }
    public static ArrayList<Data> getListOfdata(Context c){

        SharedPreferences shref = PreferenceManager.getDefaultSharedPreferences(c);
        Gson gson = new Gson();
        String response=shref.getString(Constants.KEY_DATA_LIST , "");
        ArrayList<Data> dataArrayList = gson.fromJson(response, new TypeToken<List<Data>>(){}.getType());
        return dataArrayList;
    }
    public static void saveListOfdata(ArrayList<Data> dataArrayList,Context c){
        SharedPreferences shref;
        SharedPreferences.Editor editor;
        shref = PreferenceManager.getDefaultSharedPreferences(c);

        Gson gson = new Gson();
        String json = gson.toJson(dataArrayList);

        editor = shref.edit();
        editor.remove(Constants.KEY_DATA_LIST).commit();
        editor.putString(Constants.KEY_DATA_LIST, json);
        editor.commit();
    }

    public static void savecountOfdata(int count,Context c){
        SharedPreferences shref;
        SharedPreferences.Editor editor;
        shref = PreferenceManager.getDefaultSharedPreferences(c);

           appcontext=c;
        editor = shref.edit();
        editor.remove(Constants.KEY_COUNT_LABEL).commit();
        editor.putInt(Constants.KEY_COUNT_LABEL , count);
        editor.commit();
    }

    public static int getcountOfdata(){
        SharedPreferences shref;
        SharedPreferences.Editor editor;
        shref = PreferenceManager.getDefaultSharedPreferences(appcontext);
          return shref.getInt(Constants.KEY_COUNT_LABEL, 0);

     }
    public static final boolean isInternetOn(Context context) {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet
//            Toast.makeText(context, " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(context, context.getResources().getString(R.string.internet_connectivity), Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
    public static void saveListOftimestamps(String audioname,ArrayList<String> dataArrayList,Context c){
        SharedPreferences shref;
        SharedPreferences.Editor editor;
        shref = PreferenceManager.getDefaultSharedPreferences(c);

        Gson gson = new Gson();
        String json = gson.toJson(dataArrayList);

        editor = shref.edit();
       // editor.remove(Constants.KEY_TIMESTAMPS).commit();
        editor.putString(audioname+"_"+Constants.KEY_TIMESTAMPS, json);
        editor.commit();
    }

    public static ArrayList<String> getListOftimestamps(Context c,String audioname){

        SharedPreferences shref = PreferenceManager.getDefaultSharedPreferences(c);
        Gson gson = new Gson();
        String response=shref.getString(audioname+"_"+Constants.KEY_TIMESTAMPS , "");
        ArrayList<String> dataArrayList = gson.fromJson(response, new TypeToken<ArrayList<String>>(){}.getType());
        return dataArrayList;
    }


}