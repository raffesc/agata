package ascione.agata.ui.progetto.design;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import ascione.agata.service.network.response.DesignRecordResponse;
import ascione.agata.service.singleton.ImageSingleton;
import ascione.agata.ui.image_bigger.ImageActivity;

public class DesignImmagineAdapter extends RecyclerView.Adapter<DesignImmagineAdapter.ViewHolder>{


    private LayoutInflater mInflater;
    private Context context;
    private List<DesignRecordResponse> design;

    // data is passed into the constructor
    public DesignImmagineAdapter(Context context, List<DesignRecordResponse> design) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.design = design;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public DesignImmagineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_immagine_design, parent, false);
        return new DesignImmagineAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DesignImmagineAdapter.ViewHolder holder, final int position) {

        downloadImage(position,holder);

    }


    private void downloadImage(int position, DesignImmagineAdapter.ViewHolder holder) {

        NetworkManager.downloadImage(design.get(position).getNome(), new CallbackResponseRestService<Bitmap>() {
            @Override
            public void onSuccess(Bitmap object) {
                holder.mImageView.setImageBitmap(object);
                holder.mImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImageSingleton.getInstance().setImage(object);
                        Intent intent = new Intent(context, ImageActivity.class);
                        context.startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(int errorCode, String error) {
                Log.d("ERR","Errore durante il downlaod");
            }
        });

    }







    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image);
        }

    }





    // total number of cells
    @Override
    public int getItemCount() {
        if (design != null){
            return design.size();
        }else{
            return 0;
        }
    }

}