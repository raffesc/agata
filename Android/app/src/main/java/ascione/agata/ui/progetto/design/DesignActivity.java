package ascione.agata.ui.progetto.design;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ascione.agata.R;
import ascione.agata.service.network.CallbackResponseRestService;
import ascione.agata.service.network.NetworkManager;
import ascione.agata.service.network.request.BugRequest;
import ascione.agata.service.network.request.CategoryRequest;
import ascione.agata.service.network.response.BugsCategoryResponse;
import ascione.agata.service.network.response.BugsRecordResponse;
import ascione.agata.service.network.response.DesignImageResponse;
import ascione.agata.service.network.response.ProgettiResponse;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.service.singleton.UserSingleton;
import ascione.agata.utils.AttachFileBottomSheetDialog;
import ascione.agata.utils.CallbackAlertDialogChoise;
import ascione.agata.utils.Constants;
import ascione.agata.utils.CustomDialogOneText;
import ascione.agata.utils.CustomDialogTwoText;
import ascione.agata.utils.ErrorAlertDialogs;
import ascione.agata.utils.Utils;
import ascione.agata.utils.UtilsElem;

public class DesignActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String idCategory;
    private Uri imageUri;
    private Uri imageToCropUri;
    private AttachFileBottomSheetDialog attachFileBottomSheet = new AttachFileBottomSheetDialog();
    private ConstraintLayout mContainer;

    public static final int CAMERA = 0;
    public static final int GALLERY = 1;
    private static final int TAKE_PHOTO_FROM_CAMERA = 0;
    private static final int TAKE_PHOTO_FROM_GALLERY = 1;
    private static final int PERMISSION_CAMERA = 0;
    public static final int PERMISSIONS_READ_AND_WRITE_EXTERNAL_STORAGE = 1;
    boolean fromCamera = false;
    private int mediaSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.design_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DesignFragment.newInstance())
                    .commitNow();
            setupToolbar();
            mContainer = findViewById(R.id.drawer_layout);
        }
    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(UtilsElem.getColor(this,Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
        setSupportActionBar(toolbar);
        setTitle("Design");
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

    public void aggiungiCategory() {
        CustomDialogOneText cdd=new CustomDialogOneText(this);
        cdd.show();
        cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (ProgettoSingleton.getInstance().getDescrizione() == null || ProgettoSingleton.getInstance().getDescrizione().isEmpty()) {
                    return;
                }
                insertCategory();
            }
        });
    }

    private void insertCategory() {

        final String titolo = ProgettoSingleton.getInstance().getDescrizione();
        ProgettoSingleton.getInstance().setTitle(null);
        final Context context = this;
        if (Utils.isConnectedToNetwork(context)) {
            final ProgressDialog progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
            CategoryRequest categoryRequest = new CategoryRequest(ProgettoSingleton.getInstance().getProgetto().getId(),titolo);
            NetworkManager.addDesignCategory(categoryRequest,new CallbackResponseRestService<BugsCategoryResponse>() {
                @Override
                public void onSuccess(BugsCategoryResponse object) {
                    Log.d("RESULT","" + object);
                    progressDialog.cancel();
                    setCategory(object.getId());
                    showDocumentUploadAlert();
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

    public void setCategory(String idCategory ) {
        this.idCategory = idCategory;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            File file = handleImage(requestCode,data);
            if (file != null) {
                uploadImage(file);
            }
        }





    }

    private File handleImage(int requestCode,Intent data) {
        String imageName = "";
        String imageType = "";
        String imageToCrop = "";
        switch (requestCode) {
            // Dopo aver scattato una foto chiama la libreria per il crop.

            case TAKE_PHOTO_FROM_CAMERA:
                fromCamera = true;

                // Crea una copia dell'immagine.
                imageToCrop = Utils.saveFileFromUri(this, imageUri, imageName);
                imageToCropUri = Uri.fromFile(new File(imageToCrop));
                File file = new File(imageToCropUri.getPath());
                return file;
            // Dopo aver preso una foto dalla galleria chiama la libreria per il crop.
            case TAKE_PHOTO_FROM_GALLERY:
                fromCamera = false;
                if (data != null && data.getData() != null) {
                    imageUri = data.getData();
                    return new File(imageUri.getPath());
                }
                break;
        }
        return null;
    }

    // Mostra all'utente l'alert di scelta Galleria/Fotocamera per l'upload della foto.
    public void showDocumentUploadAlert() {
        attachFileBottomSheet.setStyle(DialogFragment.STYLE_NO_FRAME, 0);
        attachFileBottomSheet.show(((AppCompatActivity) this).getSupportFragmentManager(), "attachmentBottomSheet");
        mContainer.setAlpha(0.5F);
    }

    // Setta il flag di apertura fotocamera
    public void openCamera(){
        mediaSource = CAMERA;
        checkRequiredPermission();
    }

    // Resetta l'apha della view quando viene chiuso il menu di scelta Fotocamera/Galleria
    public void resetAlpha() {
        mContainer.setAlpha(1.0F);
    }


    // Setta il flag di apertura galleria
    public void openGallery(){
        mediaSource = GALLERY;
        checkRequiredPermission();
    }


    // Controlla se l'utente ha negato/consentito i permessi per lo storage e per la camera.
    public boolean checkRequiredPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // L'utente deve negare o consentire i permessi.
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                        },
                        PERMISSIONS_READ_AND_WRITE_EXTERNAL_STORAGE);
                return false;
            } else {
                // I permessi sono già stati concessi.
                checkCameraPermission();
                return true;
            }
        } else {
            // Non c'è bisogno di controllare i permessi per versioni < di M.
            openSelectedSource();
            return true;
        }
    }

    // Controlla se l'utente ha negato/consentito i permessi per la fotocamera
    public boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // L'utente deve negare o consentire i permessi.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
            return false;
        } else {
            // I permessi sono già stati concessi.
            openSelectedSource();
            return true;
        }
    }

    @Override
    // Callback che viene chiamata al click dell'utente su nega/consenti permessi.
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        final Context context = this;
        switch (requestCode) {

            case PERMISSIONS_READ_AND_WRITE_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permessi lettura/scrittura concessi
                    checkCameraPermission();
                } else {
                    // Permessi lettura/scrittura negati
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Utils.showAlertDialog(this,
                                getResources().getString(R.string.missing_permission),
                                getResources().getString(R.string.permission_denied),
                                getResources().getString(R.string.go_to_settings),
                                getResources().getString(R.string.cancel), Constants.error, new CallbackAlertDialogChoise() {
                                    @Override
                                    public void onPositiveChoise() {
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                                        intent.setData(uri);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onNegativeChoise() {

                                    }
                                });
                    }
                }
                break;

            case PERMISSION_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permessi fotocamera concessi
                    if (Build.VERSION.SDK_INT != Build.VERSION_CODES.M) {
                        openSelectedSource();
                    }
                } else {
                    // Permessi fotocamera negati
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                        Utils.showAlertDialog(context,
                                getResources().getString(R.string.missing_permission),
                                getResources().getString(R.string.permission_denied),
                                getResources().getString(R.string.go_to_settings),
                                getResources().getString(R.string.cancel), Constants.error, new CallbackAlertDialogChoise() {
                                    @Override
                                    public void onPositiveChoise() {
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                                        intent.setData(uri);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onNegativeChoise() {

                                    }
                                });
                    }
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // A seconda della scelta dell'utente viene aperta la sorgente allegato corretta
    private void openSelectedSource() {

        ContentValues values = new ContentValues();

        switch (mediaSource){

            case CAMERA:
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO_FROM_CAMERA);
                //mContainer.setAlpha(1.0F);
                break;

            case GALLERY:

                //imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickPhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(pickPhoto, TAKE_PHOTO_FROM_GALLERY);
                //mContainer.setAlpha(1.0F);
                break;

        }
    }

    // Conversione Bitmap a Base64
    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.NO_WRAP);
        return encImage;
    }


    private  void uploadImage(File file) {
        final Context context = this;
        if (Utils.isConnectedToNetwork(context)) {
            final ProgressDialog progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();
            NetworkManager.uploadImageDesign(ProgettoSingleton.getInstance().getProgetto().getId(),
                    idCategory,
                    file,new CallbackResponseRestService<DesignImageResponse>() {
                @Override
                public void onSuccess(DesignImageResponse object) {
                    progressDialog.cancel();
                    ((DesignActivity) context).onBackPressed();
                }

                @Override
                public void onError(int errorCode, String error) {
                    progressDialog.cancel();
                    ErrorAlertDialogs.genericError(context);
                    Log.d("ERROR","SOMETHING WENT WRONG");
                }
            });

        } else {
            Utils.showAlertDialog(context, getString(R.string.no_connection_alert_title), getString(R.string.no_connection_alert_subtitle), getString(R.string.ok),"", getString(R.string.info), new CallbackAlertDialogChoise() {
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
