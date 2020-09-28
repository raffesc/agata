package ascione.agata.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ascione.agata.R;
import ascione.agata.ui.esplora.EsploraFragment;
import ascione.agata.ui.home.HomeFragment;
import ascione.agata.ui.profilo.ProfiloFragment;
import ascione.agata.ui.progetto.create.CreaProgettoActivity;
import ascione.agata.ui.progetto.create.CreaProgettoFragment;
import ascione.agata.utils.CustomDialogOneText;
import ascione.agata.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private ImageView mPlusImageView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        Utils.setStatusBarDefaultColor(this);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        mPlusImageView = findViewById(R.id.plus_button);
        mPlusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CreaProgettoActivity.class);
                startActivity(intent);
                ((MainActivity)context).finish();
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_item_home);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    if (item.getItemId() == R.id.bottom_nav_item_home) {
                        selectedFragment = new HomeFragment();
                        mPlusImageView.setVisibility(View.VISIBLE);
                        setTitle("Home");
                        // Se clicco sullo stesso bottone non deve ricaricare la view.
                    } else if (item.getItemId() != bottomNavigationView.getSelectedItemId()) {
                        switch (item.getItemId()) {
                            case R.id.bottom_nav_item_home:
                                selectedFragment = new HomeFragment();
                                setTitle("Home");
                                mPlusImageView.setVisibility(View.VISIBLE);
                                bottomNavigationView.getMenu().getItem(0).setIcon(ContextCompat.getDrawable(context, R.drawable.ic_home));
                                bottomNavigationView.getMenu().getItem(1).setIcon(ContextCompat.getDrawable(context, R.drawable.ic_esplora_deactive));
                                bottomNavigationView.getMenu().getItem(2).setIcon(ContextCompat.getDrawable(context, R.drawable.ic_profilo_deactive));
                                break;
                            case R.id.bottom_nav_item_esplora:
                                selectedFragment = new EsploraFragment();
                                mPlusImageView.setVisibility(View.GONE);
                                bottomNavigationView.getMenu().getItem(0).setIcon(ContextCompat.getDrawable(context, R.drawable.ic_home_deactive));
                                bottomNavigationView.getMenu().getItem(1).setIcon(ContextCompat.getDrawable(context, R.drawable.ic_esplora));
                                bottomNavigationView.getMenu().getItem(2).setIcon(ContextCompat.getDrawable(context, R.drawable.ic_profilo_deactive));
                                setTitle("Esplora");
                                break;
                            case R.id.bottom_nav_item_profilo:
                                selectedFragment = new ProfiloFragment();
                                mPlusImageView.setVisibility(View.GONE);
                                bottomNavigationView.getMenu().getItem(0).setIcon(ContextCompat.getDrawable(context, R.drawable.ic_home_deactive));
                                bottomNavigationView.getMenu().getItem(1).setIcon(ContextCompat.getDrawable(context, R.drawable.ic_esplora_deactive));
                                bottomNavigationView.getMenu().getItem(2).setIcon(ContextCompat.getDrawable(context, R.drawable.ic_user));
                                setTitle("Profilo");
                                break;
                        }
                    }
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment).commit();
                    }
                    return true;
                }

            };





    @Override
    public void onBackPressed() {
        if (bottomNavigationView.getSelectedItemId() != R.id.bottom_nav_item_home ) {
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_item_home);
        } else {
            finishAffinity();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_item_home);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override public void onPause() {
        super.onPause();
    }



}
