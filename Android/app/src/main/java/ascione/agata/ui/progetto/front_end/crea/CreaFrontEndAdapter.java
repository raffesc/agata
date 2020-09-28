package ascione.agata.ui.progetto.front_end.crea;

import android.content.Context;
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
import ascione.agata.service.network.response.FrontEndAttributeResponse;
import ascione.agata.service.network.response.FrontEndResponse;
import ascione.agata.ui.progetto.front_end.FrontEndElemAdapter;

public class CreaFrontEndAdapter extends RecyclerView.Adapter<CreaFrontEndAdapter.ViewHolder> {


    private LayoutInflater mInflater;
    private Context context;
    private List<FrontEndAttributeResponse> attributes;

    // data is passed into the constructor
    public CreaFrontEndAdapter(Context context, List<FrontEndAttributeResponse> attributes) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.attributes = attributes;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public CreaFrontEndAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_attrib_crea_front_end, parent, false);
        return new CreaFrontEndAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CreaFrontEndAdapter.ViewHolder holder, final int position) {

        holder.mTitleTextView.setText(getAttribute(position));

    }


    private String getAttribute(int position) {
        String attributesString = "";
        if (attributes.get(position).getPriv().equalsIgnoreCase("1")) {
            attributesString += "- ";
        } else {
            attributesString += "+ ";
        }
        attributesString += attributes.get(position).getValue() + ": " + attributes.get(position).getType();
        attributesString += "\n";
        return attributesString;
    }






    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.title_att);
        }

    }





    // total number of cells
    @Override
    public int getItemCount() {
        if (attributes != null){
            return attributes.size();
        }else{
            return 0;
        }
    }

}
