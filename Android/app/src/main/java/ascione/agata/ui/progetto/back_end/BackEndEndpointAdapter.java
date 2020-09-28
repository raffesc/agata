package ascione.agata.ui.progetto.back_end;

import android.app.ProgressDialog;
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

import ascione.agata.R;
import ascione.agata.service.network.CallbackResponseRestService;
import ascione.agata.service.network.NetworkManager;
import ascione.agata.service.network.request.IdRequest;
import ascione.agata.service.network.response.EndpointResponse;
import ascione.agata.service.network.response.FrontEndResponse;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.ui.progetto.front_end.FrontEndElemAdapter;
import ascione.agata.ui.progetto.front_end.crea.CreaFrontEndActivity;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.Utils;
import ascione.agata.utils.UtilsElem;

public class BackEndEndpointAdapter  extends RecyclerView.Adapter<BackEndEndpointAdapter.ViewHolder>{


    private LayoutInflater mInflater;
    private Context context;
    private EndpointResponse endpointResponse;

    // data is passed into the constructor
    public BackEndEndpointAdapter(Context context, EndpointResponse endpointResponse) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.endpointResponse = endpointResponse;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public BackEndEndpointAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_back_end_endpoint, parent, false);
        return new BackEndEndpointAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BackEndEndpointAdapter.ViewHolder holder, final int position) {
        holder.mDeleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEndpoint(position);
            }
        });
        holder.mTitleTextView.setText(endpointResponse.getRecords().get(position).getTitle());
        holder.mTitleTextView.setTextColor(UtilsElem.getColor(context, Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
        holder.mSubtitleTextView.setText(endpointResponse.getRecords().get(position).getDescrizione());
        holder.mSubtitleTextView.setTextColor(UtilsElem.getColor(context, Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
        holder.line.setBackgroundColor(UtilsElem.getColor(context, Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
    }



    private void deleteEndpoint(int position) {

        if (Utils.isConnectedToNetwork(context)) {
            final ProgressDialog progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage(context.getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
            NetworkManager.deleteEndpoint(new IdRequest(endpointResponse.getRecords().get(position).getId()),new CallbackResponseRestService<Boolean>() {
                @Override
                public void onSuccess(Boolean object) {
                    progressDialog.cancel();
                    endpointResponse.getRecords().remove(position);
                    notifyDataSetChanged();
                }

                @Override
                public void onError(int errorCode, String error) {
                    progressDialog.cancel();
                    Utils.showAlertDialog(context, context.getString(R.string.error), error, context.getString(R.string.ok),"", context.getString(R.string.info), new CallbackAlertDialogChoise() {
                        @Override
                        public void onPositiveChoise() {
                        }

                        @Override
                        public void onNegativeChoise() {

                        }
                    });
                }
            });

        } else {
            Utils.showAlertDialog(context, context.getString(R.string.no_connection_alert_title), context.getString(R.string.no_connection_alert_subtitle), context.getString(R.string.ok),"", context.getString(R.string.info), new CallbackAlertDialogChoise() {
                @Override
                public void onPositiveChoise() {

                }

                @Override
                public void onNegativeChoise() {

                }
            });
        }

    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTextView;
        TextView mSubtitleTextView;
        View line;
        ImageView mDeleteImageView;

        ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.title);
            mSubtitleTextView = itemView.findViewById(R.id.subtitle);
            line = itemView.findViewById(R.id.line);
            mDeleteImageView = itemView.findViewById(R.id.delete);
        }

    }





    // total number of cells
    @Override
    public int getItemCount() {
        if (endpointResponse != null && endpointResponse.getRecords() != null){
            return endpointResponse.getRecords().size();
        }else{
            return 0;
        }
    }

}

