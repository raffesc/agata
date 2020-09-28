package ascione.agata.ui.progetto.back_end;

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

import ascione.agata.R;
import ascione.agata.service.network.response.BackEndResponse;
import ascione.agata.service.network.response.EndpointResponse;
import ascione.agata.ui.home.HomeAdapter;
import ascione.agata.utils.Utils;

public class BackEndEndpointFragment extends Fragment {


    private TabLayout mTabLayoutMessages;
    private ViewPager mViewPagerMessages;

    private EndpointResponse endpointResponse;



    BackEndEndpointFragment(EndpointResponse endpointResponse) {
        this.endpointResponse = endpointResponse;
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
            BackEndEndpointAdapter adapter = new BackEndEndpointAdapter(getCorrectContext(), endpointResponse);
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
