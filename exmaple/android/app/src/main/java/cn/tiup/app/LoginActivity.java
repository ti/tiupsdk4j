package cn.tiup.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tiup.sdk.oauth.Config;
import cn.tiup.sdk.oauth.Token;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    private UserLoginTask mAuthTask;
    private static final String EXTRA_ON_LOGIN = "extra_on_login";
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private Config mOauthConfig;
    private Intent onLoginIntent;

    public static Intent makeIntent(Context context, Intent onLogin) {
        Intent intent = new Intent(context, LoginActivity.class);
        if(onLogin != null) {
            intent.putExtra(LoginActivity.EXTRA_ON_LOGIN, onLogin);
        }
        return intent;
    }

    public static void start(Activity fromActivity,Intent onLogin) {
        fromActivity.startActivity(makeIntent(fromActivity, null));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.content_login);
        initViews();
    }

    private void initViews() {
        onLoginIntent = getIntent()
                .getParcelableExtra(EXTRA_ON_LOGIN);
        mWebView = (WebView) findViewById(R.id.webView);
        mProgressBar = (ProgressBar) this.findViewById(R.id.progress);
        mOauthConfig = TiupClient.getInstance().getOauthConfig();
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        mWebView.setVisibility(View.VISIBLE);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!Uri.parse(url).getScheme().contains("http")) {
                    Uri uri = Uri.parse(url);
                    String authCode = uri.getQueryParameter("code");
                    String error = uri.getQueryParameter("error");
                    if (authCode != null ) {
                        mAuthTask = new UserLoginTask();
                        mAuthTask.execute(authCode);

                    } else if (error != null) {
                        Toast.makeText(getApplicationContext(),"ERROR: " + error, Toast.LENGTH_SHORT).show();
                    }
                    mWebView.setVisibility(View.GONE);
                    Log.e("AAAA", url);
                }

                super.onPageStarted(view, url, favicon);
            }

        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setProgress(newProgress);
            }
        });

        CookieManager.getInstance().removeAllCookies(new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(Boolean value) {
                Map<String, String> params = new HashMap<>();
                params.put("access_type","offline");
                String url = TiupClient.getInstance().getOauthConfig().getAuthCodeUrl("oauth-android-login", params);
                mWebView.loadUrl(url);
            }
        });

    }

    /**
     * 异步获取token
     */
    public class UserLoginTask extends AsyncTask<String, String, Boolean> {

        Token token;

        @Override
        protected void onProgressUpdate(String... values) {
            //显示错误
            Toast.makeText(getApplicationContext(),  values[0], Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                token = mOauthConfig.exchangeToken(params[0]);
            } catch (IOException e) {
                publishProgress("通讯错误 - " + e.getMessage());
                e.printStackTrace();
                return false;
            }
            //保存token
            PrefUtils.saveToken(LoginActivity.this, token);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {
                //跳转到新页面
                finish();
                if (onLoginIntent == null) {
                    onLoginIntent = MainActivity.makeIntent(LoginActivity.this);
                }
                startActivity(onLoginIntent);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}

