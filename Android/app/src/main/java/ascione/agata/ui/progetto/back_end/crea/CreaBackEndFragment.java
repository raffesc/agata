package ascione.agata.ui.progetto.back_end.crea;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import ascione.agata.R;
import ascione.agata.service.network.CallbackResponseRestService;
import ascione.agata.service.network.NetworkManager;
import ascione.agata.service.network.request.BackEndAttributeRequest;
import ascione.agata.service.network.request.CreaFrontEndRequest;
import ascione.agata.service.network.response.BackEndAttributeResponse;
import ascione.agata.service.network.response.BackEndCreateAttributeResponse;
import ascione.agata.service.network.response.FrontEndCreateResponse;
import ascione.agata.service.network.response.FrontEndResponse;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.service.singleton.UserSingleton;
import ascione.agata.ui.progetto.back_end.BackEndActivity;
import ascione.agata.ui.progetto.back_end.ComplAttributeBackEndAdapter;
import ascione.agata.ui.progetto.back_end.SimpleAttributeBackEndAdapter;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.Constants;
import ascione.agata.utils.CustomDialogOneText;
import ascione.agata.utils.CustomDialogTwoText;
import ascione.agata.utils.ErrorAlertDialogs;
import ascione.agata.utils.Utils;

public class CreaBackEndFragment extends Fragment {


    RecyclerView mRecyclerViewQuery, mRecyclerViewHeader, mRecyclerViewBody;

    List<BackEndAttributeResponse> query = new ArrayList<>();
    List<BackEndAttributeResponse> header = new ArrayList<>();
    List<BackEndAttributeResponse> body = new ArrayList<>();
    SimpleAttributeBackEndAdapter adapterQuery,adapterBody;
    ComplAttributeBackEndAdapter adapterHeader;
    AppCompatEditText mRestEditText;


