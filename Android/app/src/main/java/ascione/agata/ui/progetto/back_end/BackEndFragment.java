package ascione.agata.ui.progetto.back_end;

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
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import ascione.agata.R;
import ascione.agata.service.network.CallbackResponseRestService;
import ascione.agata.service.network.NetworkManager;
import ascione.agata.service.network.response.BackEndResponse;
import ascione.agata.service.network.response.EndpointResponse;
import ascione.agata.service.network.response.ProgettiResponse;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.service.singleton.UserSingleton;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.ErrorAlertDialogs;
import ascione.agata.utils.Utils;
import ascione.agata.utils.UtilsElem;

public class BackEndFragment extends Fragment {


    private TabLayout mTabLayoutMessages;
    private ViewPager mViewPagerMessages;

    private BackEndResponse backEndResponse;

    private EndpointResponse endpointResponse;


    public static BackEndFragment newInstance() {
        return new BackEndFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.back_end_fragment, container, false);
        getDatas(v);
        return v;
    }

    private void getDatas(View v) {

        if (Utils.isConnectedToNetwork(getCorrectContext())) {
            final ProgressDialog progressDialog = new ProgressDialog(getCorrectContext(), ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
            final Context context = getCorrectContext();
            NetworkManager.listBackEndAPI(ProgettoSingleton.getInstance().getProgetto().getId(),new CallbackResponseRestService<BackEndResponse>() {
                @Override
                public void onSuccess(BackEndResponse object) {
                    backEndResponse = object;
                    getEndpoints(progressDialog,v);
                }

                @Override
                public void onError(int errorCode, String error) {
                    getEndpoints(progressDialog,v);
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

    private void getEndpoints(ProgressDialog progressDialog, View v) {
        NetworkManager.listEndpoint(ProgettoSingleton.getInstance().getProgetto().getId(),new CallbackResponseRestService<EndpointResponse>() {
            @Override
            public void onSuccess(EndpointResponse object) {
                endpointResponse = object;
                progressDialog.cancel();
                setupViewPager(v);
                setupTabLayout(v);
            }

            @Override
            public void onError(int errorCode, String error) {
                progressDialog.cancel();
                setupViewPager(v);
                setupTabLayout(v);
            }
        });
    }

    private void setupViewPager(View v) {
        mViewPagerMessages = v.findViewById(R.id.view_pager_list);
        if (getActivity() != null) {
            PagerAdapter pagerAdapter = new BackEndFragment.ScreenPagerAdapter(getChildFragmentManager());
            mViewPagerMessages.setAdapter(pagerAdapter);
            mViewPagerMessages.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled( int position, float v, int i1 ) {
                    ((BackEndActivity) getCorrectContext()).setCurrIndex(position);
                }

                @Override
                public void onPageSelected( int position ) {
                    ((BackEndActivity) getCorrectContext()).setCurrIndex(position);
                }

                @Override
                public void onPageScrollStateChanged( int state ) {

                }
            });
            mViewPagerMessages.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });

        }
    }

    private void setupTabLayout(View v) {
        mTabLayoutMessages = v.findViewById(R.id.list_tab_layout);
        mTabLayoutMessages.setupWithViewPager(mViewPagerMessages);
        mTabLayoutMessages.setBackgroundColor(UtilsElem.getColor(getCorrectContext(),Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
    }

    private class ScreenPagerAdapter extends FragmentPagerAdapter {


        public ScreenPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public androidx.fragment.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new BackEndAPIFragment(backEndResponse);
                case 1:
                    return new BackEndEndpointFragment(endpointResponse);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "API";
                case 1:
                    return "Endpoint";
                default:
                    return "";
            }
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
