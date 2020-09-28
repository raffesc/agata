package ascione.agata.ui.progetto.back_end;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ascione.agata.R;
import ascione.agata.service.network.response.BackEndAttributeResponse;

public class ComplAttributeBackEndAdapter extends RecyclerView.Adapter<ComplAttributeBackEndAdapter.ViewHolder>{


    private LayoutInflater mInflater;
    private Context context;
    private List<BackEndAttributeResponse> backEndResponse;

    // data is passed into the constructor
    public ComplAttributeBackEndAdapter(Context context, List<BackEndAttributeResponse> backEndResponse) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.backEndResponse = backEndResponse;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ComplAttributeBackEndAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_back_end_att_compl, parent, false);
        return new ComplAttributeBackEndAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ComplAttributeBackEndAdapter.ViewHolder holder, final int position) {
        holder.mTitleTextView.setText(backEndResponse.get(position).getTitle());
        holder.mSubtitleTextView.setText(backEndResponse.get(position).getValue());
    }









    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTextView, mSubtitleTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.title);
            mSubtitleTextView = itemView.findViewById(R.id.subtitle);
        }

    }





    // total number of cells
    @Override
    public int getItemCount() {
        if (backEndResponse != null ){
            return backEndResponse.size();
        }else{
            return 0;
        }
    }

}




