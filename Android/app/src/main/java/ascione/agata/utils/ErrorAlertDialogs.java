package ascione.agata.utils;

import android.content.Context;

import ascione.agata.R;

public class ErrorAlertDialogs {

    public static void genericError(Context context){

        Utils.showAlertDialog(context,
                context.getResources().getString(R.string.warning),
                context.getResources().getString(R.string.generic_error_message),
                context.getResources().getString(R.string.ok),
                "", Constants.error, new CallbackAlertDialogChoise() {
                    @Override
                    public void onPositiveChoise() {

                    }

                    @Override
                    public void onNegativeChoise() {

                    }
                });

    }

    public static void errorEmail(Context context){

        Utils.showAlertDialog(context,
                context.getResources().getString(R.string.warning),
                context.getResources().getString(R.string.error_email),
                context.getResources().getString(R.string.ok),
                "", Constants.error, new CallbackAlertDialogChoise() {
                    @Override
                    public void onPositiveChoise() {

                    }

                    @Override
                    public void onNegativeChoise() {

                    }
                });

    }

    public static void passwordDifferent(Context context){

        Utils.showAlertDialog(context,
                context.getResources().getString(R.string.warning),
                context.getResources().getString(R.string.error_password),
                context.getResources().getString(R.string.ok),
                "", Constants.error, new CallbackAlertDialogChoise() {
                    @Override
                    public void onPositiveChoise() {

                    }

                    @Override
                    public void onNegativeChoise() {

                    }
                });

    }

    public static void connectionError(Context context){

        Utils.showAlertDialog(context,
                context.getResources().getString(R.string.warning),
                context.getResources().getString(R.string.error_message_no_connection),
                context.getResources().getString(R.string.ok),
                "", Constants.error, new CallbackAlertDialogChoise() {
                    @Override
                    public void onPositiveChoise() {
                    }

                    @Override
                    public void onNegativeChoise() {
                    }
                });

    }

    public static void missingFieldsError(Context context){

        Utils.showAlertDialog(context,
                context.getResources().getString(R.string.error_fill_all_required_fileds_title),
                context.getResources().getString(R.string.error_fill_all_required_fileds),
                context.getResources().getString(R.string.ok),
                "", Constants.error, new CallbackAlertDialogChoise() {
                    @Override
                    public void onPositiveChoise() {
                    }

                    @Override
                    public void onNegativeChoise() {
                    }
                });
    }

    public static void loginError(Context context){

        Utils.showAlertDialog(context,
                context.getResources().getString(R.string.warning),
                context.getResources().getString(R.string.login_error),
                context.getResources().getString(R.string.ok),
                "", Constants.info, new CallbackAlertDialogChoise() {
                    @Override
                    public void onPositiveChoise() {
                    }
                    @Override
                    public void onNegativeChoise() {
                    }
                });

    }

    public static void noLoginError(Context context){

        Utils.showAlertDialog(context,
                context.getResources().getString(R.string.warning),
                context.getResources().getString(R.string.no_login_error),
                context.getResources().getString(R.string.ok),
                "", Constants.info, new CallbackAlertDialogChoise() {
                    @Override
                    public void onPositiveChoise() {
                    }
                    @Override
                    public void onNegativeChoise() {
                    }
                });

    }


    public static void accessoDisabilitato(Context context) {
        Utils.showAlertDialog(context,
                context.getResources().getString(R.string.warning),
                context.getResources().getString(R.string.accesso_disabilitato),
                context.getResources().getString(R.string.ok),
                "", Constants.info, new CallbackAlertDialogChoise() {
                    @Override
                    public void onPositiveChoise() {
                    }
                    @Override
                    public void onNegativeChoise() {
                    }
                });
    }

    public static void userAlreadyPresent(Context context) {
        Utils.showAlertDialog(context,
                context.getResources().getString(R.string.error),
                context.getResources().getString(R.string.user_already_present),
                context.getResources().getString(R.string.ok),
                "", Constants.info, new CallbackAlertDialogChoise() {
                    @Override
                    public void onPositiveChoise() {
                    }
                    @Override
                    public void onNegativeChoise() {
                    }
                });
    }


}
