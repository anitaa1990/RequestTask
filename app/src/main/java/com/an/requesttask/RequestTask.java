package com.an.requesttask;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Map;

public class RequestTask extends AsyncTask<String, Void, Object> {

    private TaskResponseListener listener;
    private String path;
    private String url;
    private String type;
    private JSONObject jsonObject;
    private Map<String, String> queryParams;


    /* define your default url scheme here */
    private String BASE_SCHEME = "http";


    /* define your base url here */
    private String BASE_URL = "";


    /* GET Requests */
    public void get(String path,
                    TaskResponseListener listener) {
        this.path = path;
        this.type = REQUESTTYPE.GET.name();
        this.listener = listener;
    }

    public void get(String path,
                    Map<String, String> queryParams,
                     TaskResponseListener listener) {
        this.path = path;
        this.type = REQUESTTYPE.GET.name();
        this.queryParams = queryParams;
        this.listener = listener;
    }


    /* DELETE Requests */
    public void delete(String path,
                    TaskResponseListener listener) {
        this.path = path;
        this.type = REQUESTTYPE.DELETE.name();
        this.listener = listener;
    }

    public void delete(String path,
                    Map<String, String> queryParams,
                    TaskResponseListener listener) {
        this.path = path;
        this.type = REQUESTTYPE.DELETE.name();
        this.queryParams = queryParams;
        this.listener = listener;
    }


    /* POST Requests */
    public void post(String path,
                    TaskResponseListener listener) {
        this.path = path;
        this.type = REQUESTTYPE.POST.name();
        this.listener = listener;
    }

    public void post(String path,
                     Map<String, String> queryParams,
                     TaskResponseListener listener) {
        this.path = path;
        this.type = REQUESTTYPE.POST.name();
        this.queryParams = queryParams;
        this.listener = listener;
    }

    public void post(String path,
                     JSONObject jsonObject,
                     TaskResponseListener listener) {
        this.path = path;
        this.type = REQUESTTYPE.POST.name();
        this.jsonObject = jsonObject;
        this.listener = listener;
    }

    public void post(String path,
                     Map<String, String> queryParams,
                     JSONObject jsonObject,
                     TaskResponseListener listener) {
        this.path = path;
        this.type = REQUESTTYPE.POST.name();
        this.queryParams = queryParams;
        this.jsonObject = jsonObject;
        this.listener = listener;
    }


    /* PUT Requests */
    public void put(String path,
                    TaskResponseListener listener) {
        this.path = path;
        this.type = REQUESTTYPE.POST.name();
        this.listener = listener;
    }

    public void put(String path,
                    Map<String, String> queryParams,
                    TaskResponseListener listener) {
        this.path = path;
        this.type = REQUESTTYPE.POST.name();
        this.queryParams = queryParams;
        this.listener = listener;
    }

    public void put(String path,
                    JSONObject jsonObject,
                    TaskResponseListener listener) {
        this.path = path;
        this.type = REQUESTTYPE.POST.name();
        this.jsonObject = jsonObject;
        this.listener = listener;
    }

    public void put(String path,
                    Map<String, String> queryParams,
                    JSONObject jsonObject,
                    TaskResponseListener listener) {
        this.path = path;
        this.type = REQUESTTYPE.POST.name();
        this.queryParams = queryParams;
        this.jsonObject = jsonObject;
        this.listener = listener;
    }

    @SuppressLint("NewApi")
    public <P, T extends AsyncTask<P, ?, ?>> void start() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            execute();
        }
    }

    protected void onPreExecute() {}

    protected Object doInBackground(String... req) {
        /* construct the final url here */
        url = getUrl(path, queryParams);

        Object jsonResponse = null;
        HttpURLConnection connection = null;
        try {
            /* For ease of understanding, I have added the if else here
             * to create the http connection for different request types */
            if (type.equalsIgnoreCase(REQUESTTYPE.GET.name())) {
                connection = RequestUtil.createGETHttpsURLConnection(url);
            } else if (type.equalsIgnoreCase(REQUESTTYPE.PUT.name())) {
                connection = RequestUtil.createPUTHttpsURLConnection(url, jsonObject);
            } else if (type.equalsIgnoreCase(REQUESTTYPE.DELETE.name())) {
                connection = RequestUtil.createDELETEHttpsURLConnection(url);
            } else {
                connection = RequestUtil.createPOSTHttpsURLConnection(url, jsonObject);
            }

            /* Read the response received here */
            jsonResponse = RequestUtil.readHttpResponse(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    protected void onPostExecute(Object result) {
        /* The response can either be successful or failed
         * In case of failures, we add it to a separate RequestError class
         * which contains the statusCode & the error message
         * In case of success, we pass the response object to the callback */
        if(result instanceof RequestError) {
            listener.onError((RequestError) result);
        } else {
            listener.onSuccess(result);
        }

        /* if you would like to save the file locally,
         * you can uncomment the below  lines */
        String fileName = "";
        RequestUtil.writeObjectToDisk(fileName, result);
    }



    protected interface TaskResponseListener {
        void onSuccess(Object extra);
        void onError(RequestError error);
    }


    private String getUrl(String path, Map<String, String> params) {
        try {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme(BASE_SCHEME).encodedAuthority(BASE_URL).appendEncodedPath(path);
            if(params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.appendQueryParameter(entry.getKey(), entry.getValue());
                }
            }
            return builder.build().toString().replaceAll("%0A", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public enum REQUESTTYPE {
        GET,
        POST,
        PUT,
        DELETE;
    }
}