package ascione.agata.ui.progetto.front_end.crea;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ascione.agata.R;
import ascione.agata.service.network.CallbackResponseRestService;
import ascione.agata.service.network.NetworkManager;
import ascione.agata.service.network.request.CreaFrontEndRequest;
import ascione.agata.service.network.request.FrontEndAttributeRequest;
import ascione.agata.service.network.response.FrontEndAttributeResponse;
import ascione.agata.service.network.response.FrontEndCreateResponse;
import ascione.agata.service.network.response.ProgettiResponse;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.service.singleton.UserSingleton;
import ascione.agata.ui.home.HomeAdapter;
import ascione.agata.ui.progetto.front_end.FrontEndActivity;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.CustomDialogTwoText;
import ascione.agata.utils.CustomDialogWithSwitch;
import ascione.agata.utils.ErrorAlertDialogs;
import ascione.agata.utils.Utils;
import ascione.agata.utils.UtilsElem;


public class CreaFrontEndFragment extends Fragment {

    String type;

    int counter = 0;

    List<FrontEndAttributeResponse> attributes = new ArrayList<>();

    AppCompatEditText mEditText;

    FrontEndCreateResponse frontEndCreate;

    public static CreaFrontEndFragment newInstance(String type) {
        return new CreaFrontEndFragment(type);
    }

    CreaFrontEndFragment(String type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.crea_front_end_fragment, container, false);
        setupRecyclerView(v);
        TextView mAttributiTitle = v.findViewById(R.id.attributi_title);
        mAttributiTitle.setTextColor(UtilsElem.getColor(getCorrectContext(),Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
        setupEditText(v);
        setupAddAttribute(v);
        setupAddPattern(v);
        return  v;
    }

    private void setupRecyclerView(View v) {
        if (getActivity() != null) {
            RecyclerView mRecyclerView = v.findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getCorrectContext()));
            CreaFrontEndAdapter adapter = new CreaFrontEndAdapter(getCorrectContext(), attributes);
            mRecyclerView.setAdapter(adapter);
        }
    }

    private void setupEditText(View v) {
        mEditText = v.findViewById(R.id.text_pattern);
    }

    private  void setupAddAttribute(View v) {
        ImageView mAddAttribute = v.findViewById(R.id.add_attribute);
        mAddAttribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogWithSwitch cdd=new CustomDialogWithSwitch(getCorrectContext());
                cdd.show();
                cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (ProgettoSingleton.getInstance().getTitle() == null || ProgettoSingleton.getInstance().getTitle().isEmpty()
                                || ProgettoSingleton.getInstance().getDescrizione() == null || ProgettoSingleton.getInstance().getDescrizione().isEmpty()) {
                            return;
                        }
                        String title = ProgettoSingleton.getInstance().getTitle();
                        String descr = ProgettoSingleton.getInstance().getDescrizione();
                        Boolean priv = ProgettoSingleton.getInstance().getPrivate();
                        FrontEndAttributeResponse frontEndAttributeResponse = new FrontEndAttributeResponse();
                        frontEndAttributeResponse.setPriv(priv ? "1" : "2");
                        frontEndAttributeResponse.setType(descr);
                        frontEndAttributeResponse.setValue(title);
                        attributes.add(frontEndAttributeResponse);
                        ProgettoSingleton.getInstance().setTitle(null);
                        ProgettoSingleton.getInstance().setPrivate(null);
                        ProgettoSingleton.getInstance().setDescrizione(null);
                        setupRecyclerView(v);
                    }
                });
            }
        });
    }

    private void setupAddPattern(View  v) {
        Button mAddPattern = v.findViewById(R.id.add_pattern);
        mAddPattern.setBackground(UtilsElem.getBackgroundIconNoTop(getCorrectContext(),Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
        mAddPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditText.getText() == null || mEditText.getText().toString().isEmpty()) {
                    Utils.showAlertDialog(getCorrectContext(), "Attenzione", "Inserire un titolo per il pattern!", getString(R.string.ok),"", getString(R.string.info), new CallbackAlertDialogChoise() {
                        @Override
                        public void onPositiveChoise() {
                        }

                        @Override
                        public void onNegativeChoise() {

                        }
                    });
                    return;
                }
                if (Utils.isConnectedToNetwork(getCorrectContext())) {
                    final ProgressDialog progressDialog = new ProgressDialog(getCorrectContext(), ProgressDialog.THEME_HOLO_LIGHT);
                    progressDialog.setMessage(getString(R.string.loading));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    final Context context = getCorrectContext();
                    CreaFrontEndRequest creaFrontEndRequest =
                            new CreaFrontEndRequest(ProgettoSingleton.getInstance().getProgetto().getId(),
                                    ProgettoSingleton.getInstance().getProgetto().getId_owner(),mEditText.getText().toString(),"1",type);
                    NetworkManager.addFrontEnd(creaFrontEndRequest,new CallbackResponseRestService<FrontEndCreateResponse>() {
                        @Override
                        public void onSuccess(FrontEndCreateResponse object) {
                            frontEndCreate = object;
                            creaAttributi(progressDialog);
                            Log.d("RESULT","" + object);
                        }

                        @Override
                        public void onError(int errorCode, String error) {
                            progressDialog.cancel();
                            ErrorAlertDialogs.genericError(context);
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
        });
    }

    private void creaAttributi(ProgressDialog progressDialog) {
        FrontEndAttributeRequest[] frontEndAttributeRequests = new FrontEndAttributeRequest[attributes.size()];
        counter = frontEndAttributeRequests.length;
        for (int i = 0; i < frontEndAttributeRequests.length; i++) {
            final int index = i;
            frontEndAttributeRequests[i] = new FrontEndAttributeRequest(frontEndCreate.getId(),attributes.get(i).getValue(),attributes.get(i).getPriv(),attributes.get(i).getType());
            NetworkManager.addAttributesFrontEnd(frontEndAttributeRequests[i],new CallbackResponseRestService<Boolean>() {
                @Override
                public void onSuccess(Boolean object) {
                    progressDialog.cancel();
                    --counter;
                    if (counter == 0) {
                        Utils.showAlertDialog(getCorrectContext(), "Successo", "Pattern creato correttamente!", getString(R.string.ok),"", getString(R.string.info), new CallbackAlertDialogChoise() {
                            @Override
                            public void onPositiveChoise() {
                                Intent intent = new Intent(getCorrectContext(), FrontEndActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }

                            @Override
                            public void onNegativeChoise() {

                            }
                        });
                        return;
                    }
                    if (index == frontEndAttributeRequests.length - 1) {
                        progressDialog.cancel();
                        ErrorAlertDialogs.genericError(getCorrectContext());
                    }
                    Log.d("RESULT","" + object);
                }

                @Override
                public void onError(int errorCode, String error) {
                    if (index == frontEndAttributeRequests.length - 1) {
                        progressDialog.cancel();
                        ErrorAlertDialogs.genericError(getCorrectContext());
                    }
                    Log.d("ERROR","SOMETHING WENT WRONG");
                }
            });
        }
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
