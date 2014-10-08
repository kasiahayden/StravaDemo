package asia.haydenk.mystravademo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import asia.haydenk.mystravademo.models.AthleteActivity;


public class ActivityListActivity extends Activity {
    //private JStravaV3 stravaClient;
    private AsyncHttpClient stravaClient;
    private String accessToken;
    private String TAG = "ActivityListActivity";

    //private List<org.jstrava.entities.activity.Activity> athleteActivities;
    //private ArrayAdapter<org.jstrava.entities.activity.Activity> aAthleteActivities;
    private ArrayList<AthleteActivity> athleteActivities;
    private ArrayAdapter<AthleteActivity> aAthleteActivities;
    private ListView lvActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);
        accessToken = getIntent().getStringExtra("accessToken");
        Log.d(TAG, "getStringExtra accessToken: " + accessToken);
        stravaClient = new AsyncHttpClient();
        //stravaClient = MainActivity.getRestClient(accessToken);
        //stravaClient = new JStravaV3(accessToken);

        populateActivityListActivity();
        lvActivities = (ListView) findViewById(R.id.lvActivities);
        athleteActivities = new ArrayList<AthleteActivity>();
        //aAthleteActivities = new ArrayAdapter<AthleteActivity>(this, android.R.layout.simple_list_item_1, athleteActivities);
        aAthleteActivities = new AthleteActivityArrayAdapter(this, athleteActivities);
        lvActivities.setAdapter(aAthleteActivities);


    }

    private void populateActivityListActivity() {
        stravaClient.addHeader("Authorization", "Bearer " + accessToken);
        // Send authenticated request
        stravaClient.get("https://www.strava.com/api/v3/athlete/activities", new JsonHttpResponseHandler() {
            public void onSuccess(JSONArray json) {
                ArrayList<AthleteActivity> athleteActivities = AthleteActivity.fromJSONArray(json);
                aAthleteActivities.addAll(athleteActivities);
                for (int i = 0; i < athleteActivities.size(); i++) {
                    Log.d("ActivityListActivity", i + "::" + athleteActivities.get(i).getName());
                }
            }
            public void onFailure(Throwable ex) {
                ex.printStackTrace();
            }
        });

    }

    public void getRecentActivities(String accessToken) {
        // Add authentication header
        stravaClient.addHeader("Authorization", "Bearer " + accessToken);
        // Send authenticated request
        stravaClient.get("https://www.strava.com/api/v3/athlete/activities", new JsonHttpResponseHandler() {

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
                Toast.makeText(ActivityListActivity.this, name, Toast.LENGTH_SHORT).show();
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
        stravaClient.addHeader("Authorization", "Bearer " + accessToken);
        // Send authenticated request
        stravaClient.get("https://www.strava.com/api/v3/athlete/activities", new JsonHttpResponseHandler() {

            public void onSuccess(JSONArray json) {
                ArrayList<AthleteActivity> athleteActivities = AthleteActivity.fromJSONArray(json);
            }

            @Override
            public void onFailure(Throwable ex) {
                ex.printStackTrace();
            }
        });
    }


    public void sendExampleAuthenticatedRequest(String accessToken) {
        // Add authentication header
        stravaClient.addHeader("Authorization", "Bearer " + accessToken);
        // Send authenticated request
        stravaClient.get("https://www.strava.com/api/v3/athlete", new JsonHttpResponseHandler() {
            public void onSuccess(int code, JSONObject json) {
                String firstname = json.optString("firstname");
                Toast.makeText(ActivityListActivity.this, firstname, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
