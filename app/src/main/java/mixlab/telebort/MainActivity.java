package mixlab.telebort;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import mixlab.telebort.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TeleBort";
    private WebView webView;
    private static final String URL = "https://telebort.ru"; //"http://m.mixlabel.ru"

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean res = requestMultiplePermissions();
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        if (Build.VERSION.SDK_INT < 23) {
            webView.setWebChromeClient(new WebChromeClient() {
            });
        }
        if(res)webView.loadUrl(URL);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
    private String[] reqPermissions = new String[] {
            Manifest.permission.INTERNET
            // Manifest.permission.ACCESS_NETWORK_STATE
    };
    final int PERMISSION_REQUEST_CODE = 1;
    public boolean requestMultiplePermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
                Log.d(TAG, "Show an explanation");
                Toast.makeText(this,
                        "The Internet permission",
                        Toast.LENGTH_SHORT).show();
            }

            // No explanation needed; request the permission
            Log.d(TAG, "Request permission");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    PERMISSION_REQUEST_CODE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            return false;


        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.d(TAG, "get result of permissions requests");
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length == reqPermissions.length) {
            Log.d(TAG, "permission code is: " + grantResults[0]+ " waited: " + PackageManager.PERMISSION_GRANTED);
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                webView.loadUrl(URL);
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
