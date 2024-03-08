package com.policarp.journal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONable {
    private static final Gson gson = new GsonBuilder().serializeNulls().create();
    public static Object fromJSON(String str, java.lang.Class c){
        if(c == null)
            return null;
        return gson.fromJson(str, c);
    }
    public static String toJSON(Object o){
        return gson.toJson(o);
    }


}
