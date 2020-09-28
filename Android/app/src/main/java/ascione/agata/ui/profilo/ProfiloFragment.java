package ascione.agata.ui.profilo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import ascione.agata.R;
import ascione.agata.service.network.CallbackResponseRestService;
import ascione.agata.service.network.NetworkManager;
import ascione.agata.service.singleton.UserSingleton;
import ascione.agata.ui.login.LoginActivity;
import ascione.agata.utils.Utils;

import static android.content.Context.MODE_PRIVATE;

public class ProfiloFragment extends Fragment {



    public static ProfiloFragment newInstance() {
        return new ProfiloFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.profilo_fragment, container, false);
        setupImage(v);
        setupLogout(v);
        return v;
    }


    private void setupImage(View v) {
        ImageView mImageView = v.findViewById(R.id.image_profile);
        NetworkManager.downloadImage(UserSingleton.getInstance().getUser().getImage(), new CallbackResponseRestService<Bitmap>() {
            @Override
            public void onSuccess(Bitmap object) {
                mImageView.setImageBitmap(object);
            }

            @Override
            public void onError(int errorCode, String error) {
                Log.d("ERROR","Errore download immagine");
            }
        });
    }


    private void setupLogout(View v) {
        ConstraintLayout mLogoutConstraintLayout = v.findViewById(R.id.logout);
        mLogoutConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getCorrectContext().getSharedPreferences("SHARED_PREFERENCES", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // Se > M salavataggio credenziali utente in maniara sicura (Android Key Store)
                editor.putString("username", null);
                editor.putString("password", null);
                editor.commit();
                Intent intent = new Intent(getCorrectContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
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

