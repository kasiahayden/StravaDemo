package asia.haydenk.mystravademo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

import asia.haydenk.mystravademo.fragments.SegmentsListFragment;
import asia.haydenk.mystravademo.models.AthleteActivity;
import asia.haydenk.mystravademo.models.Segment;


public class SegmentListActivity extends FragmentActivity {
    private AsyncHttpClient stravaClient;
    private String accessToken;
    private String TAG = "SegmentListActivity";
    private SegmentsListFragment fragmentSegmentsList;
    /*private ArrayList<Segment> segments;
    private ArrayAdapter<Segment> aSegments;
    private ListView lvSegments;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segment_list);
        accessToken = getIntent().getStringExtra("accessToken");
        Log.d(TAG, "getStringExtra accessToken: " + accessToken);
        stravaClient = new AsyncHttpClient();

        fragmentSegmentsList = (SegmentsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_segments_list); //

        populateSegmentListActivity();
        //lvSegments = (ListView) findViewById(R.id.lvSegments);
        /*segments = new ArrayList<Segment>();
        aSegments = new ArrayAdapter<Segment>(this, android.R.layout.simple_list_item_1, segments);*/
        //aSegments = new SegmentArrayAdapter(this, segments);
        //lvSegments.setAdapter(aSegments);




    }

    private void populateSegmentListActivity() {
        stravaClient.addHeader("Authorization", "Bearer " + accessToken);
        // Send authenticated request
        stravaClient.get("https://www.strava.com/api/v3/segments/starred", new JsonHttpResponseHandler() {
            public void onSuccess(JSONArray json) {
                Log.d(TAG, "SUCCESSFUL!!! populateSegmentListActivity");
                ArrayList<Segment> segments = Segment.fromJSONArray(json);
                //aSegments.addAll(segments);
                fragmentSegmentsList.addAll(segments);
                for (int i = 0; i < segments.size(); i++) {
                    Log.d(TAG, "...segment... " + segments.get(i));
                }
                Log.d(TAG, "End of populateSegmentListActivity");
            }
            public void onFailure(Throwable ex) {
                Log.d(TAG, "Unsuccessful populateSegmentListActivity");
                ex.printStackTrace();
            }
        });

    }




}
