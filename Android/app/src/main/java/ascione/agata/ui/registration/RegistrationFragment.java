package ascione.agata.ui.registration;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
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
import ascione.agata.service.network.request.RegistrationRequest;
import ascione.agata.service.network.response.Response;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.ErrorAlertDialogs;
import ascione.agata.utils.Utils;


public class RegistrationFragment extends Fragment {

    private RegistrationRequest registrationRequest = new RegistrationRequest();

    private String confermaPassword;


    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.registration_fragment, container, false);
        setupTextFields(v);
        setupRegistrami(v);
        return v;
    }

    private void setupTextFields(View v) {

        registrationRequest.setImage(" ");

        EditText mEmailEditText = v.findViewById(R.id.email_edit_text);
        EditText mPasswordEditText = v.findViewById(R.id.password_edit_text);
        EditText mConfermaPasswordEditText = v.findViewById(R.id.conferma_password_edit_text);
        EditText mUsernameEditText = v.findViewById(R.id.username_edit_text);
        EditText mNomeEditText = v.findViewById(R.id.name_edit_text);
        EditText mCognomeEditText = v.findViewById(R.id.cognome_edit_text);
        EditText mAgeEditText = v.findViewById(R.id.age_edit_text);



        mEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                registrationRequest.setEmail(mEmailEditText.getText().toString());
            }
        });

        mUsernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                registrationRequest.setUsername(mUsernameEditText.getText().toString());
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
                registrationRequest.setPassword(mPasswordEditText.getText().toString());
            }
        });

        mConfermaPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                confermaPassword = mConfermaPasswordEditText.getText().toString();
            }
        });

        mNomeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                registrationRequest.setNome(mNomeEditText.getText().toString());
            }
        });

        mCognomeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                registrationRequest.setCognome(mCognomeEditText.getText().toString());
            }
        });


        mAgeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                registrationRequest.setAge(mAgeEditText.getText().toString());
            }
        });


    }

    private void setupRegistrami(View v) {
        Button mRegisterButton = v.findViewById(R.id.login_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (registrationRequest.getNome() == null || registrationRequest.getNome().isEmpty()) {
                    ErrorAlertDialogs.missingFieldsError(getCorrectContext());
                    return;
                }
                if (registrationRequest.getCognome() == null || registrationRequest.getCognome().isEmpty()) {
                    ErrorAlertDialogs.missingFieldsError(getCorrectContext());
                    return;
                }
                if (registrationRequest.getEmail() == null || registrationRequest.getEmail().isEmpty()) {
                    ErrorAlertDialogs.missingFieldsError(getCorrectContext());
                    return;
                }
                if (registrationRequest.getUsername() == null || registrationRequest.getUsername().isEmpty()) {
                    ErrorAlertDialogs.missingFieldsError(getCorrectContext());
                    return;
                }
                if (registrationRequest.getAge() == null || registrationRequest.getAge().isEmpty()) {
                    ErrorAlertDialogs.missingFieldsError(getCorrectContext());
                    return;
                }
                if (registrationRequest.getPassword() == null || registrationRequest.getPassword().isEmpty()) {
                    ErrorAlertDialogs.missingFieldsError(getCorrectContext());
                    return;
                }
                if (confermaPassword == null || confermaPassword.isEmpty()) {
                    ErrorAlertDialogs.missingFieldsError(getCorrectContext());
                    return;
                }
                if (!Utils.isValid(registrationRequest.getEmail())) {
                    ErrorAlertDialogs.errorEmail(getCorrectContext());
                    return;
                }
                if (!confermaPassword.equals(registrationRequest.getPassword())) {
                    ErrorAlertDialogs.passwordDifferent(getCorrectContext());
                    return;
                }

                doRegistration();
            }
        });
    }

    private void doRegistration() {
        if (Utils.isConnectedToNetwork(getCorrectContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(getCorrectContext(), ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
            final Context context = getCorrectContext();
            NetworkManager.register(registrationRequest,new CallbackResponseRestService<Response>() {
                @Override
                public void onSuccess(Response object) {
                    progressDialog.cancel();
                    Utils.showAlertDialog(getCorrectContext(), getString(R.string.registration), object.getResponse(), getString(R.string.ok),"", getString(R.string.info), new CallbackAlertDialogChoise() {
                        @Override
                        public void onPositiveChoise() {
                            getActivity().finish();
                        }

                        @Override
                        public void onNegativeChoise() {

                        }
                    });
                }

                @Override
                public void onError(int errorCode, String error) {
                    progressDialog.cancel();
                    if (errorCode == 401) {
                        ErrorAlertDialogs.userAlreadyPresent(context);
                    } else {
                        ErrorAlertDialogs.genericError(context);
                    }
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

    // Ritorna il contesto del fragment corrente
    @TargetApi(23)
    private Context getCorrectContext() {
        if (Utils.getSDKVersion() < 23) {
            return (Context) getActivity();
        } else {
            return getContext();
        }
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
