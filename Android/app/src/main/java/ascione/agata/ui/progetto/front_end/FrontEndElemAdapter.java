package ascione.agata.ui.progetto.front_end;

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
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ascione.agata.R;
import ascione.agata.service.network.CallbackResponseRestService;
import ascione.agata.service.network.NetworkManager;
import ascione.agata.service.network.response.FrontEndAttributeResponse;
import ascione.agata.service.network.response.FrontEndRecordResponse;
import ascione.agata.service.network.response.FrontEndResponse;
import ascione.agata.service.network.response.ProgettiResponse;
import ascione.agata.service.singleton.UserSingleton;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.ErrorAlertDialogs;
import ascione.agata.utils.Utils;

public class FrontEndElemAdapter extends RecyclerView.Adapter<FrontEndElemAdapter.ViewHolder>{


    private LayoutInflater mInflater;
    private Context context;
    private List<FrontEndRecordResponse> frontEndElems;
    String status = "";

    // data is passed into the constructor
    public FrontEndElemAdapter(Context context, List<FrontEndRecordResponse> frontEndElems) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.frontEndElems = frontEndElems;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public FrontEndElemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_front_end_elem, parent, false);
        return new FrontEndElemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FrontEndElemAdapter.ViewHolder holder, final int position) {

        holder.mTitleTextView.setText(frontEndElems.get(position).getNome());
        switch (frontEndElems.get(position).getStatus()) {
            case "1":
                holder.mStatusTextView.setText("TO DO");
                holder.mStatusTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
                holder.mStatusTextView.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_card_red));
                break;
            case "2":
                holder.mStatusTextView.setText("DOING");
                holder.mStatusTextView.setTextColor(ContextCompat.getColor(context, R.color.black));
                holder.mStatusTextView.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_card_yellow));
                break;
            case "3":
                holder.mStatusTextView.setText("DONE");
                holder.mStatusTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
                holder.mStatusTextView.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_card_green));
                break;
        }

        holder.mAttributesTextView.setText(getAttributes(position));

        holder.mDeleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isConnectedToNetwork(context)) {
                    final ProgressDialog progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
                    progressDialog.setMessage(context.getString(R.string.loading));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    NetworkManager.deleteFrontEnd(frontEndElems.get(position).getId(),new CallbackResponseRestService<Boolean>() {
                        @Override
                        public void onSuccess(Boolean object) {
                            progressDialog.cancel();
                            frontEndElems.remove(position);
                            notifyDataSetChanged();
                            Log.d("RESULT","" + object);
                        }

                        @Override
                        public void onError(int errorCode, String error) {
                            progressDialog.cancel();
                            ErrorAlertDialogs.genericError(context);
                            Log.d("ERROR","SOMETHING WENT WRONG");
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
        });


        holder.mStatusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isConnectedToNetwork(context)) {
                    final ProgressDialog progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
                    progressDialog.setMessage(context.getString(R.string.loading));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    status = "";
                    switch (frontEndElems.get(position).getStatus()) {
                        case "1":
                            status = "2";
                            break;
                        case "2":
                            status = "3";
                            break;
                        case "3":
                            status = "1";
                            break;
                    }
                    NetworkManager.updateStatusFrontEnd(frontEndElems.get(position).getId(),status, new CallbackResponseRestService<Boolean>() {
                        @Override
                        public void onSuccess(Boolean object) {
                            progressDialog.cancel();
                            frontEndElems.get(position).setStatus(status);
                            notifyDataSetChanged();
                            Log.d("RESULT","" + object);
                        }

                        @Override
                        public void onError(int errorCode, String error) {
                            progressDialog.cancel();
                            ErrorAlertDialogs.genericError(context);
                            Log.d("ERROR","SOMETHING WENT WRONG");
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
        });
    }

    private String getAttributes(int position) {
        String attributesString = "";
        for (FrontEndAttributeResponse attribute : frontEndElems.get(position).getAttributes()) {
            if (attribute.getPriv().equalsIgnoreCase("1")) {
               attributesString += "- ";
            } else {
                attributesString += "+ ";
            }
            attributesString += attribute.getValue() + ": " + attribute.getType();
            attributesString += "\n";
        }
        return attributesString;

    }







    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTextView, mStatusTextView,mAttributesTextView;
        ImageView mDeleteImageView;

        ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.title_pattern);
            mStatusTextView = itemView.findViewById(R.id.text_status);
            mDeleteImageView = itemView.findViewById(R.id.delete);
            mAttributesTextView = itemView.findViewById(R.id.attributes);
        }

    }





    // total number of cells
    @Override
    public int getItemCount() {
        if (frontEndElems != null){
            return frontEndElems.size();
        }else{
            return 0;
        }
    }

}