    public static CreaBackEndFragment newInstance() {
        return new CreaBackEndFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.crea_back_end_fragment, container, false);
        setupRest(v);
        setupPlusQuery(v);
        setupPlusHeader(v);
        setupPlusBody(v);
        setupPlusButton(v);
        return v;
    }

    private void setupRest(View v) {
        mRestEditText = v.findViewById(R.id.text_pattern);
    }

    private void setupPlusButton(View v) {
        Button mPlusButton = v.findViewById(R.id.add_pattern);
        mPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRestEditText.getText() == null || mRestEditText.getText().toString().isEmpty()) {
                    ErrorAlertDialogs.missingFieldsError(getCorrectContext());
                    return;
                }
                creaBackEnd();
            }
        });
    }

    private void setupPlusQuery(View v) {
        ImageView mPlusQuery = v.findViewById(R.id.add_attribute_query);
        mPlusQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogOneText cdd=new CustomDialogOneText(getCorrectContext());
                cdd.show();
                cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (ProgettoSingleton.getInstance().getDescrizione() == null || ProgettoSingleton.getInstance().getDescrizione().isEmpty()) {
                            return;
                        }
                        query.add(new BackEndAttributeResponse( " "," ",ProgettoSingleton.getInstance().getDescrizione(),"1","1"," "));
                        adapterQuery.notifyDataSetChanged();
                    }
                });
            }
        });
        RecyclerView mRecyclerViewQuery = v.findViewById(R.id.recycler_view_query);
        mRecyclerViewQuery.setLayoutManager(new LinearLayoutManager(getCorrectContext(),LinearLayoutManager.HORIZONTAL,false));
        adapterQuery = new SimpleAttributeBackEndAdapter(getCorrectContext(), query);
        mRecyclerViewQuery.setAdapter(adapterQuery);
    }

    private void setupPlusHeader(View v) {
        ImageView mPlusHeader = v.findViewById(R.id.add_attribute_header);
        mPlusHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogTwoText cdd=new CustomDialogTwoText(getCorrectContext());
                cdd.show();
                cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (ProgettoSingleton.getInstance().getTitle() == null || ProgettoSingleton.getInstance().getTitle().isEmpty()
                                || ProgettoSingleton.getInstance().getDescrizione() == null || ProgettoSingleton.getInstance().getDescrizione().isEmpty()) {
                            return;
                        }
                        header.add(new BackEndAttributeResponse(" "," ",ProgettoSingleton.getInstance().getTitle(),"1","2",ProgettoSingleton.getInstance().getDescrizione()));
                        adapterHeader.notifyDataSetChanged();
                    }
                });
            }
        });
        RecyclerView mRecyclerViewQuery = v.findViewById(R.id.recycler_view_header);
        mRecyclerViewQuery.setLayoutManager(new LinearLayoutManager(getCorrectContext(),LinearLayoutManager.HORIZONTAL,false));
        adapterHeader = new ComplAttributeBackEndAdapter(getCorrectContext(), header);
        mRecyclerViewQuery.setAdapter(adapterHeader);
    }

    private void setupPlusBody(View v) {
        ImageView mPlusBody = v.findViewById(R.id.add_attribute_body);
        mPlusBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogOneText cdd=new CustomDialogOneText(getCorrectContext());
                cdd.show();
                cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (ProgettoSingleton.getInstance().getDescrizione() == null || ProgettoSingleton.getInstance().getDescrizione().isEmpty()) {
                            return;
                        }
                        body.add(new BackEndAttributeResponse(" "," ",ProgettoSingleton.getInstance().getDescrizione(),"1","3"," "));
                        adapterBody.notifyDataSetChanged();
                    }
                });
            }
        });
        RecyclerView mRecyclerViewBody = v.findViewById(R.id.recycler_view_body);
        mRecyclerViewBody.setLayoutManager(new LinearLayoutManager(getCorrectContext(),LinearLayoutManager.HORIZONTAL,false));
        adapterBody = new SimpleAttributeBackEndAdapter(getCorrectContext(), body);
        mRecyclerViewBody.setAdapter(adapterBody);
    }

    private void creaBackEnd() {
        if (Utils.isConnectedToNetwork(getCorrectContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(getCorrectContext(), ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
            final Context context = getCorrectContext();
            CreaFrontEndRequest creaFrontEndRequest = new CreaFrontEndRequest(ProgettoSingleton.getInstance().getProgetto().getId(),
                    UserSingleton.getInstance().getUser().getId(),mRestEditText.getText().toString(),"1","1");
            NetworkManager.addBackEnd(creaFrontEndRequest,new CallbackResponseRestService<FrontEndCreateResponse>() {
                @Override
                public void onSuccess(FrontEndCreateResponse object) {
                    if (query.size() == 0 && body.size() == 0 && header.size() == 0) {
                        progressDialog.cancel();
                        return;
                    }
                    creaAttributes(object.getId(),progressDialog);
                }

                @Override
                public void onError(int errorCode, String error) {
                    progressDialog.cancel();
                    Utils.showAlertDialog(context,
                            context.getResources().getString(R.string.warning),error,
                            context.getResources().getString(R.string.ok),
                            "", Constants.info, new CallbackAlertDialogChoise() {
                                @Override
                                public void onPositiveChoise() {
                                }
                                @Override
                                public void onNegativeChoise() {
                                }
                            });
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

    private void creaAttributes(String idBackEnd, ProgressDialog progressDialog) {
        List<BackEndAttributeResponse> attToAdd = new ArrayList<>();
        for (BackEndAttributeResponse att: query) {
            att.setId_back_end(idBackEnd);
            attToAdd.add(att);
        }
        for (BackEndAttributeResponse att: header) {
            att.setId_back_end(idBackEnd);
            attToAdd.add(att);
        }
        for (BackEndAttributeResponse att: body) {
            att.setId_back_end(idBackEnd);
            attToAdd.add(att);
        }
        BackEndAttributeRequest backEndAttributeRequest = new BackEndAttributeRequest();
        backEndAttributeRequest.setAttributes(attToAdd);

        NetworkManager.addAttBackEnd(backEndAttributeRequest,new CallbackResponseRestService<BackEndCreateAttributeResponse>() {
            @Override
            public void onSuccess(BackEndCreateAttributeResponse object) {
                progressDialog.cancel();
                if (object != null && object.getAttributes() != null && object.getAttributes().size() == attToAdd.size()) {
                    Intent intent = new Intent(getCorrectContext(),BackEndActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    return;
                }
                ErrorAlertDialogs.genericError(getCorrectContext());
            }

            @Override
            public void onError(int errorCode, String error) {
                progressDialog.cancel();
                Utils.showAlertDialog(getCorrectContext(),
                        getCorrectContext().getResources().getString(R.string.warning),error,
                        getCorrectContext().getResources().getString(R.string.ok),
                        "", Constants.info, new CallbackAlertDialogChoise() {
                            @Override
                            public void onPositiveChoise() {
                            }
                            @Override
                            public void onNegativeChoise() {
                            }
                        });
                Log.d("ERROR","SOMETHING WENT WRONG");
            }
        });
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
