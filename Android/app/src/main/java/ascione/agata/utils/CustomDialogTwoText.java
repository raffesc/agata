package ascione.agata.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatEditText;

import ascione.agata.R;
import ascione.agata.service.singleton.ProgettoSingleton;

public class CustomDialogTwoText extends Dialog implements
        android.view.View.OnClickListener {

    public Context c;
    public Dialog d;
    public Button aggiungiButton, annullaButton;
    public AppCompatEditText titleTextView,descrTextView;

    public CustomDialogTwoText(Context a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_two_text);
        aggiungiButton = findViewById(R.id.aggiungi);
        annullaButton = findViewById(R.id.annulla);
        descrTextView = findViewById(R.id.edit_text);
        titleTextView = findViewById(R.id.title_edit_text);
        aggiungiButton.setOnClickListener(this);
        annullaButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aggiungi:
                if (descrTextView.getText() == null || descrTextView.getText().toString().isEmpty()) {
                    return;
                }
                if (titleTextView.getText() == null || titleTextView.getText().toString().isEmpty()) {
                    return;
                }
                ProgettoSingleton.getInstance().setDescrizione(descrTextView.getText().toString());
                ProgettoSingleton.getInstance().setTitle(titleTextView.getText().toString());
                Log.d("ADD","Ha cliccato il pulsante aggiungi");
                break;
            case R.id.annulla:
                Log.d("ANN","Ha cliccato il pulsante annulla");
                break;
            default:
                break;
        }
        dismiss();
    }
}

