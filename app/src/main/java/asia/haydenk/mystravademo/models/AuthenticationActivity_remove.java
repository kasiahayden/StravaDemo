package asia.haydenk.mystravademo.models;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;
import asia.haydenk.mystravademo.StravaAuthenticator;

import asia.haydenk.mystravademo.R;

/**
 * Created by khayden on 10/6/14.
 */
public class AuthenticationActivity_remove extends android.app.Activity {
    private static final int CLIENT_ID = 3171; // ENTER CLIENT ID
    private static final String CLIENT_SECRET = "30fb399fe606b53008531750d5cbb76223a6ed83"; // ENTER CLIENT SECRET
    private static final String AUTHORIZE_URL = "https://www.strava.com/oauth/token";
    private static final String CALLBACK_URI = "http://haydenk.asia"; //http://strava.android

    private AsyncHttpClient client;
    private String accessToken = null;

    private static String TAG = "AuthenticationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new AsyncHttpClient();
        if (getIntent().getData() != null) { // get access token
            getAccessToken(getIntent().getData());
        } else { // get request code
            getRequestCode();
        }

    }

    public void getRequestCode() {
        StravaAuthenticator auth = new StravaAuthenticator(CLIENT_ID, Uri.encode(CALLBACK_URI));
        String requestUrl = auth.getRequestAccessUrl("auto", false, true, "completed");
        Log.d("MainActivity", "requestUrl: " + requestUrl);
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(requestUrl));
        Log.d(TAG, "in RequestCode, before startActivity");
        startActivity(i);
        Log.d(TAG, "in RequestCode, after startActivity");
    }

    public void getAccessToken(Uri data) {
        String code = data.getQueryParameter("code");
        RequestParams params = new RequestParams();
        params.put("client_id", String.valueOf(CLIENT_ID));
        params.put("client_secret", CLIENT_SECRET);
        params.put("code", code);
        client.post(AUTHORIZE_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int code, JSONObject json) {
                accessToken = json.optString("access_token");
                // NOTE: STORE access token in shared preferences to avoid logging in every time
                // Authenticated Request
                //KMH sendExampleAuthenticatedRequest(accessToken);
                //getRecentActivities(accessToken);
            }

            @Override
            public void onFailure(Throwable ex) {
                ex.printStackTrace();
            }
        });
        Log.i("HELLO", getIntent().toString());
    }

    public String getAccessToken(){ //should always be non-null because onCreate will populate
        return accessToken;
    }

    public void sendExampleAuthenticatedRequest(String accessToken) {
        // Add authentication header
        client.addHeader("Authorization", "Bearer " + accessToken);
        // Send authenticated request
        client.get("https://www.strava.com/api/v3/athlete", new JsonHttpResponseHandler() {
            public void onSuccess(int code, JSONObject json) {
                String firstname = json.optString("firstname");
                Toast.makeText(AuthenticationActivity_remove.this, firstname, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
