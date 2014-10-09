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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stravaClient = new AsyncHttpClient();
        accessToken = getActivity().getIntent().getStringExtra("accessToken");
        Log.d(TAG, "getStringExtra accessToken: " + accessToken);
        populateSegmentListActivity();
    }

    public void populateSegmentListActivity() {
        stravaClient.addHeader("Authorization", "Bearer " + accessToken);
        RequestParams requestParams = new RequestParams();
        requestParams.put("bounds", "37.821362,-122.505373,37.842038,-122.465977"); // TODO: Replace calculated off of user's current
        stravaClient.get("https://www.strava.com/api/v3/segments/explore", requestParams, new JsonHttpResponseHandler() {
            public void onSuccess(JSONArray json) {
                ArrayList<Segment> segments = Segment.fromJSONArray(json);
                addAll(segments);
            }
            public void onSuccess(JSONObject json) {
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
            }
            public void onFailure(Throwable ex) {
                ex.printStackTrace();
            }
        });

    }
}
