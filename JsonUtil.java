package com.giulia.myapplication3.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by giulia on 21/06/17.
 */

public class JsonUtil {

    public static JSONArray toJSon(ArrayList<Myobject> o, String s) {
        try {
            // Here we convert Java Object to JSON
            JSONArray jsonArray2=new JSONArray();


            JSONObject jsonObj = new JSONObject();

            JSONArray jsonArray = new JSONArray();


            for (int m = 0; m < o.size(); m++) {
                JSONObject pnObj = new JSONObject();
                pnObj.put("tipo", o.get(m).getTipo());
                pnObj.put("name", o.get(m).getNome());
                jsonArray.put(pnObj);
            }

            jsonObj.put(s, jsonArray);
            jsonArray2.put(jsonObj);
            return jsonArray2;
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }

        return null;

    }
    public static JSONObject createJSON(JSONArray o,String s,String t){
        try {
            // Here we convert Java Object to JSON
            JSONObject jsonObj2 = new JSONObject();
            JSONArray jsonArray = new JSONArray();

                JSONObject pnObj = new JSONObject();
                pnObj.put(s, jsonArray);
                o.put(pnObj);


            return pnObj;
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }

        return null;

    }








}




// Set the first name/pair


           /* int i;
            for(i=0;i<2;i++){
            JSONObject pnObj = new JSONObject();
            pnObj.put("name", o.getNome());
            pnObj.put("type",o.getTipo());
            jsonArray.put(pnObj);

            }*/

            /*jsonObj.put("tipo", o.getTipo());


            JSONObject jsonAdd = new JSONObject(); // we need another object to store the address
            jsonAdd.put("address", person.getAddress().getAddress());
            jsonAdd.put("city", person.getAddress().getCity());
            jsonAdd.put("state", person.getAddress().getState());

            // We add the object to the main object
            jsonObj.put("address", jsonAdd);

            // and finally we add the phone number
            // In this case we need a json array to hold the java list
            JSONArray jsonArr = new JSONArray();

            for (PhoneNumber pn : person.getPhoneList() ) {
                JSONObject pnObj = new JSONObject();
                pnObj.put("num", pn.getNumber());
                pnObj.put("type", pn.getType());
                jsonArr.put(pnObj);
            }

            jsonObj.put("phoneNumber", jsonArr);
*/
