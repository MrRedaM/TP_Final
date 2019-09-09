package com.example.tp_final.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CustomMapSerializer implements JsonSerializer<HashMap<Plat, Integer>> {
    @Override
    public JsonElement serialize(HashMap<Plat, Integer> src, Type typeOfSrc, JsonSerializationContext context) {

        final JsonArray jsonObject = new JsonArray();
        for (Map.Entry<Plat, Integer> commande : src.entrySet()) {
        }

        return null;
    }
}
