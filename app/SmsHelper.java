package com.example.cbeapp;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.webkit.JavascriptInterface;

public class SmsHelper {
    private Context context;

    public SmsHelper(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void sendSmsFromWeb(String message) {
        try {
            ContentValues values = new ContentValues();
            values.put("address", "CBE");
            values.put("body", message);
            values.put("read", 1);
            values.put("type", 1); // 1 = Inbox

            context.getContentResolver().insert(Uri.parse("content://sms/inbox"), values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}