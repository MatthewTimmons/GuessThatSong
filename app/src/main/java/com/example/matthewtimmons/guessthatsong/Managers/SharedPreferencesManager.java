package com.example.matthewtimmons.guessthatsong.Managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.matthewtimmons.guessthatsong.Models.Level;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesManager {
    public static SharedPreferences getSharedPreferences(Context context) {
        return context
                .getSharedPreferences("com.example.matthewtimmons.guessthatsong",
                        Context.MODE_PRIVATE);
    }

    public static void clearAllSaveData(Context context) {
        getSharedPreferences(context).edit().clear().commit();
    }


}
