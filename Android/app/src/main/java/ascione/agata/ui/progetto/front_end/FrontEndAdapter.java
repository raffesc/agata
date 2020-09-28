package ascione.agata.ui.progetto.front_end;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ascione.agata.R;
import ascione.agata.service.network.response.DesignRecordResponse;
import ascione.agata.service.network.response.FrontEndResponse;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.ui.progetto.design.DesignActivity;
import ascione.agata.ui.progetto.design.DesignImmagineAdapter;
import ascione.agata.ui.progetto.front_end.crea.CreaFrontEndActivity;
import ascione.agata.utils.UtilsElem;

public class FrontEndAdapter extends RecyclerView.Adapter<FrontEndAdapter.ViewHolder>{


    private LayoutInflater mInflater;
    private Context context;
    private FrontEndResponse frontEndResponse;

    // data is passed into the constructor
    public FrontEndAdapter(Context context, FrontEndResponse frontEndResponse) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.frontEndResponse = frontEndResponse;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public FrontEndAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_front_end, parent, false);
        return new FrontEndAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FrontEndAdapter.ViewHolder holder, final int position) {

        holder.mTitleTextView.setTextColor(UtilsElem.getColor(context, Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));

        switch (position) {
            case 0:
                holder.mTitleTextView.setText("Model");
                setupRecyclerViewModel(holder);
                holder.mAggiungiImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, CreaFrontEndActivity.class);
                        intent.putExtra("type","1");
                        context.startActivity(intent);
                    }
                });
                break;
            case 1:
                holder.mTitleTextView.setText("View");
                setupRecyclerViewView(holder);
                holder.mAggiungiImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, CreaFrontEndActivity.class);
                        intent.putExtra("type","2");
                        context.startActivity(intent);
                    }
                });
                break;
            case 2:
                holder.mTitleTextView.setText("Controller");
                setupRecyclerViewController(holder);
                holder.mAggiungiImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, CreaFrontEndActivity.class);
                        intent.putExtra("type","3");
                        context.startActivity(intent);
                    }
                });
                break;
            default:
                break;
        }


    }

    private void setupRecyclerViewModel(FrontEndAdapter.ViewHolder holder ) {
        holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        FrontEndElemAdapter adapter = new FrontEndElemAdapter(context, frontEndResponse.getModel());
        holder.mRecyclerView.setAdapter(adapter);
    }

    private void setupRecyclerViewView(FrontEndAdapter.ViewHolder holder ) {
        holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        FrontEndElemAdapter adapter = new FrontEndElemAdapter(context, frontEndResponse.getView());
        holder.mRecyclerView.setAdapter(adapter);
    }

    private void setupRecyclerViewController(FrontEndAdapter.ViewHolder holder ) {
        holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        FrontEndElemAdapter adapter = new FrontEndElemAdapter(context, frontEndResponse.getController());
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
        if (frontEndResponse != null){
            return 3;
        }else{
            return 0;
        }
    }

}
