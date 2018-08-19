package com.example.matthewtimmons.guessthatsong.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.matthewtimmons.guessthatsong.Adapters.LevelsAdapter;
import com.example.matthewtimmons.guessthatsong.Managers.LevelSaveDataManager;
import com.example.matthewtimmons.guessthatsong.R;

public class LevelSelectActivity extends AppCompatActivity {
    GridView levelsGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        levelsGridView = findViewById(R.id.levels_grid_view);
        LevelsAdapter levelsAdapter = new LevelsAdapter(this, LevelSaveDataManager.allLevelsData);
        levelsGridView.setAdapter(levelsAdapter);

        levelsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent goToGameIntent = new Intent(LevelSelectActivity.this, GameActivity.class);
                goToGameIntent.putExtra("levelIndex", (i + 1));
                startActivity(goToGameIntent);
            }
        });

    }
}
