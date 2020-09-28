package ascione.agata.ui.progetto.list_utenti;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import ascione.agata.R;
import ascione.agata.service.network.response.EndpointResponse;
import ascione.agata.service.network.response.UserListRecordResponse;
import ascione.agata.ui.progetto.back_end.BackEndEndpointAdapter;
import ascione.agata.utils.Utils;

public class UtentiFragment extends Fragment {


    private List<UserListRecordResponse> users;

    private boolean isFirst;


    UtentiFragment(List<UserListRecordResponse> users,boolean isFirst) {
        this.users = users;
        this.isFirst = isFirst;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.back_end_endpoint_fragment, container, false);
        setupRecyclerView(v);
        return v;
    }


    private void setupRecyclerView(View v) {
        if (getActivity() != null) {
            RecyclerView mRecyclerView = v.findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getCorrectContext()));
            UtentiAdapter adapter = new UtentiAdapter(getCorrectContext(), users,isFirst);
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
