package ascione.agata.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ascione.agata.R;

public class UtilsElem {


    public static int getColor (Context context,int id) {
        switch (id) {
            case 1:
                return ContextCompat.getColor(context, R.color.project_color_1);
            case 2:
                return ContextCompat.getColor(context, R.color.project_color_2);
            case 3:
                return ContextCompat.getColor(context, R.color.project_color_3);
            default:
                return ContextCompat.getColor(context, R.color.project_color_4);
        }
    }


    public static Drawable getIcon (Context context, int id){
        switch (id) {
            case 2:
                return ContextCompat.getDrawable(context, R.drawable.ic_icon2);
            case 3:
                return ContextCompat.getDrawable(context, R.drawable.ic_icon3);
            default:
                return ContextCompat.getDrawable(context,R.drawable.ic_icon1);
        }
    }

    public static Drawable getBackgroundIcon (Context context, int id){
        switch (id) {
            case 2:
                return ContextCompat.getDrawable(context, R.drawable.bg_card_color_2_top_radius);
            case 3:
                return ContextCompat.getDrawable(context, R.drawable.bg_card_color_3_top_radius);
            default:
                return ContextCompat.getDrawable(context,R.drawable.bg_card_color_1_top_radius);
        }
    }

    public static Drawable getBackgroundIconNoTop (Context context, int id){
        switch (id) {
            case 2:
                return ContextCompat.getDrawable(context, R.drawable.bg_card_color_2);
            case 3:
                return ContextCompat.getDrawable(context, R.drawable.bg_card_color_3);
            default:
                return ContextCompat.getDrawable(context,R.drawable.bg_card_color_1);
        }
    }

    public static List<String> getListUsers (String users){
        List<String> listUsers = new ArrayList<>();
        Gson gson = new Gson();
        users = users.replaceAll("&quot;","");
        String[] risposta = gson.fromJson(users, String[].class);
        for (int i = 0; i < risposta.length; i++) {
            listUsers.add(risposta[i]);
        }
        return listUsers;
    }

    public static int getMainColor(Context context) {
        return ContextCompat.getColor(context, R.color.mainColor);
    }

}
