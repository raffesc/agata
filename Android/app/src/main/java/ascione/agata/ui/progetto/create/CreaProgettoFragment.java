package ascione.agata.ui.progetto.create;

import androidx.lifecycle.ViewModelProviders;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import ascione.agata.R;
import ascione.agata.service.network.CallbackResponseRestService;
import ascione.agata.service.network.NetworkManager;
import ascione.agata.service.network.request.CreaFrontEndRequest;
import ascione.agata.service.network.request.CreaProgettoRequest;
import ascione.agata.service.network.response.FrontEndCreateResponse;
import ascione.agata.service.network.response.ProgettiRecordResponse;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.service.singleton.UserSingleton;
import ascione.agata.ui.MainActivity;
import ascione.agata.ui.progetto.back_end.SimpleAttributeBackEndAdapter;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.Constants;
import ascione.agata.utils.ErrorAlertDialogs;
import ascione.agata.utils.Utils;


public class CreaProgettoFragment extends Fragment {


    EditText mTitleEditText;

    private int indexColor = 0, indexIcon = 0;

    public static CreaProgettoFragment newInstance() {
        return new CreaProgettoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crea_progetto_fragment, container, false);
        setupTitleEditText(v);
        setupColorRecyclerView(v);
        setupIconRecyclerView(v);
        setupPlusButton(v);
        return v;
    }

    private void setupTitleEditText(View  v) {
        mTitleEditText = v.findViewById(R.id.text_pattern);
    }

    private void setupColorRecyclerView(View v){
        RecyclerView mRecyclerViewQuery = v.findViewById(R.id.recycler_view_query);
        mRecyclerViewQuery.setLayoutManager(new LinearLayoutManager(getCorrectContext(),LinearLayoutManager.HORIZONTAL,false));
        List<Boolean> elems = new ArrayList<>();
        elems.add(true);
        elems.add(false);
        elems.add(false);
        elems.add(false);
        CreaProgettoAdapter adapter = new CreaProgettoAdapter(getCorrectContext(), this,elems,false);
        mRecyclerViewQuery.setAdapter(adapter);
    }

    public void changeIndexColor(int index) {
        this.indexColor = index;
    }

    public void changeIndexIcon(int index) {
        this.indexIcon = index;
    }

    private void setupIconRecyclerView(View v) {
        RecyclerView mRecyclerViewQuery = v.findViewById(R.id.recycler_view_header);
        mRecyclerViewQuery.setLayoutManager(new LinearLayoutManager(getCorrectContext(),LinearLayoutManager.HORIZONTAL,false));
        List<Boolean> elems = new ArrayList<>();
        elems.add(true);
        elems.add(false);
        elems.add(false);
        elems.add(false);
        CreaProgettoAdapter adapter = new CreaProgettoAdapter(getCorrectContext(), this,elems,true);
        mRecyclerViewQuery.setAdapter(adapter);
    }

    private void setupPlusButton(View v) {
        Button mPlusButton = v.findViewById(R.id.add_pattern);
        mPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTitleEditText.getText() == null || mTitleEditText.getText().toString().isEmpty()) {
                    ErrorAlertDialogs.missingFieldsError(getCorrectContext());
                    return;
                }
                creaProgetto();
            }
        });
    }

    private void creaProgetto() {
        if (Utils.isConnectedToNetwork(getCorrectContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(getCorrectContext(), ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
            final Context context = getCorrectContext();
            int currIcon = indexIcon + 1;
            int currColor = indexColor + 1;
            CreaProgettoRequest creaFrontEndRequest = new CreaProgettoRequest(UserSingleton.getInstance().getUser().getId(),UserSingleton.getInstance().getUser().getId(),mTitleEditText.getText().toString(),""+ currIcon,""+currColor);
            NetworkManager.addProgetto(creaFrontEndRequest,new CallbackResponseRestService<ProgettiRecordResponse>() {
                @Override
                public void onSuccess(ProgettiRecordResponse object) {
                    progressDialog.cancel();
                    Intent intent = new Intent(getCorrectContext(), MainActivity.class);
                    startActivity(intent);
                    ((CreaProgettoActivity) getCorrectContext()).finish();
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
