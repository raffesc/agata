package ascione.agata.ui.progetto.create;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ascione.agata.R;
import ascione.agata.service.network.CallbackResponseRestService;
import ascione.agata.service.network.NetworkManager;
import ascione.agata.service.network.request.IdRequest;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.ui.progetto.back_end.ComplAttributeBackEndAdapter;
import ascione.agata.ui.progetto.back_end.SimpleAttributeBackEndAdapter;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.ErrorAlertDialogs;
import ascione.agata.utils.Utils;
import ascione.agata.utils.UtilsElem;

public class CreaProgettoAdapter extends RecyclerView.Adapter<CreaProgettoAdapter.ViewHolder> {


    private LayoutInflater mInflater;
    private Context context;
    private List<Boolean> elems;
    boolean isIcon;
    String status;
    CreaProgettoFragment currFragment;

    // data is passed into the constructor
    public CreaProgettoAdapter(Context context,CreaProgettoFragment currFragment, List<Boolean> elems, boolean isIcon) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.elems = elems;
        this.isIcon = isIcon;
        this.currFragment = currFragment;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public CreaProgettoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_crea_progetto, parent, false);
        return new CreaProgettoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CreaProgettoAdapter.ViewHolder holder, final int position) {


        if (elems.get(position)) {
            holder.mExternalConstraintLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_card_white));
        } else {
            holder.mExternalConstraintLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_card_black));
        }
        if (isIcon) {
            holder.mImageViewIcon.setBackground(UtilsElem.getIcon(context,position+1));
            holder.mImageViewIcon.setVisibility(View.VISIBLE);
            holder.mImageViewBack.setVisibility(View.VISIBLE);
            holder.mImageView.setVisibility(View.GONE);
        } else {
            holder.mImageView.setBackgroundColor(UtilsElem.getColor(context,position+1));
            holder.mImageViewIcon.setVisibility(View.GONE);
            holder.mImageViewBack.setVisibility(View.GONE);
            holder.mImageView.setVisibility(View.VISIBLE);
        }
        holder.mExternalConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isIcon) {
                    ((CreaProgettoFragment) currFragment).changeIndexIcon(position);
                } else {
                    ((CreaProgettoFragment) currFragment).changeIndexColor(position);
                }
                elems.set(position,true);
                for (int i = 0; i < elems.size(); i++) {
                    if (i != position) {
                        elems.set(i,false);
                    }
                }
                notifyDataSetChanged();
            }
        });
    }







    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout mExternalConstraintLayout;
        ImageView mImageView,mImageViewBack, mImageViewIcon;
        ViewHolder(View itemView) {
            super(itemView);
            mExternalConstraintLayout = itemView.findViewById(R.id.external_constraint_layout);
            mImageView = itemView.findViewById(R.id.image);
            mImageViewBack = itemView.findViewById(R.id.image_view1);
            mImageViewIcon = itemView.findViewById(R.id.imageicon);
        }

    }





    // total number of cells
    @Override
    public int getItemCount() {
        return elems.size();
    }

}
