package ascione.agata.ui.progetto;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ascione.agata.R;
import ascione.agata.service.network.response.ProgettiRecordResponse;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.ui.progetto.appunti.AppuntiActivity;
import ascione.agata.ui.progetto.back_end.BackEndActivity;
import ascione.agata.ui.progetto.bugs.BugsActivity;
import ascione.agata.ui.progetto.design.DesignActivity;
import ascione.agata.ui.progetto.front_end.FrontEndActivity;
import ascione.agata.ui.progetto.list_utenti.ListaUtentiActivity;
import ascione.agata.utils.Utils;
import ascione.agata.utils.UtilsElem;

public class DettaglioProgettoAdapter extends RecyclerView.Adapter<DettaglioProgettoAdapter.ViewHolder>{


    private LayoutInflater mInflater;
    private Context context;
    private List<String> arrayElem;

    // data is passed into the constructor
    public DettaglioProgettoAdapter(Context context, List<String> arrayElem) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.arrayElem = arrayElem;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public DettaglioProgettoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_dettaglio_progetto, parent, false);
        return new DettaglioProgettoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DettaglioProgettoAdapter.ViewHolder holder, final int position) {

        holder.mTitleTextView.setText(arrayElem.get(position));
        holder.mTitleTextView.setTextColor(UtilsElem.getColor(context,Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
        holder.mIconText.setBackground(UtilsElem.getBackgroundIconNoTop(context,Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
        holder.mExternalConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position) {
                    case 0:
                        Intent intent2 = new Intent(context, FrontEndActivity.class);
                        context.startActivity(intent2);
                        break;
                    case 1:
                        Intent intent3 = new Intent(context, BackEndActivity.class);
                        context.startActivity(intent3);
                        break;
                    case 2:
                        Intent intent1 = new Intent(context, DesignActivity.class);
                        context.startActivity(intent1);
                        break;
                    case 3:
                        Intent intent = new Intent(context, BugsActivity.class);
                        context.startActivity(intent);
                        break;
                    case 4:
                        Intent intent4 = new Intent(context, AppuntiActivity.class);
                        context.startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5 = new Intent(context, ListaUtentiActivity.class);
                        context.startActivity(intent5);
                        break;
                    default:
                }
            }
        });


    }





    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTextView,mIconText;
        ConstraintLayout mExternalConstraintLayout;

        ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.text);
            mIconText = itemView.findViewById(R.id.end);
            mExternalConstraintLayout = itemView.findViewById(R.id.external_constraint_layout);
        }

    }





    // total number of cells
    @Override
    public int getItemCount() {
        if (arrayElem != null){
            return arrayElem.size();
        }else{
            return 0;
        }
    }

}
