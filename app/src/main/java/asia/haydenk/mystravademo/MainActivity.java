package asia.haydenk.mystravademo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;



public class MainActivity extends Activity {

    private static final int CLIENT_ID = 3171; // ENTER CLIENT ID
    private static final String CLIENT_SECRET = "30fb399fe606b53008531750d5cbb76223a6ed83"; // ENTER CLIENT SECRET
    private static final String AUTHORIZE_URL = "https://www.strava.com/oauth/token";
    private static final String CALLBACK_URI = "http://haydenk.asia"; //http://strava.android

    private AsyncHttpClient client;
    private String accessToken = null;

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
        startActivity(i);
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
                getRecentActivities(accessToken);
            }

            @Override
            public void onFailure(Throwable ex) {
                ex.printStackTrace();
            }
        });
        Log.i("HELLO", getIntent().toString());
    }

    public void sendExampleAuthenticatedRequest(String accessToken) {
        // Add authentication header
        client.addHeader("Authorization", "Bearer " + accessToken);
        // Send authenticated request
        client.get("https://www.strava.com/api/v3/athlete", new JsonHttpResponseHandler() {
            public void onSuccess(int code, JSONObject json) {
                String firstname = json.optString("firstname");
                Toast.makeText(MainActivity.this, firstname, Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "example authenticated request: firstname==" + firstname);
            }
        });
    }

    public void getRecentActivities(String accessToken) {
        // Add authentication header
        client.addHeader("Authorization", "Bearer " + accessToken);
        // Send authenticated request
        client.get("https://www.strava.com/api/v3/athlete/activities", new JsonHttpResponseHandler() {

            public void onSuccess(JSONArray json) {

                String name = "name";
                try {
                    name = json.getJSONObject(0).getString("name");
                } catch (JSONException e) {
                    name = "fail";
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "get recent activity: name==" + name);

                for(int i = 0; i < json.length(); i++) {
                    try {
                        name = json.getJSONObject(i).getString("name");
                    } catch (JSONException e) {
                        name = "fail";
                        e.printStackTrace();
                    }
                    Log.d("MainActivity", "get recent activity: name==" + name);

                }
            }

            /*public void onSuccess(int code, JSONObject json) {
                String name = json.optString("name");
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "get recent activity: name==" + name);
            }*/
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Click handler method for the button used to start OAuth flow //loginToRest
    public void testCall (View view) {
        Toast.makeText(this, "inside testCall", Toast.LENGTH_SHORT);
        Log.d("MainActivity", "==============Inside testCall");
        if (accessToken != null) {
            getRecentActivities(accessToken);
        }
    }

}
