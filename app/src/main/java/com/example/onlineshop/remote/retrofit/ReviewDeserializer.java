package com.example.onlineshop.remote.retrofit;

import android.text.Html;

import com.example.onlineshop.model.Review;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ReviewDeserializer implements JsonDeserializer<Review> {
    @Override
    public Review deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject reviewObject = json.getAsJsonObject();
        int id = reviewObject.get("id").getAsInt();
        int productId = reviewObject.get("product_id").getAsInt();
        String content = Html.fromHtml(reviewObject.get("review").getAsString()).toString();
        String name = reviewObject.get("reviewer").getAsString();
        String email = reviewObject.get("reviewer_email").getAsString();
        int rating = reviewObject.get("rating").getAsInt();
        return new Review(id, productId, content, name, email, rating);
    }
}
