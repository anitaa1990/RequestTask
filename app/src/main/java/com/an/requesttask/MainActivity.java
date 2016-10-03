package com.an.requesttask;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /* GET Request Sample */
        final RequestTask getRequestTask = new RequestTask();
        getRequestTask.get("sample_one.json", new RequestTask.TaskResponseListener() {
            @Override
            public void onSuccess(Object extra) {
                System.out.println("Successful " + extra);
            }

            @Override
            public void onError(RequestError error) {
                System.out.println("Error " + error.getHeaders());
                System.out.println("Error " + error.getResponseCode());
                System.out.println("Error " + error.getResponseMessage());
            }
        });
        getRequestTask.start();
    }
}
