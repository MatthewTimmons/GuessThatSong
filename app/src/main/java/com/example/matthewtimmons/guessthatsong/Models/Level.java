package com.example.matthewtimmons.guessthatsong.Models;

import android.content.Context;
import android.media.MediaPlayer;

public class Level {
    String levelId, songName, bandName;
    Boolean userHasBeatenThisLevel;
    int songReferenceEasy, songReferenceMedium, songReferenceHard;

    public Level(String levelId, String songName, String bandName,
                 int songReferenceEasy, int songReferenceMedium, int songReferenceHard, Boolean userHasBeatenThisLevel) {
        this.levelId = levelId;
        this.songName = songName;
        this.bandName = bandName;
        this.songReferenceEasy = songReferenceEasy;
        this.songReferenceMedium = songReferenceMedium;
        this.songReferenceHard = songReferenceHard;
        if (userHasBeatenThisLevel != null) {
            this.userHasBeatenThisLevel = userHasBeatenThisLevel;
        } else {
            this.userHasBeatenThisLevel = false;
        }
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public Boolean getUserHasBeatenThisLevel() {
        return userHasBeatenThisLevel;
    }

    public void setUserHasBeatenThisLevel(Boolean userHasBeatenThisLevel) {
        this.userHasBeatenThisLevel = userHasBeatenThisLevel;
    }

    public int getSongReferenceEasy() {
        return songReferenceEasy;
    }

    public void setSongReferenceEasy(int songReferenceEasy) {
        this.songReferenceEasy = songReferenceEasy;
    }

    public int getSongReferenceMedium() {
        return songReferenceMedium;
    }

    public void setSongReferenceMedium(int songReferenceMedium) {
        this.songReferenceMedium = songReferenceMedium;
    }

    public int getSongReferenceHard() {
        return songReferenceHard;
    }

    public void setSongReferenceHard(int songReferenceHard) {
        this.songReferenceHard = songReferenceHard;
    }

    public String getBlankedOutAnswer(String input) {
        for (Character letter : input.toCharArray()) {
            if (!letter.toString().equals(" ")) {
                input = input.replace(letter, '_');
            }
        }
        return input;
    }
}
