package ascione.agata.ui.progetto.bugs;

import androidx.lifecycle.ViewModelProviders;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import ascione.agata.R;
import ascione.agata.service.network.CallbackResponseRestService;
import ascione.agata.service.network.NetworkManager;
import ascione.agata.service.network.response.BugsRecordResponse;
import ascione.agata.service.network.response.BugsResponse;
import ascione.agata.service.network.response.ProgettiResponse;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.service.singleton.UserSingleton;
import ascione.agata.ui.home.HomeAdapter;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.Constants;
import ascione.agata.utils.ErrorAlertDialogs;
import ascione.agata.utils.Utils;


public class BugsFragment extends Fragment {


    private BugsResponse bugsResponse = new BugsResponse();

    public static BugsFragment newInstance() {
        return new BugsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View  v =  inflater.inflate(R.layout.bugs_fragment, container, false);
        loadData(v);
        return v;
    }

    private void loadData(View v) {
        if (Utils.isConnectedToNetwork(getCorrectContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(getCorrectContext(), ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
            NetworkManager.listBugs(ProgettoSingleton.getInstance().getProgetto().getId(),new CallbackResponseRestService<BugsResponse>() {
                @Override
                public void onSuccess(BugsResponse object) {
                    progressDialog.cancel();
                    bugsResponse = object;
                    setupRecyclerView(v);
                    Log.d("RESULT","" + object);
                }

                @Override
                public void onError(int errorCode, String error) {
                    progressDialog.cancel();
                    setupRecyclerView(v);
                    /*Utils.showAlertDialog(context,
                            context.getResources().getString(R.string.warning),error,
                            context.getResources().getString(R.string.ok),
                            "", Constants.info, new CallbackAlertDialogChoise() {
                                @Override
                                public void onPositiveChoise() {
                                }
                                @Override
                                public void onNegativeChoise() {
                                }
                            }); */
                    Log.d("ERROR","SOMETHING WENT WRONG");
                }
            });

        } else {
            setupRecyclerView(v);
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
            RecyclerView mRecyclerView = v.findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getCorrectContext()));
            List<List<BugsRecordResponse>> bugsCurrent = new ArrayList<>();
            for (int i = 0; i < bugsResponse.getRecords().size(); i++) {
                boolean isAlreadyInside = false;
                for (int j = 0; j < bugsCurrent.size();j++) {
                    if (bugsResponse.getRecords().get(i).getId_category().equalsIgnoreCase(bugsCurrent.get(j).get(0).getId_category())) {
                        bugsCurrent.get(j).add(bugsResponse.getRecords().get(i));
                        isAlreadyInside = true;
                    }
                }
                if (!isAlreadyInside) {
                    List<BugsRecordResponse> bugsRecordResponses = new ArrayList<>();
                    bugsRecordResponses.add(bugsResponse.getRecords().get(i));
                    bugsCurrent.add(bugsRecordResponses);
                }
            }
            BugsAdapter adapter = new BugsAdapter(getCorrectContext(), bugsCurrent);
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
