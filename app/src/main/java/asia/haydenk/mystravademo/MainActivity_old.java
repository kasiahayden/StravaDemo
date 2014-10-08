package asia.haydenk.mystravademo;

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

import java.util.ArrayList;

import asia.haydenk.mystravademo.models.AthleteActivity;


public class MainActivity_old extends android.app.Activity {


    private AsyncHttpClient client;
    private String accessToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void sendExampleAuthenticatedRequest(String accessToken) {
        // Add authentication header
        client.addHeader("Authorization", "Bearer " + accessToken);
        // Send authenticated request
        client.get("https://www.strava.com/api/v3/athlete", new JsonHttpResponseHandler() {
            public void onSuccess(int code, JSONObject json) {
                String firstname = json.optString("firstname");
                Toast.makeText(MainActivity_old.this, firstname, Toast.LENGTH_SHORT).show();
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
                String type = "type";
                String distance = "distance";
                String elapsed_time = "elapsed_time";
                String start_date_local = "start_date_local";
                JSONArray segment_efforts = null;
                try {
                    name = json.getJSONObject(0).getString("name");
                    type = json.getJSONObject(0).getString("type"); //activity type, ie. ride, run, swim, etc.
                    distance = json.getJSONObject(0).getString("distance"); //float, meters
                    elapsed_time = json.getJSONObject(0).getString("elapsed_time"); //int, seconds
                    start_date_local = json.getJSONObject(0).getString("start_date_local"); //time
                    segment_efforts = json.getJSONObject(0).getJSONArray("segment_efforts"); //list of segment objs
                } catch (JSONException e) {
                    name = "fail";
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity_old.this, name, Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "... " + name + " ..."
                                + "\n" + type
                                + "\n" + distance
                                + "\n" + elapsed_time
                                + "\n" + start_date_local
                                + "\n" + "number of segments: " + segment_efforts.length()
                );

                /*for(int i = 0; i < json.length(); i++) {
                    try {
                        name = json.getJSONObject(i).getString("name");
                    } catch (JSONException e) {
                        name = "fail";
                        e.printStackTrace();
                    }
                    Log.d("MainActivity", "get recent activity: name==" + name);

                }*/


            }

            /*public void onSuccess(int code, JSONObject json) {
                String name = json.optString("name");
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "get recent activity: name==" + name);
            }*/
        });
    }


    public void getActivityListTimeline(String accessToken) {
        // Add authentication header
        client.addHeader("Authorization", "Bearer " + accessToken);
        // Send authenticated request
        client.get("https://www.strava.com/api/v3/athlete/activities", new JsonHttpResponseHandler() {

            public void onSuccess(JSONArray json) {
                ArrayList<AthleteActivity> athleteActivities = AthleteActivity.fromJSONArray(json);
            }

            @Override
            public void onFailure(Throwable ex) {
                ex.printStackTrace();
            }
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
