package asia.haydenk.mystravademo.fragments;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import asia.haydenk.mystravademo.models.Segment;

/**
 * Created by khayden on 10/9/14.
 */
public class NearbySegmentsFragment extends SegmentsListFragment {
    private String accessToken;
    private String TAG = "NearbySegmentsFragment";
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
        /*curl -G https://www.strava.com/api/v3/segments/explore \
        -H "Authorization: Bearer 83ebeabdec09f6670863766f792ead24d61fe3f9" \
        -d bounds=37.821362,-122.505373,37.842038,-122.465977 \*/




        stravaClient.addHeader("Authorization", "Bearer " + accessToken);

        RequestParams requestParams = new RequestParams();
        requestParams.put("bounds", "37.821362,-122.505373,37.842038,-122.465977");
        Log.d(TAG, "Inside populateSegmentListActivity" );
        // Send authenticated request
        stravaClient.get("https://www.strava.com/api/v3/segments/explore", requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(JSONArray json) {
                Log.d(TAG, "SUCCESSFUL!!! populateSegmentListActivity :: " + stravaClient.toString());
                ArrayList<Segment> segments = Segment.fromJSONArray(json);
                //aSegments.addAll(segments);
                //fragmentSegmentsList.addAll(segments);
                addAll(segments);
                for (int i = 0; i < segments.size(); i++) {
                    Log.d(TAG, "...segment... " + segments.get(i));
                }
                Log.d(TAG, "End of populateSegmentListActivity");
            }
            public void onSuccess(JSONObject json) {
                Log.d(TAG, "onSuccess object : " + json);
                ArrayList<Segment> segments;
                try {
                    segments = Segment.fromJSONArray(json.getJSONArray("segments"));
                    addAll(segments);
                    for (int i = 0; i < segments.size(); i++) {
                        Log.d(TAG, "...segment... " + segments.get(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
