package ascione.agata.ui.progetto;

import androidx.lifecycle.ViewModelProviders;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ascione.agata.R;
import ascione.agata.ui.home.HomeAdapter;
import ascione.agata.utils.Utils;


public class DettaglioProgettoFragment extends Fragment {

    List<String> arrayElem = new ArrayList<>();

    public static DettaglioProgettoFragment newInstance() {
        return new DettaglioProgettoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.dettaglio_progetto_fragment, container, false);
        arrayElem.add("Front-End");
        arrayElem.add("Back-End");
        arrayElem.add("Design");
        arrayElem.add("Bugs");
        arrayElem.add("Appunti");
        arrayElem.add("Lista Utenti");
        setupRecyclerView(v);
        return v;
    }

    private void setupRecyclerView(View v) {
        if (getActivity() != null) {
           RecyclerView mRecyclerView = v.findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getCorrectContext()));
            DettaglioProgettoAdapter adapter = new DettaglioProgettoAdapter(getCorrectContext(), arrayElem);
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
