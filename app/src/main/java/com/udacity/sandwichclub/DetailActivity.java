package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Helper method to join a list of strings into one string.
     * @param delimiter - used to join strings
     * @param stringList - list of strings to join
     * @return combined string using delimiter
     */
    private String join(String delimiter, List<String> stringList) {

        StringBuilder outString = new StringBuilder();
        for (int i=0; i < stringList.size(); i++) {
            outString.append(stringList.get(i));
            if (i < stringList.size() - 1) {
                // there's more to come so use delimiter
                outString.append(delimiter);
            }
        }

        return outString.toString();
    }

    private void populateUI(Sandwich sandwich) {
        TextView tvMainName = findViewById(R.id.main_name_tv);
        TextView tvAlsoKnownAs =  findViewById(R.id.also_known_tv);
        TextView tvIngredients =  findViewById(R.id.ingredients_tv);
        TextView tvDescription =  findViewById(R.id.description_tv);
        TextView tvPlaceOfOrigin =  findViewById(R.id.origin_tv);


        tvMainName.setText(sandwich.getMainName());
        tvAlsoKnownAs.setText(join(", ",sandwich.getAlsoKnownAs()));
        tvIngredients.setText(join(", ",sandwich.getIngredients()));
        tvDescription.setText(sandwich.getDescription());
        tvPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
    }
}
