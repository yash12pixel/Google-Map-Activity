package com.example.googlemapsactivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser
{
    private HashMap<String,String> getSingleNearbyplace(JSONObject googleplaceJson)
    {
        HashMap<String,String> googleplaceMap = new HashMap<>();
        String NameOfplace = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try
        {
            if(!googleplaceJson.isNull("name"))
            {
                NameOfplace = googleplaceJson.getString("name");
            }
            if(!googleplaceJson.isNull("vicinity"))
            {
                vicinity = googleplaceJson.getString("vicinity");
            }
            latitude = googleplaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");

            longitude = googleplaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");

            reference = googleplaceJson.getString("reference");

            googleplaceMap.put("place_name",NameOfplace);
            googleplaceMap.put("vicinity",vicinity);
            googleplaceMap.put("latitude",latitude);
            googleplaceMap.put("longitude",longitude);
            googleplaceMap.put("reference",reference);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return googleplaceMap;
    }

    private List<HashMap<String,String>> getAllNearbyPlaces (JSONArray jsonArray)
    {
        int counter = jsonArray.length();

        List<HashMap<String,String>> NearbyPlacesList = new ArrayList<>();

        HashMap<String,String> NearbyPlaceMap = null;

        for (int i=0; i<counter; i++)
        {
            try
            {
                NearbyPlaceMap = getSingleNearbyplace((JSONObject) jsonArray.get(i));
                NearbyPlacesList.add(NearbyPlaceMap);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return NearbyPlacesList;
    }

    public List<HashMap<String,String>> Parse(String jSONdata)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try
        {
            jsonObject = new JSONObject(jSONdata);
            jsonArray = jsonObject.getJSONArray("results");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return getAllNearbyPlaces(jsonArray );
    }
}
