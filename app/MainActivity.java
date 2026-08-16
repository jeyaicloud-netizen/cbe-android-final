package com.example.cbeapp;

import android.app.role.RoleManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);
        setContentView(webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        // JS Bridge ማገናኘት
        webView.addJavascriptInterface(new SmsHelper(this), "AndroidSMS");

        // Vercel Link ማስገባት
        webView.loadUrl("https://cbe-birr-pluss-54321.vercel.app");

        // Default SMS App መሆን አለመሆኑን ማረጋገጥ
        requestDefaultSmsRole();
    }

    private void requestDefaultSmsRole() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            RoleManager roleManager = getSystemService(RoleManager.class);
            if (roleManager != null && !roleManager.isRoleHeld(RoleManager.ROLE_SMS)) {
                Intent intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS);
                startActivityForResult(intent, 101);
            }
        } else {
            if (!getPackageName().equals(Telephony.Sms.getDefaultSmsPackage(this))) {
                Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, getPackageName());
                startActivity(intent);
            }
        }
    }
}