package com.an.requesttask;

import android.util.Log;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;



public class RequestUtil {

    /* You can change the log to false in case you do
     * not want the logs to be printed for a release build */
    private static final boolean LOGGING = true;

    /* This is the TAG used for all logging purposes */
    private static final String TAG = "RequestTask";

    /* set the timeout for a connection  */
    private final static int ONE_MINUTE = 60;

    /* replace the {packageName} with your own app's package name  */
    private static final String LOCALE_CACHE_PATH = "/data/data/{packageName}/%s";

    public static void setLog(String message) {
        if(LOGGING) Log.d(TAG, message);
    }


    /* You can use this method to store the
     * request response from your local cache  */
    public static void writeObjectToDisk(String fileName, Object object) {
        fileName = String.format(LOCALE_CACHE_PATH, fileName);
        ObjectUtil objDataStream = new ObjectUtil();
        objDataStream.writeObjects(object,fileName);
    }

    /* You can use this method to retrieve the
     * request response from your local cache  */
    public static Object readObjectFromDisk(String fileName) {
        fileName = String.format(LOCALE_CACHE_PATH, fileName);
        ObjectUtil objDataStream = new ObjectUtil();
        return objDataStream.readObjects(fileName);
    }

    private static String getHttpResponse(InputStream inputStream) throws IOException {
        StringBuffer sb = new StringBuffer();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public static Object readHttpResponse(HttpURLConnection connection) throws Exception {
        try {
            Integer responseCode = connection.getResponseCode();
            InputStream inputStream;

            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                String responseString = getHttpResponse(inputStream);
                return responseString;
            } else {
                inputStream = connection.getErrorStream();
                String errorString = getHttpResponse(inputStream);

                /* For ease of use, we are creating an separate
                 * requestError object class and adding the
                 * responseCode & the response message to this class
                 * This can be commented out if not needed and you
                 * can pass only the error String if you wish */
                RequestError requestError = new RequestError();
                requestError.setResponseMessage(errorString);
                requestError.setResponseCode(responseCode);
                return requestError;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }


    public static HttpURLConnection createGETHttpsURLConnection(String urlString)
            throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod(RequestTask.REQUESTTYPE.GET.name());
        return connection;
    }

    public static HttpURLConnection createDELETEHttpsURLConnection(String urlString)
            throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod(RequestTask.REQUESTTYPE.DELETE.name());
        return connection;
    }


    public static HttpURLConnection createPOSTHttpsURLConnection(String urlString, JSONObject jsonObject)
            throws IOException {
        HttpURLConnection connection = createHttpsURLConnection(urlString);
        connection.setRequestMethod(RequestTask.REQUESTTYPE.POST.name());
        connection = defineHttpsURLConnection(connection, jsonObject);
        return connection;
    }


    public static HttpURLConnection createPUTHttpsURLConnection(String urlString, JSONObject jsonObject)
            throws IOException {
        HttpURLConnection connection = createHttpsURLConnection(urlString);
        connection.setRequestMethod(RequestTask.REQUESTTYPE.PUT.name());
        connection = defineHttpsURLConnection(connection, jsonObject);
        return connection;
    }


    private static HttpURLConnection defineHttpsURLConnection(HttpURLConnection connection, JSONObject jsonObject) {
        try {
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            /* Add headers to the request */
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(jsonObject.toString().getBytes(Charset.forName("UTF-8")));
            wr.flush();
            wr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }


    private static HttpURLConnection createHttpsURLConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setConnectTimeout(ONE_MINUTE);
        connection.setReadTimeout(ONE_MINUTE);

        return connection;
    }
}
