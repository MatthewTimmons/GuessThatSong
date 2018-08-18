package com.example.matthewtimmons.guessthatsong.Managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.matthewtimmons.guessthatsong.Models.Level;
import com.example.matthewtimmons.guessthatsong.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LevelSaveDataManager {
    public static List<Level> allLevelsData;

    public static void instantiateLevelsWithSaveData(Context context) {
        List<Level> allLevels = new ArrayList<Level>();
        SharedPreferences sharedPreferences = SharedPreferencesManager.getSharedPreferences(context);
        Map<String, Object> val = (Map<String, Object>) sharedPreferences.getAll();

        allLevels.add(new Level("cyfmh", "Can You Feel My Heart", "Bring Me The Horizon", R.raw.can_you_feel_my_heart_5, R.raw.can_you_feel_my_heart_3, R.raw.can_you_feel_my_heart_1, (Boolean) val.get("cyfmh")));
        allLevels.add(new Level("drown", "Drown", "Bring Me The Horizon", R.raw.drown_5, R.raw.drown_3, R.raw.drown_1, (Boolean) val.get("2")));
        allLevels.add(new Level("february", "February", "A Thorn For Every Heart", R.raw.february_5, R.raw.february_3, R.raw.february_1, (Boolean) val.get("3")));
        allLevels.add(new Level("helena", "Helena", "My Chemical Romance", R.raw.helena_5, R.raw.helena_3, R.raw.helena_1, (Boolean) val.get("4")));
        allLevels.add(new Level("ino", "I'm Not Okay", "My Chemical Romance", R.raw.im_not_okay_5, R.raw.im_not_okay_3, R.raw.im_not_okay_1, (Boolean) val.get("5")));

        if (val != null) {
            Toast.makeText(context, val.toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "val was empty", Toast.LENGTH_SHORT).show();
        }

        allLevelsData = allLevels;
    }
}
