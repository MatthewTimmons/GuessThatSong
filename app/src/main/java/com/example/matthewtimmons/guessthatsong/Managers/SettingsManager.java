package com.example.matthewtimmons.guessthatsong.Managers;

public class SettingsManager {
    public static Integer songDurationDifficulty;
    public static Boolean showBandTextView;
    public static Boolean showSongTextView;

    public static Integer getSongDurationDifficulty() {
        return songDurationDifficulty;
    }

    public static void setSongDurationDifficulty(Integer songDurationDifficulty) {
        SettingsManager.songDurationDifficulty = songDurationDifficulty;
    }

    public static Boolean getShowBandTextView() {
        return showBandTextView;
    }

    public static void setShowBandTextView(Boolean showBandTextView) {
        SettingsManager.showBandTextView = showBandTextView;
    }

    public static Boolean getShowSongTextView() {
        return showSongTextView;
    }

    public static void setShowSongTextView(Boolean showSongTextView) {
        SettingsManager.showSongTextView = showSongTextView;
    }
}
