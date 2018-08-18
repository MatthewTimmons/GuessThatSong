package com.example.matthewtimmons.guessthatsong.Activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matthewtimmons.guessthatsong.Managers.LevelSaveDataManager;
import com.example.matthewtimmons.guessthatsong.Managers.SettingsManager;
import com.example.matthewtimmons.guessthatsong.Models.Level;
import com.example.matthewtimmons.guessthatsong.R;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    ImageView playSongImageView;
    TextView songTextView, bandTextView;
    Button keyboardButton;
    ConstraintLayout keyboardConstraintLayoutRow1, keyboardConstraintLayoutRow2, keyboardConstraintLayoutRow3;

    Level currentLevel;
    Integer currentLevelIndex, songDurationDifficulty, incorrectGuesses;
    Boolean showBandTextView, showSongTextView;
    MediaPlayer ring;

    String blankedBandName, blankedSongName;
    List<String> guesses;

    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_MEDIUM = 1;
    public static final int DIFFICULTY_HARD = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Get all difficulty settings and song information
        currentLevelIndex = (getIntent().getIntExtra("levelIndex", 0) - 1);
        currentLevel = LevelSaveDataManager.allLevelsData.get(currentLevelIndex);
        songDurationDifficulty = SettingsManager.getSongDurationDifficulty();
        showBandTextView = SettingsManager.getShowBandTextView();
        showSongTextView = SettingsManager.getShowSongTextView();
        guesses = new ArrayList<String>();
        guesses.add(" ");

        // Get View references
        playSongImageView = findViewById(R.id.play_song_image_view);
        songTextView = findViewById(R.id.song_title_text_view);
        bandTextView = findViewById(R.id.band_title_text_view);
        keyboardConstraintLayoutRow1 = findViewById(R.id.keyboard_constraint_layout_row_1);
        keyboardConstraintLayoutRow2 = findViewById(R.id.keyboard_constraint_layout_row_2);
        keyboardConstraintLayoutRow3 = findViewById(R.id.keyboard_constraint_layout_row_3);

        // Get blanked out band and song names
        blankedBandName = currentLevel.getBlankedOutAnswer(currentLevel.getBandName());
        blankedSongName = currentLevel.getBlankedOutAnswer(currentLevel.getSongName());
        Toast.makeText(this, blankedBandName, Toast.LENGTH_SHORT).show();
        setAnswerView();

        if (songDurationDifficulty == DIFFICULTY_EASY) { ring = MediaPlayer.create(this, currentLevel.getSongReferenceEasy()); }
        if (songDurationDifficulty == DIFFICULTY_MEDIUM) { ring = MediaPlayer.create(this, currentLevel.getSongReferenceMedium()); }
        if (songDurationDifficulty == DIFFICULTY_HARD) { ring = MediaPlayer.create(this, currentLevel.getSongReferenceHard()); }

        setKeyboardButtonFunctionality(keyboardConstraintLayoutRow1);
        setKeyboardButtonFunctionality(keyboardConstraintLayoutRow2);
        setKeyboardButtonFunctionality(keyboardConstraintLayoutRow3);

        playSongImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ring.start();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        ring.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Reset media player if user changes apps and comes back to a still open game
        if (songDurationDifficulty == DIFFICULTY_EASY) { ring = MediaPlayer.create(this, currentLevel.getSongReferenceEasy()); }
        if (songDurationDifficulty == DIFFICULTY_MEDIUM) { ring = MediaPlayer.create(this, currentLevel.getSongReferenceMedium()); }
        if (songDurationDifficulty == DIFFICULTY_HARD) { ring = MediaPlayer.create(this, currentLevel.getSongReferenceHard()); }
    }

    private void setAnswerView() {
        if (showSongTextView) { songTextView.setText(blankedSongName); }
        if (showBandTextView) { bandTextView.setText(blankedBandName); }
    }

    private void makeAGuess(String letter, View view) {
        guesses.add(letter);
        blankedSongName = checkForValues(currentLevel.getSongName(), letter, view);
        blankedBandName = checkForValues(currentLevel.getBandName(), letter, view);
        setAnswerView();
        Toast.makeText(this, guesses.toString(), Toast.LENGTH_SHORT).show();
    }

    private String checkForValues(String input, String letterGuessed, View view) {
        Integer counter = 0;
        for (Character letter : input.toCharArray()) {
            if (!guesses.contains(letter.toString().toUpperCase())) {
                input = input.replace(letter, '_');
                counter++;
            }
        }
        if (counter == 0) {
            incorrectGuesses++;
        } else {
            correctAnswer(letterGuessed);
        }
        return input;
    }

    public void wrongAnswer(String letter) {

    };

    public void correctAnswer(String letter) {
        Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
    }

    private void setKeyboardButtonFunctionality(ConstraintLayout view) {
        for (int i = 0; i < view.getChildCount(); i++) {
            view.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setEnabled(false);
                    makeAGuess(view.getTag().toString(), view);
                }
            });
        }
    }
}
