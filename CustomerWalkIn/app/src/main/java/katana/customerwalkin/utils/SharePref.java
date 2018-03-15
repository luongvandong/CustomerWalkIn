package katana.customerwalkin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import katana.customerwalkin.WalkInApplication;
import katana.customerwalkin.api.model.ColorModel;
import katana.customerwalkin.api.model.DataAuth;

/**
 * ka
 * 26/09/2017
 */

public class SharePref {

    private static final String AUTH = "AUTH";
    private static final String COLOR = "COLOR";

    public static void setAuth(DataAuth auth) {
        SharedPreferences preferences = WalkInApplication.getInstance()
                .getSharedPreferences(AUTH, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        editor.putString("AUTH", gson.toJson(auth));
        editor.apply();
    }

    public static DataAuth getDataAuth() {
        Gson gson = new Gson();
        SharedPreferences preferences = WalkInApplication.getInstance()
                .getSharedPreferences(AUTH, Context.MODE_PRIVATE);
        String auth = preferences.getString(AUTH, "");
        if (auth.equals("")) {
            return null;
        }
        return gson.fromJson(auth, DataAuth.class);
    }

    public static void setColor(ColorModel color) {
        SharedPreferences preferences = WalkInApplication.getInstance()
                .getSharedPreferences(COLOR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        editor.putString("COLOR", gson.toJson(color));
        editor.apply();
    }

    public static ColorModel getColorModel() {
        Gson gson = new Gson();
        SharedPreferences preferences = WalkInApplication.getInstance()
                .getSharedPreferences(COLOR, Context.MODE_PRIVATE);
        String auth = preferences.getString(COLOR, "");
        if (auth.equals("")) {
            return null;
        }
        return gson.fromJson(auth, ColorModel.class);
    }
}
