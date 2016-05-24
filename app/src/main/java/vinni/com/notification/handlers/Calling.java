package vinni.com.notification.handlers;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vinni on 5/23/16.
 */
public class Calling {

    private static final String PREFIX = "api";
    private static final String API_METHOD_CALL = "incoming_call";
    private static final String API_METHOD_HANDSHAKE = "handshake";
    private static final String API_PARAM_MESSAGE = "message";
    private static final String TAG = "Calling";

    public static final String RESPONSE_SUCCESS = "SUCCESS";
    public static final String RESPONSE_FAILURE = "FAILURE";

    public static String checkConnection(String host) {
        String response = "";
        if (host != null && !host.isEmpty()) {
            String address = createUrl(host, API_METHOD_HANDSHAKE);
            HttpURLConnection connection = createConnection(address);
            if (connection != null) {
                response = sendRequest(connection, new HashMap<String, String>());
            }
        }
        return response;
    }

    public static String sendNotification(final String message) {
        String response = "";
        String host = Settings.getIpAddress();
        if (host != null && !host.isEmpty()) {
            String address = createUrl(host, API_METHOD_CALL);
            HttpURLConnection connection = createConnection(address);
            if (connection != null) {
                response = sendRequest(connection, new HashMap<String, String>() {
                    {
                        put(API_PARAM_MESSAGE, message);
                    }
                });
            }
        }
        return response;
    }

    private static String sendRequest(HttpURLConnection connection, Map<String, String> data) {
        String responseStr = "";
        try {
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(getParamsString(data));
            wr.flush();
            wr.close();
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            responseStr = response.toString();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            responseStr = e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return responseStr;
    }

    private static HttpURLConnection createConnection(String dataUrl) {
        HttpURLConnection connection = null;
        URL url;
        try {
            url = new URL(dataUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
        } catch (Exception e) {
            Log.e(TAG, "Creating connection failure!");
        }
        return connection;
    }

    private static String getParamsString(Map<String, String> data) {
        String result = "";
        if (data.size() == 0) {
            return result;
        }
        for (Map.Entry<String, String> entry : data.entrySet()) {
            result += entry.getKey() + "=" + entry.getValue() + "&";
        }
        if (result.length() > 0) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    private static String createUrl(String host, String method) {
        return "http://" + host + "/" + PREFIX + "/" + method + "/?";
    }
}
