package ascione.agata.ui.home;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import ascione.agata.R;
import ascione.agata.service.network.CallbackResponseRestService;
import ascione.agata.service.network.NetworkManager;
import ascione.agata.service.network.response.ProgettiResponse;
import ascione.agata.service.singleton.UserSingleton;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.ErrorAlertDialogs;
import ascione.agata.utils.Utils;

public class HomeFragment extends Fragment {


    private ProgettiResponse progettiResponse = new ProgettiResponse();

    private RecyclerView mRecyclerView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.home_fragment, container, false);
        loadData(v);
        return v;
    }

    private void loadData(View v) {
        ImageView mNoResultsImageView = v.findViewById(R.id.no_results);
        if (Utils.isConnectedToNetwork(getCorrectContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(getCorrectContext(), ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
            final Context context = getCorrectContext();
            NetworkManager.listProgetti(UserSingleton.getInstance().getUser().getId(),new CallbackResponseRestService<ProgettiResponse>() {
                @Override
                public void onSuccess(ProgettiResponse object) {
                    progressDialog.cancel();
                    progettiResponse = object;
                    if (progettiResponse != null && progettiResponse.getRecords() != null && progettiResponse.getRecords().size() > 0) {
                        mNoResultsImageView.setVisibility(View.GONE);
                    } else {
                        mNoResultsImageView.setVisibility(View.VISIBLE);
                    }
                    setupRecyclerView(v);
                    Log.d("RESULT","" + object);
                }

                @Override
                public void onError(int errorCode, String error) {
                    progressDialog.cancel();
                    mNoResultsImageView.setVisibility(View.VISIBLE);
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

    private void setupRecyclerView(View v) {
        if (getActivity() != null) {
            mRecyclerView = v.findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getCorrectContext()));
            HomeAdapter adapter = new HomeAdapter(getCorrectContext(), progettiResponse.getRecords());
            mRecyclerView.setAdapter(adapter);
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

