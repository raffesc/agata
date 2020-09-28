package ascione.agata.ui.progetto.list_utenti;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ascione.agata.R;
import ascione.agata.service.network.response.EndpointResponse;
import ascione.agata.service.network.response.UserListRecordResponse;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.ui.progetto.back_end.BackEndEndpointAdapter;
import ascione.agata.utils.UtilsElem;

public class UtentiAdapter extends RecyclerView.Adapter<UtentiAdapter.ViewHolder>{


    private LayoutInflater mInflater;
    private Context context;
    private List<UserListRecordResponse> users;
    private boolean isFirst;

    // data is passed into the constructor
    public UtentiAdapter(Context context, List<UserListRecordResponse> users, boolean isFirst) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.users = users;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public UtentiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_users, parent, false);
        return new UtentiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UtentiAdapter.ViewHolder holder, final int position) {
        holder.mNomeTextView.setText(users.get(position).getUsername());
    }







    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mNomeTextView;


        ViewHolder(View itemView) {
            super(itemView);
            mNomeTextView = itemView.findViewById(R.id.text);
        }

    }





    // total number of cells
    @Override
    public int getItemCount() {
        if (users != null ){
            return users.size();
        }else{
            return 0;
        }
    }

}
