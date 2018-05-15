package com.monetization.varunam.kk_monetization;

import android.content.Context;
import android.content.SharedPreferences;

public class PointsPrefs {

    private static final String POINTS_PREFS_KEY = "points-prefs-key";
    private static final String POINTS_KEY = "points-key";
    private static final int POINTS_DEFAULT = 10;

    private static PointsPrefs pointsPrefs = null;
    private static SharedPreferences sharedPreferences;

    private PointsPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences(POINTS_PREFS_KEY, Context.MODE_PRIVATE);
    }

    public static PointsPrefs getInstance(Context context) {
        if (pointsPrefs == null) {
            pointsPrefs = new PointsPrefs(context);
        }
        return pointsPrefs;
    }

    public static PointsPrefs getInstance() {
        if (pointsPrefs != null)
            return pointsPrefs;

        throw new IllegalArgumentException("You should use getInstance(context) atleast once before using getInstance()");
    }

    public void addPoints(int points_to_be_added) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int points = getAvailablePoints();
        editor.putInt(POINTS_KEY, points + points_to_be_added);
        editor.apply();
    }

    public int getAvailablePoints() {
        return sharedPreferences.getInt(POINTS_KEY, POINTS_DEFAULT);
    }

    public boolean deductPoints(int points_to_be_deducted) {
        int points = getAvailablePoints();
        if (points >= points_to_be_deducted) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(POINTS_KEY, points - points_to_be_deducted);
            editor.apply();
            return true;
        } else
            return false;
    }

}
