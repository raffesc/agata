package ascione.agata.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ascione.agata.R;
import ascione.agata.ui.progetto.design.DesignActivity;

public class AttachFileBottomSheetDialog extends BottomSheetDialogFragment {

    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_camera_gallery, container, false);

        LinearLayout mCameraLinearLayout = view.findViewById(R.id.camera_linear_layout);
        LinearLayout mGalleryLinearLayout = view.findViewById(R.id.gallery_linear_layout);
        context = getCorrectContext();

        // Apertura fotocamera
        mCameraLinearLayout.setOnClickListener(v->{
            if(context instanceof DesignActivity){
                ((DesignActivity) context).openCamera();
            }
        });

        // Apertura galleria
        mGalleryLinearLayout.setOnClickListener(v->{
            if(context instanceof DesignActivity){
                ((DesignActivity) context).openGallery();
            }
        });

        return view;
    }

    @TargetApi(23)
    private Context getCorrectContext() {
        if (Utils.getSDKVersion() < 23) {
            return (Context) getActivity();
        }
        else {
            return getContext();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ((DesignActivity) context).resetAlpha();
    }
}
