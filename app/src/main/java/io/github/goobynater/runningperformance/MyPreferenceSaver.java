package io.github.goobynater.runningperformance;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

/*
 * Created by Jason on 3/7/2017.
*/

public final class MyPreferenceSaver{
    private final SharedPreferences timepreference;
    private static MyPreferenceSaver sInstance;

    public static MyPreferenceSaver get(Context c) {
        if (sInstance == null) {
            sInstance = new MyPreferenceSaver(c);
        }
        return sInstance;
    }

    private MyPreferenceSaver(Context c) {
        HomeScreen homescreen = HomeScreen.get();
        if(homescreen != null)
            timepreference  = c.getSharedPreferences("MyTimes", Context.MODE_PRIVATE);
        else timepreference  = c.getSharedPreferences("MyTimes", Context.MODE_PRIVATE);
    }

    public void setString(String name, String val) {
        timepreference.edit().putString(name, val).apply();
    }

    public String getString(String name) {
        return timepreference.getString(name, "");
    }

    public void setInt(String name, int val) {
        timepreference.edit().putInt(name, val).apply();
    }

    public int getInt(String name) {
        return timepreference.getInt(name, -1);
    }

    public void setFloat(String name, float val) { timepreference.edit().putFloat(name, val).apply();}

    public float getFloat(String name) { return timepreference.getFloat(name, -1);}

    public String remove(String name) { String rstring = getString(name); timepreference.edit().remove(name).apply(); return rstring;}
}



