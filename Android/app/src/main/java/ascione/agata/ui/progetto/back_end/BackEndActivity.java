package ascione.agata.ui.progetto.back_end;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.health.ProcessHealthStats;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import ascione.agata.R;
import ascione.agata.service.network.CallbackResponseRestService;
import ascione.agata.service.network.NetworkManager;
import ascione.agata.service.network.request.EndpointRequest;
import ascione.agata.service.network.request.IdRequest;
import ascione.agata.service.network.response.EndpointRecordResponse;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.ui.progetto.back_end.crea.CreaBackEndActivity;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.CustomDialogTwoText;
import ascione.agata.utils.Utils;
import ascione.agata.utils.UtilsElem;

public class BackEndActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.back_end_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, BackEndFragment.newInstance())
                    .commitNow();
            setupToolbar();
            setupAdd();
        }
    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(UtilsElem.getColor(this,Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
        setSupportActionBar(toolbar);
        setTitle("Back End");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void setCurrIndex(int index) {
        this.index = index;
    }

    private void setupAdd() {
        ImageView mAddImageView = findViewById(R.id.add);
        final Context context = this;
        mAddImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index == 0) {
                    Intent intent = new Intent(context, CreaBackEndActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    CustomDialogTwoText cdd=new CustomDialogTwoText(context);
                    cdd.show();
                    cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if (ProgettoSingleton.getInstance().getTitle() == null || ProgettoSingleton.getInstance().getTitle().isEmpty()
                                    || ProgettoSingleton.getInstance().getDescrizione() == null || ProgettoSingleton.getInstance().getDescrizione().isEmpty()) {
                                return;
                            }
                            insertEndpoint();
                        }
                    });
                }
            }
        });
    }

    private void insertEndpoint() {
        final Context context = this;
        if (Utils.isConnectedToNetwork(context)) {
            final ProgressDialog progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage(context.getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
            NetworkManager.addEndpoint(new EndpointRequest(ProgettoSingleton.getInstance().getProgetto().getId(),ProgettoSingleton.getInstance().getTitle(),ProgettoSingleton.getInstance().getDescrizione()),new CallbackResponseRestService<EndpointRecordResponse>() {
                @Override
                public void onSuccess(EndpointRecordResponse object) {
                    progressDialog.cancel();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, BackEndFragment.newInstance())
                            .commitNow();
                    //bugs.remove(position);
                    //notifyDataSetChanged();
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

}
