package com.example.matthewtimmons.guessthatsong.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matthewtimmons.guessthatsong.Managers.LevelSaveDataManager;
import com.example.matthewtimmons.guessthatsong.Managers.SettingsManager;
import com.example.matthewtimmons.guessthatsong.R;

import java.util.logging.Level;

public class MainActivity extends AppCompatActivity {
    ImageView difficultyInfoIconImageView;
    Button easyButton, mediumButton, hardButton, customButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LevelSaveDataManager.instantiateLevelsWithSaveData(this);

        difficultyInfoIconImageView = findViewById(R.id.difficulty_info_icon_image_view);
        easyButton = findViewById(R.id.easy_button);
        mediumButton = findViewById(R.id.medium_button);
        hardButton = findViewById(R.id.hard_button);
        customButton = findViewById(R.id.custom_button);

        final Intent goToLevelSelectIntent = new Intent(this, LevelSelectActivity.class);

        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLevelSelectIntent.putExtra("difficulty", "Easy");
                SettingsManager.setShowBandTextView(true);
                SettingsManager.setShowSongTextView(true);
                SettingsManager.setSongDurationDifficulty(0);
                startActivity(goToLevelSelectIntent);
            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLevelSelectIntent.putExtra("difficulty", "Medium");
                SettingsManager.setShowBandTextView(true);
                SettingsManager.setShowSongTextView(true);
                SettingsManager.setSongDurationDifficulty(1);
                startActivity(goToLevelSelectIntent);
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLevelSelectIntent.putExtra("difficulty", "Hard");
                SettingsManager.setShowBandTextView(true);
                SettingsManager.setShowSongTextView(true);
                SettingsManager.setSongDurationDifficulty(2);
                startActivity(goToLevelSelectIntent);
            }
        });

    }
}
