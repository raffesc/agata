package ascione.agata.ui.progetto.list_utenti;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
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
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import ascione.agata.R;
import ascione.agata.service.network.CallbackResponseRestService;
import ascione.agata.service.network.NetworkManager;
import ascione.agata.service.network.response.ProgettiResponse;
import ascione.agata.service.network.response.UserListRecordResponse;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.service.singleton.UserSingleton;
import ascione.agata.ui.home.HomeAdapter;
import ascione.agata.ui.progetto.back_end.BackEndAPIFragment;
import ascione.agata.ui.progetto.back_end.BackEndEndpointFragment;
import ascione.agata.ui.progetto.back_end.BackEndFragment;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.ErrorAlertDialogs;
import ascione.agata.utils.Utils;
import ascione.agata.utils.UtilsElem;


public class ListaUtentiFragment extends Fragment {


    private TabLayout mTabLayoutMessages;
    private ViewPager mViewPagerMessages;

    private List<UserListRecordResponse> tuoTeamUsers;

    private List<UserListRecordResponse> tuttiUsers;

    AppCompatEditText mListaUtentiEditText;

    private boolean isSelectedFirst = true;


    public static ListaUtentiFragment newInstance() {
        return new ListaUtentiFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.lista_utenti_fragment, container, false);
        setupListaUtenti(v);
        setupUtenti(v);
        setupUtentiConstraintLayout(v);
        return  v;
    }

    private void  setupUtenti(View v) {
        List<String> usersCurrProj = UtilsElem.getListUsers(ProgettoSingleton.getInstance().getProgetto().getId_users());


        tuttiUsers = new ArrayList<>(UserSingleton.getInstance().getAllUsersList());


        tuoTeamUsers = new ArrayList<>();

        for (UserListRecordResponse userListRecordResponse : UserSingleton.getInstance().getAllUsersList()) {
            for (String userCurr: usersCurrProj) {
                if (userCurr != null && userCurr.equalsIgnoreCase(userListRecordResponse.getId())) {
                    tuoTeamUsers.add(userListRecordResponse);
                }
            }
        }




        setupRecyclerView(v);
    }



    private void setupListaUtenti(View v) {
        mListaUtentiEditText = v.findViewById(R.id.utenti);
        mListaUtentiEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {



                setupRecyclerView(v);

            }
        });
    }

    private void  setupRecyclerView(View v) {
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);

        List<String> usersCurrProj = UtilsElem.getListUsers(ProgettoSingleton.getInstance().getProgetto().getId_users());


        tuttiUsers = new ArrayList<>(UserSingleton.getInstance().getAllUsersList());

        tuoTeamUsers = new ArrayList<>();

        for (UserListRecordResponse userListRecordResponse : UserSingleton.getInstance().getAllUsersList()) {
            for (String userCurr: usersCurrProj) {
                if (userCurr != null && userCurr.equalsIgnoreCase(userListRecordResponse.getId())) {
                    tuoTeamUsers.add(userListRecordResponse);
                }
            }
        }

        if (mListaUtentiEditText.getText() != null && !mListaUtentiEditText.getText().toString().isEmpty()) {

            for (int i = 0; i <  tuttiUsers.size(); i++) {
                if (tuttiUsers.get(i).getUsername().indexOf(mListaUtentiEditText.getText().toString()) < 0) {
                    tuttiUsers.remove(i);
                }
            }

            for (int i = 0; i <  tuoTeamUsers.size(); i++) {

                if (tuoTeamUsers.get(i).getUsername().indexOf(mListaUtentiEditText.getText().toString()) < 0) {
                    tuoTeamUsers.remove(i);
                }
            }


        }

        if (isSelectedFirst) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getCorrectContext()));
            UtentiAdapter adapter = new UtentiAdapter(getCorrectContext(), tuoTeamUsers,true);
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getCorrectContext()));
            UtentiAdapter adapter = new UtentiAdapter(getCorrectContext(), tuttiUsers,false);
            recyclerView.setAdapter(adapter);
        }
    }

    private void setupUtentiConstraintLayout(View v) {
        ConstraintLayout mAllUtentiConstraintLayout = v.findViewById(R.id.allutenticonstraint);

        ConstraintLayout mMieiUtentiConstraintLayout = v.findViewById(R.id.tuoiutenticonstraint);

        mAllUtentiConstraintLayout.setBackground(UtilsElem.getBackgroundIconNoTop(getCorrectContext(),Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
        mAllUtentiConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSelectedFirst = false;
                setupRecyclerView(v);
            }
        });
        mMieiUtentiConstraintLayout.setBackground(UtilsElem.getBackgroundIconNoTop(getCorrectContext(),Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
        mMieiUtentiConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSelectedFirst = true;
                setupRecyclerView(v);
            }
        });

        TabLayout mListTabLayout = v.findViewById(R.id.list_tab_layout);
        mListTabLayout.setBackgroundColor(UtilsElem.getColor(getCorrectContext(),Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
        ConstraintLayout mConstraintBackGround = v.findViewById(R.id.top_search);
        mConstraintBackGround.setBackgroundColor(UtilsElem.getColor(getCorrectContext(),Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));

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
