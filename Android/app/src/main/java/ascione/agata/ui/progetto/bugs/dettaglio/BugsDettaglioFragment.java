package ascione.agata.ui.progetto.bugs.dettaglio;

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
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.ui.progetto.bugs.BugsAdapter;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.Constants;
import ascione.agata.utils.Utils;


public class BugsDettaglioFragment extends Fragment {


    List<BugsRecordResponse> bugs;

    public static BugsDettaglioFragment newInstance() {
        return new BugsDettaglioFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.bugs_dettaglio_fragment, container, false);
        bugs = ProgettoSingleton.getInstance().getBugs();
        setupRecyclerView(v);
        return v;
    }




    private void setupRecyclerView(View v) {
        if (getActivity() != null) {
            RecyclerView mRecyclerView = v.findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getCorrectContext()));
            BugsDettaglioAdapter adapter = new BugsDettaglioAdapter(getCorrectContext(), bugs);
            mRecyclerView.setAdapter(adapter);
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
