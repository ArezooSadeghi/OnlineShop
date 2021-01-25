package com.example.onlineshop.database;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Converter {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static String listToString(List<String> data) {
        return String.join("/", data);
    }

    @TypeConverter
    public static List<String> stringToList(String data) {
        return new ArrayList<String>(Arrays.asList(data.split("/")));
    }
}
