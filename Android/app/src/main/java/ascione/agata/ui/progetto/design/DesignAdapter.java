package ascione.agata.ui.progetto.design;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import ascione.agata.R;
import ascione.agata.service.network.CallbackResponseRestService;
import ascione.agata.service.network.NetworkManager;
import ascione.agata.service.network.request.BugRequest;
import ascione.agata.service.network.request.CategoryRequest;
import ascione.agata.service.network.response.BugsCategoryResponse;
import ascione.agata.service.network.response.BugsRecordResponse;
import ascione.agata.service.network.response.DesignRecordResponse;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.CustomDialogTwoText;
import ascione.agata.utils.Utils;
import ascione.agata.utils.UtilsElem;

public class DesignAdapter extends RecyclerView.Adapter<DesignAdapter.ViewHolder>{


    private LayoutInflater mInflater;
    private Context context;
    private List<List<DesignRecordResponse>> design;

    // data is passed into the constructor
    public DesignAdapter(Context context, List<List<DesignRecordResponse>> design) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.design = design;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public DesignAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_design, parent, false);
        return new DesignAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DesignAdapter.ViewHolder holder, final int position) {

        holder.mAggiungiConstraintLayout.setBackground(UtilsElem.getBackgroundIconNoTop(context,Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
        holder.mTitleTextView.setTextColor(UtilsElem.getColor(context,Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));

        if (position == design.size()) {
            holder.mExternalConstraintLayout.setVisibility(View.GONE);
            holder.mAggiungiConstraintLayout.setVisibility(View.VISIBLE);
            holder.mAggiungiConstraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((DesignActivity) context).aggiungiCategory();
                }
            });
        } else {
            holder.mAggiungiImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((DesignActivity) context).setCategory(design.get(position).get(0).getId_category());
                    ((DesignActivity) context).showDocumentUploadAlert();
                }
            });
            holder.mExternalConstraintLayout.setVisibility(View.VISIBLE);
            holder.mAggiungiConstraintLayout.setVisibility(View.GONE);
            holder.mTitleTextView.setText(design.get(position).get(0).getNome_category());
            setupRecyclerView(position,holder);
        }

    }


    private void setupRecyclerView(int position, DesignAdapter.ViewHolder holder) {

        holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        DesignImmagineAdapter adapter = new DesignImmagineAdapter(context, design.get(position));
        holder.mRecyclerView.setAdapter(adapter);

    }





    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTextView;
        ImageView mAggiungiImageView;
        ConstraintLayout mExternalConstraintLayout, mAggiungiConstraintLayout;
        RecyclerView mRecyclerView;

        ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.nome_category);
            mAggiungiImageView = itemView.findViewById(R.id.add);
            mExternalConstraintLayout = itemView.findViewById(R.id.external_constraint_layout);
            mRecyclerView = itemView.findViewById(R.id.recycler_view);
            mAggiungiConstraintLayout = itemView.findViewById(R.id.add_card);
        }

    }





    // total number of cells
    @Override
    public int getItemCount() {
        if (design != null){
            return design.size() + 1;
        }else{
            return 1;
        }
    }

}
