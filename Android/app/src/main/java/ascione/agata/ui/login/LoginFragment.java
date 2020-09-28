package ascione.agata.ui.login;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ascione.agata.R;
import ascione.agata.service.network.CallbackResponseRestService;
import ascione.agata.service.network.NetworkManager;
import ascione.agata.service.network.request.LoginRequest;
import ascione.agata.service.network.response.UserListResponse;
import ascione.agata.service.network.response.UserResponse;
import ascione.agata.service.singleton.UserSingleton;
import ascione.agata.ui.MainActivity;
import ascione.agata.ui.registration.RegistrationActivity;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.ErrorAlertDialogs;
import ascione.agata.utils.Utils;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    private String username, password;

    EditText mUsernameEditText, mPasswordEditText;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.login_fragment, container, false);
        setupTextFields(v);
        setupLoginButton(v);
        setupRegister(v);
        setupIsAlreadyLogged();
        return v;
    }

    private void setupTextFields(View v) {
        mUsernameEditText = v.findViewById(R.id.username_edit_text);
        mPasswordEditText = v.findViewById(R.id.password_edit_text);
        mUsernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                username = mUsernameEditText.getText().toString();
            }
        });
        mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = mPasswordEditText.getText().toString();
            }
        });
    }

    private void setupIsAlreadyLogged() {
        SharedPreferences sharedPreferences = getCorrectContext().getSharedPreferences("SHARED_PREFERENCES", MODE_PRIVATE);
        // Se > M salavataggio credenziali utente in maniara sicura (Android Key Store)
        username = sharedPreferences.getString("username", null);
        password = sharedPreferences.getString("password", null);
        if (username != null && password != null) {
            mUsernameEditText.setText(username);
            mPasswordEditText.setText(password);
            doLogin();
        }
    }

    private void setupLoginButton(View v) {
        Button mLoginButton = v.findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (username == null || username.isEmpty()) {
                    ErrorAlertDialogs.missingFieldsError(getCorrectContext());
                    return;
                }
                if (password == null || password.isEmpty()) {
                    ErrorAlertDialogs.missingFieldsError(getCorrectContext());
                    return;
                }

                doLogin();
            }
        });

    }

    private void doLogin() {
        if (Utils.isConnectedToNetwork(getCorrectContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(getCorrectContext(), ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
                progressDialog.show();
                LoginRequest loginRequest = new LoginRequest(username,password);
                final Context context = getCorrectContext();
                NetworkManager.login(loginRequest,new CallbackResponseRestService<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse object) {
                        progressDialog.cancel();
                        UserSingleton.getInstance().setUser(object);
                        //reUpdateDataDrawer();
                        goToHome();
                        SharedPreferences sharedPreferences = getCorrectContext().getSharedPreferences("SHARED_PREFERENCES", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        // Se > M salavataggio credenziali utente in maniara sicura (Android Key Store)
                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.commit();
                        NetworkManager.listUsers(new CallbackResponseRestService<UserListResponse>() {
                            @Override
                            public void onSuccess(UserListResponse object) {
                                UserSingleton.getInstance().setAllUsersList(object.getRecords());
                            }

                            @Override
                            public void onError(int errorCode, String error) {
                                Log.d("ERROR","Erorre chiamata list users");
                            }
                        });
                        Log.d("RESULT","" + object);
                    }

                    @Override
                    public void onError(int errorCode,String error) {
                        progressDialog.cancel();
                        ErrorAlertDialogs.loginError(context);
                        Log.d("ERROR","SOMETHING WENT WRONG");
                    }
                });

            } else {
                Utils.showAlertDialog(getCorrectContext(), getString(R.string.no_connection_alert_title), getString(R.string.no_connection_alert_subtitle), getString(R.string.ok),"", getString(R.string.info), new CallbackAlertDialogChoise() {
                    @Override
                    public void onPositiveChoise() {

                    }

                    @Override
                    public void onNegativeChoise() {

                    }
                });
            }
    }

    private void setupRegister(View v) {
        Button mRegisterButton = v.findViewById(R.id.registration_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getCorrectContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void goToHome() {
        Intent intent = new Intent(getCorrectContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // Ritorna il contesto del fragment corrente
    @TargetApi(23)
    private Context getCorrectContext() {
        if (Utils.getSDKVersion() < 23) {
            return (Context) getActivity();
        } else {
            return getContext();
        }
    }




}
