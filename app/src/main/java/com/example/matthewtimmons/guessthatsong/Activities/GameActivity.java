package com.example.matthewtimmons.guessthatsong.Activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matthewtimmons.guessthatsong.Managers.LevelSaveDataManager;
import com.example.matthewtimmons.guessthatsong.Managers.SettingsManager;
import com.example.matthewtimmons.guessthatsong.Managers.SharedPreferencesManager;
import com.example.matthewtimmons.guessthatsong.Models.Level;
import com.example.matthewtimmons.guessthatsong.R;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    ImageView playSongImageView;
    TextView songTextView, bandTextView, songNameHeaderTextView, bandNameHeaderTextView,
            correctGuessesTextView, incorrectGuessesTextView, remainingGuessesTextView;
    EditText guessEditText;
    Button submitGuessButton;

    Level currentLevel;
    Integer currentLevelIndex, songDurationDifficulty;
    Boolean showBandTextView, showSongTextView, gameStillRunning;
    MediaPlayer ring;

    String blankedBandName, blankedSongName;
    List<String> guesses, correctGuesses, incorrectGuesses;

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
        showSongTextView = SettingsManager.getShowSongTextView();;
        correctGuesses = new ArrayList<String>();
        incorrectGuesses = new ArrayList<String>();
        gameStillRunning = true;
        guesses = new ArrayList<String>();
        guesses.add(" ");

        // Get View references
        playSongImageView = findViewById(R.id.play_song_image_view);
        correctGuessesTextView = findViewById(R.id.correct_guesses);
        incorrectGuessesTextView = findViewById(R.id.incorrect_guesses);
        remainingGuessesTextView = findViewById(R.id.remaining_guesses);
        songTextView = findViewById(R.id.song_title_text_view);
        songNameHeaderTextView = findViewById(R.id.song_header_text_view);
        bandTextView = findViewById(R.id.band_title_text_view);
        bandNameHeaderTextView = findViewById(R.id.band_header_text_view);
        guessEditText = findViewById(R.id.submit_guess_edit_text);
        submitGuessButton = findViewById(R.id.submit_guess_button);

        // Get blanked out band and song names
        blankedBandName = currentLevel.getBlankedOutAnswer(currentLevel.getBandName());
        blankedSongName = currentLevel.getBlankedOutAnswer(currentLevel.getSongName());
        setAnswerView();

        if (songDurationDifficulty == DIFFICULTY_EASY) { ring = MediaPlayer.create(this, currentLevel.getSongReferenceEasy()); }
        if (songDurationDifficulty == DIFFICULTY_MEDIUM) { ring = MediaPlayer.create(this, currentLevel.getSongReferenceMedium()); }
        if (songDurationDifficulty == DIFFICULTY_HARD) { ring = MediaPlayer.create(this, currentLevel.getSongReferenceHard()); }

        playSongImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ring.start();
            }
        });

        guessEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent event) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    submitGuessButton.performClick();
                    guessEditText.setText("");
                    return true;
                }
                return false;
            }
        });

        submitGuessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guess = guessEditText.getText().toString().toUpperCase();
                if (!guesses.contains(guess) && gameStillRunning) {
                    makeAGuess(guessEditText.getText().toString().toUpperCase());
                } else {
                    Toast.makeText(GameActivity.this, "You already guessed " + guess, Toast.LENGTH_SHORT).show();
                }
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
        if (!showSongTextView) {
            songTextView.setVisibility(View.GONE);
            songNameHeaderTextView.setVisibility(View.GONE);
        } else if (!showBandTextView){
            bandTextView.setVisibility(View.GONE);
            bandNameHeaderTextView.setVisibility(View.GONE);
        }
    }

    private void makeAGuess(String letter) {
        String songBefore = blankedSongName;
        String bandBefore = blankedBandName;
        guesses.add(letter);
        blankedSongName = checkForValues(currentLevel.getSongName());
        blankedBandName = checkForValues(currentLevel.getBandName());
        if (currentLevel.getBandName().toUpperCase().contains(letter) || currentLevel.getSongName().toUpperCase().contains(letter)) {
            correctGuesses.add(letter);
            correctGuessesTextView.setText(TextUtils.join(", ", correctGuesses));
        } else {
            incorrectGuesses.add(letter);
            incorrectGuessesTextView.setText(TextUtils.join(", ", incorrectGuesses));
            remainingGuessesTextView.setText(String.valueOf(4 - incorrectGuesses.size()));
        }
        setAnswerView();
        checkWinState();
    }

    private String checkForValues(String input) {
        for (Character letter : input.toCharArray()) {
            if (!guesses.contains(letter.toString().toUpperCase())) {
                input = input.replace(letter, '_');
            }
        }
        return input;
    }

    private void checkWinState() {
        if (!blankedSongName.contains("_") && !blankedBandName.contains("_")) {
            Toast.makeText(this, "Congratulations! You won", Toast.LENGTH_SHORT).show();
            gameStillRunning = false;
            remainingGuessesTextView.setBackgroundColor(Color.GREEN);
            remainingGuessesTextView.setTextColor(Color.WHITE);
            SharedPreferences sharedPreferences = SharedPreferencesManager.getSharedPreferences(this);
            sharedPreferences.edit().putBoolean(currentLevel.getLevelId(), true).commit();
        } else {
            if (incorrectGuesses.size() > 3) {
                gameStillRunning = false;
                remainingGuessesTextView.setBackgroundColor(Color.RED);
                remainingGuessesTextView.setTextColor(Color.WHITE);
                Toast.makeText(this, "Sorry, you lose", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
