package com.example.android_sensor;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.view.textclassifier.TextLinks;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class GSpreadSheetPush {

/**
 * This is where data is transferred from your phone to GSheet using HTTP Rest API calls
  */

public static void addItemToSheet(final Context context, String uAction, String uName, String ePowerCharged, String tCalculated) {
    final String phoneManufacture = Build.MANUFACTURER;
    final String deviceName = Build.MODEL;
    final String uploadAction = uAction;
    final String userName = uName;
    final String estimatedPowerCharged = ePowerCharged;
    final String timeCalculated = tCalculated;
    ProgressDialog loading = null;

    if (uploadAction == "addItem") {
            loading = ProgressDialog.show(context, "Saving Data", "Please wait");
    }

    final ProgressDialog finalLoading = loading;

    StringRequest stringRequest = new StringRequest(Request.Method.POST,
            "https://script.google.com/macros/s/AKfycbxSghDUjSzaSnaq2KDB1-7Twp7CE0ryfHtJjij4XqfNrlBOgYa0/exec",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (uploadAction == "addItem") {
                        finalLoading.dismiss();
                        Toast.makeText(context, "Saving Succeed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Upload Succeed", Toast.LENGTH_SHORT).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (uploadAction == "addItem") {
                        finalLoading.dismiss();
                        Toast.makeText(context, "Saving Failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }) {
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> parameters = new HashMap<>();

            // We pass parameters here
            parameters.put("action", uploadAction);
            parameters.put("userName", userName);
            parameters.put("phoneManufacture", phoneManufacture);
            parameters.put("deviceName", deviceName);
            parameters.put("estimatedPowerCharged", estimatedPowerCharged);
            parameters.put("timeCalculated", timeCalculated);
            return parameters;
        }
    };

    int socketTimeOut = 20000; // 20 sec

    RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    stringRequest.setRetryPolicy(retryPolicy);

    RequestQueue queue = Volley.newRequestQueue(context);

    queue.add(stringRequest);
} }
