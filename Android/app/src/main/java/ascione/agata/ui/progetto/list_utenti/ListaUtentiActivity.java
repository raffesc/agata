package ascione.agata.ui.progetto.list_utenti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import ascione.agata.R;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.utils.UtilsElem;

public class ListaUtentiActivity extends AppCompatActivity {

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_utenti_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ListaUtentiFragment.newInstance())
                    .commitNow();
            setupToolbar();
        }
    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(UtilsElem.getColor(this,Integer.valueOf(ProgettoSingleton.getInstance().getProgetto().getMain_color())));
        setSupportActionBar(toolbar);
        setTitle("Lista Utenti");
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

}
