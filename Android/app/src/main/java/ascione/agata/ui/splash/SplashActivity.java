package ascione.agata.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import ascione.agata.R;
import ascione.agata.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    public static final int SPLASH_SCREEN_DELAY_MILLISECONDS = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SplashFragment.newInstance())
                    .commitNow();

            goToNextActivity();
        }
    }

    private void goToNextActivity () {

        new Handler().postDelayed( () -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_SCREEN_DELAY_MILLISECONDS);

    }
}
