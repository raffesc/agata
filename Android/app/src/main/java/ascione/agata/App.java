package ascione.agata;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import com.androidnetworking.AndroidNetworking;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import ascione.agata.service.network.NetworkManager;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import static android.content.ContentValues.TAG;

public class App extends Application {

    private static Context mContext;

    public App() {}

    @Override
    public void onCreate() {
        // Il codice inserito qui viene eseguito solo una volta, all'avvio dell'app.
        super.onCreate();
        mContext = this;

        Log.d("SETUP", "APP INITIAL SETUP");



        // Init di Android Networking

        CookieJar cookieJar = new CookieJar() {

            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                // here you get the cookies from Response
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        };

    /*  OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                 .cookieJar(cookieJar)
                .connectTimeout(NetworkManager.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(NetworkManager.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(NetworkManager.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .build(); */

        OkHttpClient okHttpClient = getUnsafeOkHttpClient();



        AndroidNetworking.initialize(mContext, okHttpClient);









    }



    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }



    private boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED;
    }

    // Checks if a volume containing external storage is available to at least read.
    private boolean isExternalStorageReadable() {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ||
                Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED_READ_ONLY;
    }







    public static Context getContext() {
        return mContext;
    }



    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            CookieJar cookieJar = new CookieJar() {

                private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    // here you get the cookies from Response
                    cookieStore.put(url.host(), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            };

            OkHttpClient okHttpClient = builder.
                    cookieJar(cookieJar).
                    connectTimeout(NetworkManager.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                    .readTimeout(NetworkManager.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                    .writeTimeout(NetworkManager.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS).build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
