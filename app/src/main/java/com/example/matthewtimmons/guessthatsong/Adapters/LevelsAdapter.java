package com.example.matthewtimmons.guessthatsong.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matthewtimmons.guessthatsong.Activities.LevelSelectActivity;
import com.example.matthewtimmons.guessthatsong.Models.Level;

import com.example.matthewtimmons.guessthatsong.R;

import java.util.List;

public class LevelsAdapter extends BaseAdapter {
    Context context;
    List<Level> levels;

    public LevelsAdapter(Context context, List<Level> levels) {
        this.context = context;
        this.levels = levels;
    }

    @Override
    public int getCount() {
        return levels.size();
    }

    @Override
    public Object getItem(int i) {
        return levels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Level level = levels.get(i);

        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.viewholder_level, null);
        }

        TextView levelNumberTextView = view.findViewById(R.id.level_number);
        ImageView checkboxImageView = view.findViewById(R.id.green_checkmark_icon_image_view);

        levelNumberTextView.setText("" + (i + 1));
        if (level.getUserHasBeatenThisLevel()) {
            checkboxImageView.setVisibility(View.VISIBLE);
        }

        return view;
    }
}
