package ascione.agata.ui.progetto.back_end;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ascione.agata.R;
import ascione.agata.service.network.CallbackResponseRestService;
import ascione.agata.service.network.NetworkManager;
import ascione.agata.service.network.request.IdRequest;
import ascione.agata.service.network.response.BackEndResponse;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.ui.progetto.design.DesignImmagineAdapter;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.ErrorAlertDialogs;
import ascione.agata.utils.Utils;
import ascione.agata.utils.UtilsElem;

public class BackEndAPIAdapter extends RecyclerView.Adapter<BackEndAPIAdapter.ViewHolder> {


    private LayoutInflater mInflater;
    private Context context;
    private BackEndResponse backEndResponse;
    String status;

    // data is passed into the constructor
    public BackEndAPIAdapter(Context context, BackEndResponse backEndResponse) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.backEndResponse = backEndResponse;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public BackEndAPIAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_back_end_api, parent, false);
        return new BackEndAPIAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BackEndAPIAdapter.ViewHolder holder, final int position) {
        holder.mTitleTextView.setText(backEndResponse.getRecords().get(position).getNome());
        holder.mDeleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBackEnd(position);
            }
        });
        switch (backEndResponse.getRecords().get(position).getStatus()) {
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

        holder.mQueryTitleTextView.setTextColor(UtilsElem.getColor(context,Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
        holder.mHeaderTextView.setTextColor(UtilsElem.getColor(context,Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
        holder.mBodyTextView.setTextColor(UtilsElem.getColor(context,Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));

        setupRecyclerViewQuery(holder,position);
        setupRecyclerViewHeader(holder,position);
        setupRecyclerViewBody(holder,position);

        holder.mStatusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isConnectedToNetwork(context)) {
                    final ProgressDialog progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
                    progressDialog.setMessage(context.getString(R.string.loading));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    status = "";
                    switch (backEndResponse.getRecords().get(position).getStatus()) {
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
                    NetworkManager.updateStatusBackEnd(backEndResponse.getRecords().get(position).getId(),status, new CallbackResponseRestService<Boolean>() {
                        @Override
                        public void onSuccess(Boolean object) {
                            progressDialog.cancel();
                            backEndResponse.getRecords().get(position).setStatus(status);
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

    private void deleteBackEnd(int position) {
        if (Utils.isConnectedToNetwork(context)) {
            final ProgressDialog progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage(context.getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
            NetworkManager.deleteBackEnd(new IdRequest(backEndResponse.getRecords().get(position).getId()),new CallbackResponseRestService<Boolean>() {
                @Override
                public void onSuccess(Boolean object) {
                    progressDialog.cancel();
                    backEndResponse.getRecords().remove(position);
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


    private void setupRecyclerViewQuery(BackEndAPIAdapter.ViewHolder holder, int position) {
        if (backEndResponse.getRecords().get(position).getQuery()  == null || backEndResponse.getRecords().get(position).getQuery().size() == 0) {
            holder.mRecyclerViewQuery.setVisibility(View.GONE);
            holder.mQueryTitleTextView.setVisibility(View.GONE);
        } else {
            holder.mRecyclerViewQuery.setVisibility(View.VISIBLE);
            holder.mQueryTitleTextView.setVisibility(View.VISIBLE);
        }
        holder.mRecyclerViewQuery.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        SimpleAttributeBackEndAdapter adapter = new SimpleAttributeBackEndAdapter(context, backEndResponse.getRecords().get(position).getQuery());
        holder.mRecyclerViewQuery.setAdapter(adapter);

    }

    private void setupRecyclerViewHeader(BackEndAPIAdapter.ViewHolder holder, int position) {
        if (backEndResponse.getRecords().get(position).getHeader()  == null || backEndResponse.getRecords().get(position).getHeader().size() == 0) {
            holder.mRecyclerViewHeader.setVisibility(View.GONE);
            holder.mHeaderTextView.setVisibility(View.GONE);
        } else {
            holder.mRecyclerViewHeader.setVisibility(View.VISIBLE);
            holder.mHeaderTextView.setVisibility(View.VISIBLE);
        }
        holder.mRecyclerViewHeader.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        ComplAttributeBackEndAdapter adapter = new ComplAttributeBackEndAdapter(context, backEndResponse.getRecords().get(position).getHeader());
        holder.mRecyclerViewHeader.setAdapter(adapter);

    }

    private void setupRecyclerViewBody(BackEndAPIAdapter.ViewHolder holder, int position) {
        if (backEndResponse.getRecords().get(position).getBody()  == null || backEndResponse.getRecords().get(position).getBody().size() == 0) {
            holder.mRecyclerViewBody.setVisibility(View.GONE);
            holder.mBodyTextView.setVisibility(View.GONE);
        } else {
            holder.mRecyclerViewBody.setVisibility(View.VISIBLE);
            holder.mBodyTextView.setVisibility(View.VISIBLE);
        }
        holder.mRecyclerViewBody.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        SimpleAttributeBackEndAdapter adapter = new SimpleAttributeBackEndAdapter(context, backEndResponse.getRecords().get(position).getBody());
        holder.mRecyclerViewBody.setAdapter(adapter);
    }






    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTextView, mStatusTextView,mQueryTitleTextView, mHeaderTextView, mBodyTextView;
        View line;
        RecyclerView mRecyclerViewQuery, mRecyclerViewHeader, mRecyclerViewBody;
        ImageView mDeleteImageView;


        ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.title_pattern);
            mStatusTextView = itemView.findViewById(R.id.text_status);
            mQueryTitleTextView = itemView.findViewById(R.id.query_title);
            mHeaderTextView = itemView.findViewById(R.id.header_title);
            mBodyTextView = itemView.findViewById(R.id.body_title);
            mRecyclerViewQuery = itemView.findViewById(R.id.recycler_view_query);
            mRecyclerViewHeader = itemView.findViewById(R.id.recycler_view_header);
            mRecyclerViewBody = itemView.findViewById(R.id.recycler_view_body);
            mDeleteImageView = itemView.findViewById(R.id.delete);
        }

    }





    // total number of cells
    @Override
    public int getItemCount() {
        if (backEndResponse != null && backEndResponse.getRecords() != null){
            return backEndResponse.getRecords().size();
        }else{
            return 0;
        }
    }

}


