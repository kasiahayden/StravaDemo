package asia.haydenk.mystravademo;

import android.app.Activity;
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
import asia.haydenk.mystravademo.models.Segment;


public class ActivityListActivity extends Activity {
    //private JStravaV3 stravaClient;
    private AsyncHttpClient stravaClient;
    private String accessToken;
    private String TAG = "ActivityListActivity";
    private ArrayList<AthleteActivity> athleteActivities;
    private ArrayAdapter<AthleteActivity> aAthleteActivities;
    private ListView lvActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);
        accessToken = getIntent().getStringExtra("accessToken");
        stravaClient = new AsyncHttpClient();
        populateActivityListActivity();
        lvActivities = (ListView) findViewById(R.id.lvActivities);
        athleteActivities = new ArrayList<AthleteActivity>();
        aAthleteActivities = new AthleteActivityArrayAdapter(this, athleteActivities);
        lvActivities.setAdapter(aAthleteActivities);
    }

    private void populateActivityListActivity() {
        stravaClient.addHeader("Authorization", "Bearer " + accessToken);
        stravaClient.get("https://www.strava.com/api/v3/athlete/activities", new JsonHttpResponseHandler() {
            public void onSuccess(JSONArray json) {
                ArrayList<AthleteActivity> athleteActivities = AthleteActivity.fromJSONArray(json);
                aAthleteActivities.addAll(athleteActivities);
            }
            public void onFailure(Throwable ex) {
                ex.printStackTrace();
            }
        });

    }


    public void getActivityListTimeline(String accessToken) {
        stravaClient.addHeader("Authorization", "Bearer " + accessToken);
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
}
