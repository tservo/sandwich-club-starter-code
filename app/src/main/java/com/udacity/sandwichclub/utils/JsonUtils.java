package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {
        Log.d(TAG,json);
        Sandwich sandwich;
        try {
            JSONObject jsonObject = new JSONObject(json);

            // try to get name
            JSONObject nameObject = jsonObject.getJSONObject("name");
            String mainName  = nameObject.getString("mainName");


            // iterate over the JSONArray to make our alsoKnownAs ArrayList
            JSONArray aka = nameObject.getJSONArray("alsoKnownAs");
            ArrayList<String> alsoKnownAs = new ArrayList<>(aka.length());
            for (int i = 0; i < aka.length(); i++) {
                alsoKnownAs.add(aka.getString(i));
            }

            // get the simple strings
            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");
            String image = jsonObject.getString("image");

            // get the ingredients array
            JSONArray ingredientsJSON = jsonObject.getJSONArray("ingredients");
            ArrayList<String> ingredients = new ArrayList<>(ingredientsJSON.length());
            for (int i = 0; i < ingredientsJSON.length(); i++) {
                ingredients.add(ingredientsJSON.getString(i));
            }

            sandwich = new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);
        } catch (JSONException e) {
            Log.e(TAG,"Could not parse JSON "+json);
            return null;
        }
        // get main name:

        return sandwich;

    }
}
