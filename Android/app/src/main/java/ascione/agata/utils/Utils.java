package ascione.agata.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import ascione.agata.R;
import ascione.agata.service.singleton.UserSingleton;

public class Utils {



    // Return SDK version
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static boolean isConnectedOnCellular(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return mobile.isConnectedOrConnecting();
    }

    public static boolean isConnectedOnWifi(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mobile.isConnectedOrConnecting();
    }

    public static boolean isConnectedToNetwork(Context context) {
        return isConnectedOnCellular(context) || isConnectedOnWifi(context);
    }

    // Setta il colore verde alla status bar
    public static void setStatusBarDefaultColor(Context context) {
        Window window = ((Activity)context).getWindow();
        window.setStatusBarColor(context.getResources().getColor(R.color.colorPrimary));
    }



    public static String encodeToBase64(String string) {
        byte[] byteData = Base64.encode(string.getBytes(), Base64.NO_WRAP);
        return new String(byteData);
    }

    public static void showAlertDialog(Context context, String title, String message, String positiveButton, String negativeButton, String icon, final CallbackAlertDialogChoise callbackAlertDialogChoise){
        Drawable alertIcon = null;
        if (icon.equals(Constants.error)){
            //alertIcon = context.getResources().getDrawable(R.drawable.ic_warning);
        }else if (icon.equals(Constants.info)){
            //alertIcon = context.getResources().getDrawable(R.drawable.ic_info);
        }

        AlertDialog alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setIcon(alertIcon)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with operation.
                        callbackAlertDialogChoise.onPositiveChoise();
                    }
                })
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel operation.
                        callbackAlertDialogChoise.onNegativeChoise();
                    }
                })
                .show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }

    public static String saveFileFromUri(Context context, android.net.Uri sourceUri, String fileName) {
        String storageFolder = context.getFilesDir().toString();
        String sourceFilename = getRealPathFromURI(context, sourceUri);

        String destinationFilename = storageFolder + "/" + fileName+"-copy.jpg";

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(sourceFilename));
            bos = new BufferedOutputStream(new FileOutputStream(destinationFilename, false));
            byte[] buf = new byte[1024];
            bis.read(buf);
            do {
                bos.write(buf);
            } while(bis.read(buf) != -1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) bis.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return destinationFilename;
    }


    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

/*
    public static void showNumberPickerInAlertDialog(String[] values, int index, Context context, String title, String message, String positiveButton, String negativeButton,  final CallbackNumberPicker callbackAlertDialogChoise){


        final NumberPicker numberPicker = new NumberPicker(context);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(values.length - 1);
        numberPicker.setDisplayedValues( values );
        //setNumberPickerTextColor(numberPicker,ContextCompat.getColor(context,R.color.colorPrimary));
        numberPicker.setValue(index);
        //numberPicker.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary));
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.my_dialog_theme)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setView(numberPicker)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with operation.
                        callbackAlertDialogChoise.onPositiveChoise(numberPicker.getValue());
                    }
                })
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel operation.
                        callbackAlertDialogChoise.onNegativeChoise();
                    }
                })
                .show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }*/

    private static void setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {

        try{
            Field selectorWheelPaintField = numberPicker.getClass()
                    .getDeclaredField("mSelectorWheelPaint");
            selectorWheelPaintField.setAccessible(true);
            ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
        }
        catch(NoSuchFieldException e){
            Log.w("TextColorNP", e);
        }
        catch(IllegalAccessException e){
            Log.w("TextColorNP", e);
        }
        catch(IllegalArgumentException e){
            Log.w("TextColorNP", e);
        }

        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText)
                ((EditText)child).setTextColor(color);
        }
        numberPicker.invalidate();
    }

    // Questo metodo formatta una data in formato stringa nel formato indicato da outputFormat
    public static String formatDateString(Context context, String dateString, String inputFormat, String outputFormat) {
        if (context != null) {
            Locale currentLocale = context.getResources().getConfiguration().locale;
            SimpleDateFormat parser = new SimpleDateFormat(inputFormat, currentLocale);

            Date date = null;
            try {
                date = parser.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat formatter = new SimpleDateFormat(outputFormat, currentLocale);
            return formatter.format(date);
        }else{
            return "";
        }

    }

    public static Date convertStringToDate(Context context, String dateString, String inputFormat) {
        Locale currentLocale = context.getResources().getConfiguration().locale;
        SimpleDateFormat formatter = new SimpleDateFormat(inputFormat, currentLocale);
        Date date = new Date();
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertDateToString(Context context, Date date, String outputFormat){
        Locale currentLocale = context.getResources().getConfiguration().locale;
        DateFormat dateFormat = new SimpleDateFormat(outputFormat, currentLocale);
        return dateFormat.format(date);
    }

    // Apre la tastiera
    public static void openKeyboard(final Context context){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if ( context != null) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        }, 300);
    }


    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static String getUser(String val) {
        for (int i = 0; i < UserSingleton.getInstance().getAllUsersList().size();i++) {
            if (val.equalsIgnoreCase(UserSingleton.getInstance().getAllUsersList().get(i).getId())) {
                return UserSingleton.getInstance().getAllUsersList().get(i).getUsername().substring(0,2).toUpperCase();
            }
        }
        return "";
    }

    // Converte dp in Pixel
    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    // Vibrazione all'interazione.
    public static void vibrateFeedback(Context context){
        Vibrator mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        mVibrator.vibrate(30);
    }

    // Return the inches of the device
    public static double getScreenSize(Activity context) {
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        return diagonalInches;
    }

    // Detect if device is larger than 6.5 inches
    public static boolean isLargeScreen(Activity context) {
        return getScreenSize(context) > 5;
    }

    // Function to make blink animation for TextView
    public static void makeAnimation(final TextView element, double time, final Drawable starterBackground,final  Drawable[] starterCompundDrawables,final int alpha) {
        element.setBackgroundColor(Color.WHITE);
        element.getBackground().setAlpha(45);
        element.setCompoundDrawables(new ColorDrawable(Color.WHITE),new ColorDrawable(Color.WHITE),new ColorDrawable(Color.WHITE),new ColorDrawable(Color.WHITE));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                element.setBackground(starterBackground);
                element.getBackground().setAlpha(alpha);
                element.setCompoundDrawables(starterCompundDrawables[0],starterCompundDrawables[1],starterCompundDrawables[2],starterCompundDrawables[3]);
            }
        }, (long) time);
    }
    // function to make blink animation for RecyclerView
    public static void makeAnimation(final RecyclerView element, double time, final  Drawable starterBackground) {
        element.setBackgroundColor(Color.WHITE);
        element.getBackground().setAlpha(45);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                element.setBackground(starterBackground);
            }
        }, (long) time);
    }

    public static boolean isCurrentDeviceSamsungGalaxyA40(){
        String deviceModel = Build.MODEL;
        if (deviceModel.equalsIgnoreCase("SM-A405FN")){
            return true;
        }else {
            return false;
        }
    }

}
