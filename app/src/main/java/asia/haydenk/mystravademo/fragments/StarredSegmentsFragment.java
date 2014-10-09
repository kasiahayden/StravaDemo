package asia.haydenk.mystravademo.fragments;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import asia.haydenk.mystravademo.R;
import asia.haydenk.mystravademo.models.Segment;

/**
 * Created by khayden on 10/9/14.
 */
public class StarredSegmentsFragment extends SegmentsListFragment {
    private String accessToken;
    private String TAG = "StarredSegmentsFragment";
    private AsyncHttpClient stravaClient;
    private SegmentsListFragment fragmentSegmentsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stravaClient = new AsyncHttpClient();
        accessToken = getActivity().getIntent().getStringExtra("accessToken");
        Log.d(TAG, "getStringExtra accessToken: " + accessToken);
        //fragmentSegmentsList = (SegmentsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_segments_list);
        populateSegmentListActivity();
    }

    public void populateSegmentListActivity() {
        stravaClient.addHeader("Authorization", "Bearer " + accessToken);
        // Send authenticated request
        stravaClient.get("https://www.strava.com/api/v3/segments/starred", new JsonHttpResponseHandler() {
            public void onSuccess(JSONArray json) {
                Log.d(TAG, "SUCCESSFUL!!! populateSegmentListActivity");
                ArrayList<Segment> segments = Segment.fromJSONArray(json);
                //aSegments.addAll(segments);
                //fragmentSegmentsList.addAll(segments);
                addAll(segments);
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
