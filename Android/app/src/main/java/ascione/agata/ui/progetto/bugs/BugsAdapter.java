package ascione.agata.ui.progetto.bugs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ascione.agata.R;
import ascione.agata.service.network.CallbackResponseRestService;
import ascione.agata.service.network.NetworkManager;
import ascione.agata.service.network.request.BugRequest;
import ascione.agata.service.network.request.CategoryRequest;
import ascione.agata.service.network.request.IdRequest;
import ascione.agata.service.network.response.BugsCategoryResponse;
import ascione.agata.service.network.response.BugsRecordResponse;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.ui.progetto.bugs.dettaglio.BugsDettaglioActivity;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.CustomDialogOneText;
import ascione.agata.utils.CustomDialogTwoText;
import ascione.agata.utils.Utils;
import ascione.agata.utils.UtilsElem;

public class BugsAdapter extends RecyclerView.Adapter<BugsAdapter.ViewHolder>{


    private LayoutInflater mInflater;
    private Context context;
    private List<List<BugsRecordResponse>> bugs;

    // data is passed into the constructor
    public BugsAdapter(Context context, List<List<BugsRecordResponse>> bugs) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.bugs = bugs;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public BugsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_bugs, parent, false);
        return new BugsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BugsAdapter.ViewHolder holder, final int position) {


        holder.mAggiungiContraintLayout.setBackground(UtilsElem.getBackgroundIconNoTop(context,Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
        holder.mTitleTextView.setTextColor(UtilsElem.getColor(context,Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
        holder.mDeleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBug(position);
            }
        });
        if (position == bugs.size()) {
            holder.mNormalConstraintLayout.setVisibility(View.GONE);
            holder.mAggiungiContraintLayout.setVisibility(View.VISIBLE);
            holder.mAggiungiContraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomDialogTwoText cdd=new CustomDialogTwoText(context);
                    cdd.show();
                    cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if (ProgettoSingleton.getInstance().getTitle() == null || ProgettoSingleton.getInstance().getTitle().isEmpty()
                             || ProgettoSingleton.getInstance().getDescrizione() == null || ProgettoSingleton.getInstance().getDescrizione().isEmpty()) {
                                return;
                            }
                            insertCategory();
                        }
                    });
                }
            });
        } else {
            holder.mAggiungiContraintLayout.setVisibility(View.GONE);
            holder.mNormalConstraintLayout.setVisibility(View.VISIBLE);
            holder.mTitleTextView.setText(bugs.get(position).get(0).getNome_category());
            holder.mNumberTextView.setText("" + bugs.get(position).size());
            holder.mExternalConstraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, BugsDettaglioActivity.class);
                    ProgettoSingleton.getInstance().setBugs(bugs.get(position));
                    context.startActivity(intent);
                }
            });
        }


    }

    private void deleteBug(int position) {
        if (Utils.isConnectedToNetwork(context)) {
            final ProgressDialog progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage(context.getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
            for (int i = 0; i < bugs.get(position).size();i++) {
                NetworkManager.deleteBug(new IdRequest(bugs.get(position).get(i).getId()),new CallbackResponseRestService<Boolean>() {
                    @Override
                    public void onSuccess(Boolean object) {
                        bugs.get(position).remove(0);
                        if  (bugs.get(position) == null || bugs.get(position).size() == 0) {
                            progressDialog.cancel();
                            bugs.remove(position);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(int errorCode, String error) {
                        progressDialog.cancel();
                        /*Utils.showAlertDialog(context, context.getString(R.string.error), error, context.getString(R.string.ok),"", context.getString(R.string.info), new CallbackAlertDialogChoise() {
                            @Override
                            public void onPositiveChoise() {
                            }

                            @Override
                            public void onNegativeChoise() {

                            }
                        });*/
                    }
                });

            }
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




    private void insertCategory() {

        final String titolo = ProgettoSingleton.getInstance().getTitle();
        final String descrizione = ProgettoSingleton.getInstance().getDescrizione();
        ProgettoSingleton.getInstance().setTitle(null);
        ProgettoSingleton.getInstance().setDescrizione(null);

        if (Utils.isConnectedToNetwork(context)) {
            final ProgressDialog progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage(context.getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
            CategoryRequest categoryRequest = new CategoryRequest(ProgettoSingleton.getInstance().getProgetto().getId(),titolo);
            NetworkManager.addBugCategory(categoryRequest,new CallbackResponseRestService<BugsCategoryResponse>() {
                @Override
                public void onSuccess(BugsCategoryResponse object) {
                    Log.d("RESULT","" + object);
                    BugRequest bugRequest = new BugRequest();
                    bugRequest.setTitle(" ");
                    bugRequest.setId_category(object.getId());
                    bugRequest.setId_project(object.getId_project());
                    bugRequest.setDescrizione(descrizione);
                    NetworkManager.addBugInCategory(bugRequest,new CallbackResponseRestService<BugsRecordResponse>() {
                                @Override
                                public void onSuccess(BugsRecordResponse object) {
                                    progressDialog.cancel();
                                    List<BugsRecordResponse> bugCurr = new ArrayList<>();
                                    object.setNome_category(titolo);
                                    bugCurr.add(object);
                                    bugs.add(bugCurr);
                                    notifyDataSetChanged();
                                    Log.d("RESULT", "" + object);
                                }

                                @Override
                                public void onError(int errorCode, String error) {
                                    progressDialog.cancel();
                                    Utils.showAlertDialog(context, context.getString(R.string.error), error, context.getString(R.string.ok), "", context.getString(R.string.info), new CallbackAlertDialogChoise() {
                                        @Override
                                        public void onPositiveChoise() {

                                        }

                                        @Override
                                        public void onNegativeChoise() {

                                        }
                                    });
                                }

                            });
                }

                @Override
                public void onError(int errorCode, String error) {
                    progressDialog.cancel();
                    ProgettoSingleton.getInstance().setDescrizione(null);
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
        TextView mTitleTextView, mNumberTextView;
        ConstraintLayout mNormalConstraintLayout, mAggiungiContraintLayout;
        ConstraintLayout mExternalConstraintLayout;
        ImageView mDeleteImageView;

        ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.text);
            mNumberTextView = itemView.findViewById(R.id.number);
            mExternalConstraintLayout = itemView.findViewById(R.id.external_constraint_layout);
            mNormalConstraintLayout = itemView.findViewById(R.id.normal_card);
            mAggiungiContraintLayout = itemView.findViewById(R.id.add_card);
            mDeleteImageView = itemView.findViewById(R.id.delete);
        }

    }





    // total number of cells
    @Override
    public int getItemCount() {
        if (bugs != null){
            return bugs.size() + 1;
        }else{
            return 0;
        }
    }

}
