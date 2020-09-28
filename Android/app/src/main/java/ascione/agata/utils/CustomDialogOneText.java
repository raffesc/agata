package ascione.agata.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import ascione.agata.R;
import ascione.agata.service.singleton.ProgettoSingleton;

public class CustomDialogOneText extends Dialog implements
        android.view.View.OnClickListener {

    public Context c;
    public Dialog d;
    public Button aggiungiButton, annullaButton;
    public AppCompatEditText textView;

    public CustomDialogOneText(Context a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_one_text);
        aggiungiButton = findViewById(R.id.aggiungi);
        annullaButton = findViewById(R.id.annulla);
        textView = findViewById(R.id.edit_text);
        aggiungiButton.setOnClickListener(this);
        annullaButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aggiungi:
                if (textView.getText() == null || textView.getText().toString().isEmpty()) {
                    return;
                }
                ProgettoSingleton.getInstance().setDescrizione(textView.getText().toString());
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

